package utils.pdfs;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import org.junit.Test;

public class PdfBuilderTest {

    @Test
    public void testBuildTicket() {
        PdfBuilder pdf = new PdfBuilder("/test/ticket").pageTermic();
        pdf.addImage("logo_upm.jpg");
        pdf.paragraphEmphasized("Tfno: 666.666.666").paragraph("NIF: 12345678Z")
                .paragraph("Campus Sur UPM, Calle Nikola Tesla, s/n, 28031 Madrid");

        pdf.barCode("201707083").separator();
        pdf.paragraphEmphasized(new SimpleDateFormat("yyyy-MM-dd HH:mm").format(Calendar.getInstance().getTime()));
        pdf.valuesTable(20, 85, 20, 30, 40, 15).tableHeader(" ", "Desc.", "Ud.", "Dto.", "€", "E.");
        pdf.tableCell("1", "Articulo 2345", "1", "10%", "99,45€", "");
        pdf.tableCell("2", "Articulo 234876", "3", "10%", "99,45€", "No");
        pdf.tableCell("3", "Articulo 433", "2", "10%", "22,45€", "");
        pdf.tableCell("4", "Articulo 232245", "1", "10%", "99,45€", "");
        pdf.tableCell("5", "Articulo 553", "3", "10%", "99,45€", "No");
        pdf.tableCell("6", "Articulo 335", "2", "10%", "22,45€", "");
        pdf.tableColspanRight("TOTAL: 234,34€").separator();

        pdf.paragraph("Periodo de devolución o cambio: 15 dias a partir de la fecha del ticket");
        pdf.paragraphEmphasized("Gracias por su compra");
        pdf.qrCode("GasRd5jfTgGGt4fDeSW3");

        pdf.build();
    }

    @Test
    public void testBuildInvoice() {
        PdfBuilder pdf = new PdfBuilder("/test/invoice").pageTermic();
        pdf.header("logo_upm.jpg", "UPM" + "\n" + "NIF: 12345678Z" + "\n" + "Calle Alan Turing s/n, 28031 Madrid");
        pdf.header(
                "Factura Nº: 2017/3" + "       " + new SimpleDateFormat("dd 'de' MMM 'de' yyyy").format(Calendar.getInstance().getTime()));
        pdf.separator();
        pdf.header("Jesús García Gómez" + "\n" + "NIF: 12345678Z" + "\n" + "Calle de Miw, 28031 Madrid").separator();
        pdf.valuesTable(20, 85, 20, 30, 40).tableHeader(" ", "Desc.", "Ud.", "Dto.", "€");
        pdf.tableCell("1", "Articulo 2345", "1", "10%", "99,45€");
        pdf.tableCell("2", "Articulo 234876", "3", "10%", "99,45€");
        pdf.tableCell("3", "Articulo 433", "2", "10%", "22,45€");
        pdf.tableCell("4", "Articulo 232245", "1", "10%", "99,45€");
        pdf.tableCell("5", "Articulo 553", "3", "10%", "99,45€");
        pdf.tableCell("6", "Articulo 335", "2", "10%", "22,45€");
        pdf.tableColspanRight("Base Imponible: 200,01€").tableColspanRight("21% IVA: 34,56€").tableColspanRight("TOTAL: 234,34€")
                .separator();
        pdf.barCode("20173");

        pdf.build();
    }

}
