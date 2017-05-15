package api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import api.exceptions.TextilePrintingNotFoundException;
import controllers.TextilePrintingController;
import wrappers.TextilePrintingCreationWrapper;
import wrappers.TextilePrintingUpdateWrapper;
import wrappers.TextilePrintingWrapper;

@RestController
@RequestMapping(Uris.VERSION + Uris.TEXTILE_PRINTINGS)
public class TextilePrintingResource {

    private TextilePrintingController textilePrintingController;

    @Autowired
    public void setTextilePrintingController(TextilePrintingController textilePrintingController) {
        this.textilePrintingController = textilePrintingController;
    }

    @RequestMapping(method = RequestMethod.POST)
    public void createTextilePrinting(@RequestBody TextilePrintingCreationWrapper textilePrintingCreationWrapper) {
        //TODO Check fields
        textilePrintingController.createTextilePrinting(textilePrintingCreationWrapper);
    }

    @RequestMapping(method = RequestMethod.PUT)
    public void updateTextilePrinting(@RequestBody TextilePrintingUpdateWrapper textilePrintingUpdateWrapper) throws TextilePrintingNotFoundException {
        if(!textilePrintingController.textilePrintingExists(textilePrintingUpdateWrapper.getId())){
            throw new TextilePrintingNotFoundException("Id: " + textilePrintingUpdateWrapper.getId());
        }
        textilePrintingController.updateTextilePrinting(textilePrintingUpdateWrapper);
    }

    @RequestMapping(method = RequestMethod.GET)
    public List<TextilePrintingWrapper> findAllTextilePrintings() {
        return textilePrintingController.findAllTextilePrintings();
    }
    
    @RequestMapping(value = Uris.ID, method = RequestMethod.GET)
    public TextilePrintingWrapper findOneTextilePrinting(@PathVariable long id) throws TextilePrintingNotFoundException{
        if(!textilePrintingController.textilePrintingExists(id)){
            throw new TextilePrintingNotFoundException("Id: " + id);
        }
        return textilePrintingController.findOneTextilePrinting(id);
    }    
    
    @RequestMapping(value = Uris.ID, method = RequestMethod.DELETE)
    public void deleteTextilePrinting(@PathVariable long id) throws TextilePrintingNotFoundException{
        if(!textilePrintingController.textilePrintingExists(id)){
            throw new TextilePrintingNotFoundException("Id: " + id);
        }
        textilePrintingController.deleteTextilePrinting(id);        
    }
}
