package controllers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;

import daos.core.InvoiceDao;
import daos.core.ProductDao;
import daos.core.TicketDao;
import daos.users.UserDao;
import entities.core.Product;
import entities.core.Shopping;
import entities.core.ShoppingState;
import entities.core.Ticket;
import entities.users.User;
import services.PdfGenerationService;
import wrappers.DayTicketWrapper;
import wrappers.ShoppingCreationWrapper;
import wrappers.ShoppingTrackingWrapper;
import wrappers.ShoppingUpdateWrapper;
import wrappers.TicketCreationResponseWrapper;
import wrappers.TicketCreationWrapper;
import wrappers.TicketReferenceCreatedWrapper;
import wrappers.TicketReferenceWrapper;
import wrappers.TicketWrapper;

@Controller
public class TicketController {

    private TicketDao ticketDao;

    private UserDao userDao;

    private ProductDao productDao;

    private InvoiceDao invoiceDao;

    private PdfGenerationService pdfGenService;
    
    @Autowired
    public void setTicketDao(TicketDao ticketDao) {
        this.ticketDao = ticketDao;
    }

    @Autowired
    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }

    @Autowired
    public void setProductDao(ProductDao productDao) {
        this.productDao = productDao;
    }

    @Autowired
    public void setInvoiceDao(InvoiceDao invoiceDao) {
        this.invoiceDao = invoiceDao;
    }
    
    @Autowired
    public void setPdfGenerationService(PdfGenerationService pdfGenService){
        this.pdfGenService = pdfGenService;
    }

    public TicketCreationResponseWrapper createTicket(TicketCreationWrapper ticketCreationWrapper) throws IOException {
        Ticket ticket = new Ticket(getNextId());
        Long userMobile = ticketCreationWrapper.getUserMobile();
        if (userMobile != null) {
            User user = userDao.findByMobile(userMobile);
            ticket.setUser(user);
        }

        List<Shopping> shoppingList = new ArrayList<>();
        for (ShoppingCreationWrapper shoppingCreationWrapper : ticketCreationWrapper.getShoppingList()) {
            String productCode = shoppingCreationWrapper.getProductCode();
            Product product = productDao.findOne(productCode);
            ShoppingState shoppingState = shoppingCreationWrapper.isDelivered() ? ShoppingState.COMMITTED : ShoppingState.OPENED;
            Shopping shopping = new Shopping(shoppingCreationWrapper.getAmount(), shoppingCreationWrapper.getDiscount(), product,
                    product.getDescription(), product.getRetailPrice(), shoppingState);
            shoppingList.add(shopping);
        }

        ticket.setShoppingList(shoppingList);
        ticketDao.save(ticket);
        ticket = ticketDao.findFirstByReference(ticket.getReference());
        byte[] ticketPdfByteArray = pdfGenService.generateTicketPdf(ticket);
        return new TicketCreationResponseWrapper(ticketPdfByteArray, ticket.getReference());
    }

    private long getNextId() {
        long nextId = 1;
        Ticket ticket = ticketDao.findFirstByOrderByCreatedDescIdDesc();

        if (ticket != null) {
            Calendar todayMidnight = Calendar.getInstance();
            todayMidnight.set(Calendar.HOUR_OF_DAY, 0);
            todayMidnight.set(Calendar.MINUTE, 0);
            todayMidnight.set(Calendar.SECOND, 0);
            todayMidnight.set(Calendar.MILLISECOND, 0);

            if (ticket.getCreated().compareTo(todayMidnight) >= 0) {
                nextId = ticket.getId() + 1;
            }
        }
        return nextId;
    }

    public Ticket getTicket(String reference) {
        return ticketDao.findFirstByReference(reference);
    }

    public Ticket updateTicket(String reference, List<ShoppingUpdateWrapper> updatedShoppings) {
        Ticket ticket = getTicket(reference);
        Iterator<Shopping> shoppingList = ticket.getShoppingList().iterator();
        for (ShoppingUpdateWrapper shoppingUpdateWrapper : updatedShoppings) {
            String productCode = shoppingUpdateWrapper.getProductCode();
            boolean foundProductCode = false;
            while (shoppingList.hasNext() && !foundProductCode) {
                Shopping shopping = shoppingList.next();
                if (shopping.getProduct().getCode().equals(productCode)) {
                    foundProductCode = true;
                    shopping.setAmount(shoppingUpdateWrapper.getAmount());
                    shopping.setShoppingState(shoppingUpdateWrapper.getShoppingState());
                }
            }
        }
        ticketDao.save(ticket);
        return ticket;
    }

    public List<ShoppingTrackingWrapper> getTicketTracking(String reference) {
        Ticket ticket = ticketDao.findFirstByReference(reference);

        List<ShoppingTrackingWrapper> shoppingTrackingWrapperList = new LinkedList<>();
        for (Shopping shopping : ticket.getShoppingList()) {
            shoppingTrackingWrapperList.add(new ShoppingTrackingWrapper(shopping));
        }

        return shoppingTrackingWrapperList;
    }

    public boolean ticketReferenceExists(String reference) {
        Ticket ticket = ticketDao.findFirstByReference(reference);
        return ticket != null;
    }

    public List<DayTicketWrapper> getWholeDayTickets(Calendar dayToGetTickets) {
        List<DayTicketWrapper> dayTicketsList = new ArrayList<>();
        List<Ticket> ticketList = ticketDao.findByCreated(dayToGetTickets);
        for (Ticket ticket : ticketList) {
            dayTicketsList.add(new DayTicketWrapper(ticket));
        }
        return dayTicketsList;
    }
    
    public Page<TicketReferenceCreatedWrapper> getTicketsByUserMobile(long mobile, Pageable pageable) {
        Page<Ticket> ticketPage = ticketDao.findByUserMobile(mobile, pageable);
        List<TicketReferenceCreatedWrapper> ticketWrapperList = new ArrayList<>(); 
        for (Ticket ticket : ticketPage.getContent()) {
            ticketWrapperList.add(new TicketReferenceCreatedWrapper(ticket));
        }
        return new PageImpl<TicketReferenceCreatedWrapper>(ticketWrapperList, pageable, ticketPage.getTotalElements());
    }
       
    public Ticket findOneTicket(TicketReferenceWrapper ticketReferenceWrapper) {
        return ticketDao.findFirstByReference(ticketReferenceWrapper.getTicketReference());
    }
    
    public Ticket findOneTicketByReference(String ticketReference){
        return ticketDao.findFirstByReference(ticketReference);
    }

    public boolean ticketIsAlreadyAssignedToInvoice(Ticket ticket) {
        return invoiceDao.findByTicketReference(ticket.getReference()) != null;
    }

    public boolean isTicketClosed(Ticket ticket) {
        boolean closed = true;
        for (Shopping shopping : ticket.getShoppingList()) {
            if (shopping.getShoppingState() != ShoppingState.CLOSED) {
                closed = false;
            }
        }
        return closed;
    }

    public List<Ticket> findAll() {
       return ticketDao.findAll();      
    }

    public TicketWrapper associateUserToTicket(String ticketReference, Long userMobile) {
        Ticket ticket = findOneTicketByReference(ticketReference);
        User user = userDao.findByMobile(userMobile);
        ticket.setUser(user);
        return new TicketWrapper(ticketDao.saveAndFlush(ticket));

    }
}
