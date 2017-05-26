package services;

import static config.ResourceNames.PDFS_ROOT;
import static config.ResourceNames.PDF_FILE_EXT;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;

public abstract class PdfGenerator<T> {
    
    protected T entity;
    
    public PdfGenerator(T entity){
        this.entity = entity;
    }

    private void makeDirectories(String path) {
        File file = new File(createPath(path));
        if (!file.exists()) {
            file.getParentFile().mkdirs();
        }
    }

    private String createPath(String path) {
        if (!path.startsWith(PDFS_ROOT)) {
            path = PDFS_ROOT + path;
        }
        if (!path.endsWith(PDF_FILE_EXT)) {
            path += PDF_FILE_EXT;
        }
        return path;
    }

    protected abstract String ownPath();

    protected abstract PageSize ownPageSize();

    public byte[] generatePdf() throws IOException {
        String path = ownPath();
        makeDirectories(path);
        PdfWriter pdfWriter = new PdfWriter(createPath(path));
        PdfDocument pdfDocument = new PdfDocument(pdfWriter);
        Document document = new Document(pdfDocument, ownPageSize());
        buildPdf(pdfDocument, document);
        document.close();
        return Files.readAllBytes(new File(path).toPath());
    }

    protected abstract void buildPdf(PdfDocument pdfDocument, Document document);
    
    
}
