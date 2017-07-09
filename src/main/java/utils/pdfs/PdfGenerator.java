package utils.pdfs;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.SimpleDateFormat;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import entities.core.Invoice;
import entities.core.Shopping;
import entities.core.ShoppingState;
import entities.core.Ticket;
import services.Company;
import services.DatabaseSeederService;

@Component
public class PdfGenerator {

    private static final float[] TABLE_COLUMNS_SIZES = {20, 85, 20, 30, 40, 15};

    private static final String[] TABLE_COLUMNS_HEADERS = {" ", "Desc.", "Ud.", "Dto.", "€", "E."};

    private static final String DATE_FORMAT = "yyyy-MM-dd HH:mm";

    private Company company;

    @Autowired
    public void setDatabaseSeederService(DatabaseSeederService databaseSeederService) {
        this.company = databaseSeederService.getCompany();
    }

    public byte[] generate(Ticket ticket) {
        final String path = "/tickets/ticket-";

        PdfBuilder pdf = new PdfBuilder(path + ticket.getDate() + ticket.getId()).pageTermic();
        pdf.addImage(company.getLogo());

        pdf.paragraphEmphasized("Tfno: " + company.getPhone()).paragraph("NIF: " + company.getNif()).paragraph(company.getPostalAddress());

        pdf.barCode(ticket.getDate() + "" + ticket.getId()).separator();

        SimpleDateFormat formatter = new SimpleDateFormat(DATE_FORMAT);
        pdf.paragraphEmphasized(formatter.format(ticket.getCreated().getTimeInMillis()));

        pdf.valuesTable(TABLE_COLUMNS_SIZES).tableHeader(TABLE_COLUMNS_HEADERS);
        for (int i = 0; i < ticket.getShoppingList().size(); i++) {
            Shopping shopping = ticket.getShoppingList().get(i);
            String state = "";
            if (shopping.getShoppingState() != ShoppingState.COMMITTED) {
                state = "N";
            }
            pdf.tableCell(String.valueOf(i + 1), shopping.getDescription(), String.valueOf(shopping.getAmount()),
                    shopping.getDiscount() + "%", new BigDecimal(shopping.getShoppingTotal()).setScale(2, RoundingMode.HALF_UP) + "€",
                    state);
        }
        pdf.tableColspanRight("TOTAL: " + ticket.getTicketTotal().setScale(2, RoundingMode.HALF_UP) + "€").separator();

        pdf.paragraph("Periodo de devolución o cambio: 15 dias a partir de la fecha del ticket");
        pdf.paragraphEmphasized("Gracias por su compra");
        pdf.qrCode(ticket.getReference());

        return pdf.build();
    }

    public byte[] generate(Invoice invoice) {
        final String path = "/invoices/invoice-";

        PdfBuilder pdf = new PdfBuilder(path + invoice.getId()).pageTermic();

        pdf.header(company.getLogo(), company.getCompany() + "\n" + "NIF: " + company.getNif() + "\n" + company.getPostalAddress());
        pdf.header(
                "Factura Nº: " + invoice.getId() + "       " + new SimpleDateFormat("dd 'de' MMM 'de' yyyy").format(invoice.getCreated().getTimeInMillis()));
        pdf.separator();

        pdf.header(invoice.getTicket().getUser().getUsername() + "\n" + "DNI: " + invoice.getTicket().getUser().getDni() + "\n"
                + invoice.getTicket().getUser().getAddress()).separator();

        pdf.valuesTable(100, 20, 30, 40).tableHeader("Desc.", "Ud.", "Dto.", "€");
        for (int i = 0; i < invoice.getTicket().getShoppingList().size(); i++) {
            Shopping shopping = invoice.getTicket().getShoppingList().get(i);
            pdf.tableCell(shopping.getDescription(), String.valueOf(shopping.getAmount()), shopping.getDiscount() + "%",
                    new BigDecimal(shopping.getShoppingTotal()).setScale(2, RoundingMode.HALF_UP) + "€");
        }

        BigDecimal baseImponible = invoice.getTicket().getTicketTotal().divide(new BigDecimal(1.21), 2, RoundingMode.HALF_UP);
        pdf.tableColspanRight("Base Imponible: " + baseImponible + "€");
        pdf.tableColspanRight("21% IVA: " + baseImponible.multiply(new BigDecimal(21).movePointLeft(2)) + "€");
        pdf.tableColspanRight("TOTAL: " + invoice.getTicket().getTicketTotal().setScale(2, RoundingMode.HALF_UP) + "€").separator();
        pdf.barCode(String.valueOf(invoice.getId()));
        
        return pdf.build();
    }

}
