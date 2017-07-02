package api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import api.exceptions.EmbroideryNotFoundException;
import controllers.EmbroideryController;
import wrappers.EmbroideryCreationWrapper;
import wrappers.EmbroideryUpdateWrapper;
import wrappers.EmbroideryWrapper;

@RestController
@RequestMapping(Uris.VERSION + Uris.EMBROIDERIES)
public class EmbroideryResource {

    private EmbroideryController embroideryController;

    @Autowired
    public void setEmbroideryController(EmbroideryController embroideryController) {
        this.embroideryController = embroideryController;
    }

    @RequestMapping(method = RequestMethod.POST)
    public void createEmbroidery(@RequestBody EmbroideryCreationWrapper embroideryCreationWrapper) {
        //TODO Check fields
        embroideryController.createEmbroidery(embroideryCreationWrapper);
    }

    @RequestMapping(value = Uris.PRODUCT_CODE_ID, method = RequestMethod.PUT)
    public void updateEmbroidery(@PathVariable String code, @RequestBody EmbroideryUpdateWrapper embroideryUpdateWrapper) throws EmbroideryNotFoundException {
        throwExceptionIfEmbroideryDoesNotExist(code);
        embroideryController.updateEmbroidery(code, embroideryUpdateWrapper);
    }

    @RequestMapping(method = RequestMethod.GET)
    public List<EmbroideryWrapper> findAllEmbroideries() {
        return embroideryController.findAllEmbroideries();
    }
    
    @RequestMapping(value = Uris.PRODUCT_CODE_ID, method = RequestMethod.GET)
    public EmbroideryWrapper findOneEmbroidery(@PathVariable String code) throws EmbroideryNotFoundException{
        throwExceptionIfEmbroideryDoesNotExist(code);
        return embroideryController.findOneEmbroidery(code);
    }
    
    @RequestMapping(value = Uris.PRODUCT_CODE_ID, method = RequestMethod.DELETE)
    public void deleteEmbroidery(@PathVariable String code) throws EmbroideryNotFoundException{
        throwExceptionIfEmbroideryDoesNotExist(code);
        embroideryController.deleteEmbroidery(code);        
    }
    
    private void throwExceptionIfEmbroideryDoesNotExist(String code) throws EmbroideryNotFoundException{
        if(!embroideryController.embroideryExists(code)){
            throw new EmbroideryNotFoundException("Code: " + code);
        }
    }
}