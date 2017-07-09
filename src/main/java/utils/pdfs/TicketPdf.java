package utils.pdfs;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.SimpleDateFormat;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import entities.core.Shopping;
import entities.core.ShoppingState;
import entities.core.Ticket;
import services.Company;
import services.DatabaseSeederService;

@Component
public class TicketPdf {

    private Company company;

    @Autowired
    public void setDatabaseSeederService(DatabaseSeederService databaseSeederService) {
        this.company = databaseSeederService.getCompany();
    }

    public byte[] generate(Ticket ticket) {
        PdfBuilder pdf = new PdfBuilder("/tickets/ticket-" + ticket.getDate() + ticket.getId()).pageTermic();
        pdf.addImage(company.getLogo());
        pdf.paragraphEmphasized("Tfno: " + company.getPhone()).paragraph("NIF: " + company.getNif()).paragraph(company.getPostalAddress());
        pdf.barCode(ticket.getDate() + "" + ticket.getId()).separator();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        pdf.paragraphEmphasized(formatter.format(ticket.getCreated().getTimeInMillis()));
        pdf.table(20, 85, 20, 30, 40, 15).tableHeader(" ", "Desc.", "Ud.", "Dto.", "€", "E.");

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
}
