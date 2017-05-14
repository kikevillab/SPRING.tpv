package api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

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
        embroideryController.createEmbroidery(embroideryCreationWrapper);
    }

    @RequestMapping(method = RequestMethod.PUT)
    public void updateEmbroidery(@RequestBody EmbroideryUpdateWrapper embroideryUpdateWrapper) {
        embroideryController.updateEmbroidery(embroideryUpdateWrapper);
    }

    @RequestMapping(method = RequestMethod.GET)
    public EmbroideryWrapper findAllEmbroideries() {
        return embroideryController.findAllEmbroideries();
    }
}
