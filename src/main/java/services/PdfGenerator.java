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

    protected Document document;

    protected T entity;

    public PdfGenerator(T entity) {
        this.entity = entity;
    }

    private void makeDirectories(String fullPath) {
        File file = new File(createFullPath(fullPath));
        if (!file.exists()) {
            file.getParentFile().mkdirs();
        }
    }

    private String createFullPath(String path) {
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

    private Document getDocument(String fullPath, PageSize pageSize) throws IOException {
        PdfWriter pdfWriter = new PdfWriter(fullPath);
        PdfDocument pdfDocument = new PdfDocument(pdfWriter);
        return new Document(pdfDocument, pageSize);
    }

    public byte[] generatePdf() throws IOException {
        String fullPath = createFullPath(ownPath());
        makeDirectories(fullPath);
        document = getDocument(fullPath, ownPageSize());
        buildPdf();
        document.close();
        return Files.readAllBytes(new File(fullPath).toPath());
    }

    protected abstract void buildPdf();

}
