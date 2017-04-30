package services;

import java.io.File;
import java.io.FileNotFoundException;

import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;

public abstract class PdfGenerator<T> {

	private final static String USER_HOME = System.getProperty("user.home");
	private final static String PDFS_ROOT = USER_HOME + "/tpv/pdfs/";
    private final static String PDF_FILE_EXT = ".pdf";
    
    protected Document pdfDocument;
    
    private void makeDirectories(String path) {
        File file = new File(createPath(path));
        if (!file.exists()) {
            file.getParentFile().mkdirs();
        }
    }
    
    private String createPath(String path) {
    	if(!path.startsWith(PDFS_ROOT)) {
    		path = PDFS_ROOT + path;
    	}
    	if(!path.endsWith(PDF_FILE_EXT)) {
    		path += PDF_FILE_EXT;
    	}
    	return path;
    }
    
    protected abstract String ownPath();
    
    protected abstract PageSize ownPageSize();
    
    private Document getPdfDocument(String path, PageSize pageSize) throws FileNotFoundException {
        PdfWriter pdfWriter = new PdfWriter(createPath(path));
        PdfDocument pdfDocument = new PdfDocument(pdfWriter);
        return new Document(pdfDocument, pageSize);
    }
    
	protected void generatePdf(T entity) throws FileNotFoundException {
		String path = ownPath();
		makeDirectories(path);
		pdfDocument = getPdfDocument(path, ownPageSize());
	}
}
