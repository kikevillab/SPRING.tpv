package api;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.MediaType;
import org.springframework.http.converter.ByteArrayHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

public class EnhancedRestTemplate extends RestTemplate {
    public EnhancedRestTemplate() {
        super();
        setPDFMessageConverter();
    }
    
    private void setPDFMessageConverter(){
        ByteArrayHttpMessageConverter byteArrayHttpMessageConverter = new ByteArrayHttpMessageConverter();
        List<MediaType> supportedApplicationTypes = new ArrayList<MediaType>();
        MediaType pdfApplication = new MediaType("application","pdf");
        supportedApplicationTypes.add(pdfApplication);
        byteArrayHttpMessageConverter.setSupportedMediaTypes(supportedApplicationTypes);
        getMessageConverters().add(byteArrayHttpMessageConverter);
    }
}
