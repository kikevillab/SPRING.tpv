package controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import daos.core.EmbroideryDao;
import entities.core.Embroidery;
import wrappers.EmbroideryCreationWrapper;
import wrappers.EmbroideryUpdateWrapper;
import wrappers.EmbroideryWrapper;

@Controller
public class EmbroideryController {
    
    private EmbroideryDao embroideryDao;
    
    @Autowired
    public void setEmbroideryDao(EmbroideryDao embroideryDao){
        this.embroideryDao = embroideryDao;
    }

    public void createEmbroidery(EmbroideryCreationWrapper embroideryCreationWrapper) {
        Embroidery embroideryToBeSaved = new Embroidery();
        embroideryToBeSaved.setCode(embroideryCreationWrapper.getCode());
        embroideryToBeSaved.setColors(embroideryCreationWrapper.getColors());
        embroideryToBeSaved.setDescription(embroideryCreationWrapper.getDescription());
        embroideryToBeSaved.setDiscontinued(embroideryCreationWrapper.isDiscontinued());
        embroideryToBeSaved.setImage(embroideryCreationWrapper.getImage());
        embroideryToBeSaved.setRetailPrice(embroideryCreationWrapper.getRetailPrice());
        embroideryToBeSaved.setSquareMillimeters(embroideryCreationWrapper.getSquareMillimeters());
        embroideryToBeSaved.setStitches(embroideryCreationWrapper.getStitches());
        embroideryDao.saveAndFlush(embroideryToBeSaved);
    }

    public void updateEmbroidery(EmbroideryUpdateWrapper embroideryUpdateWrapper) {
        Embroidery embroideryToBeUpdated = embroideryDao.findOne(embroideryUpdateWrapper.getId());
        embroideryToBeUpdated.setCode(embroideryUpdateWrapper.getCode());
        embroideryToBeUpdated.setColors(embroideryUpdateWrapper.getColors());
        embroideryToBeUpdated.setDescription(embroideryUpdateWrapper.getDescription());
        embroideryToBeUpdated.setDiscontinued(embroideryUpdateWrapper.isDiscontinued());
        embroideryToBeUpdated.setImage(embroideryUpdateWrapper.getImage());
        embroideryToBeUpdated.setRetailPrice(embroideryUpdateWrapper.getRetailPrice());
        embroideryToBeUpdated.setSquareMillimeters(embroideryUpdateWrapper.getSquareMillimeters());
        embroideryToBeUpdated.setStitches(embroideryUpdateWrapper.getStitches());       
        embroideryDao.save(embroideryToBeUpdated);       
    }

    public List<EmbroideryWrapper> findAllEmbroideries() {
        List<EmbroideryWrapper> embroideryWrappers = new ArrayList<>();
        for(Embroidery embroidery : embroideryDao.findAll()){
            embroideryWrappers.add(entityToWrapper(embroidery));
        }     
        return embroideryWrappers;
    }

    public EmbroideryWrapper findOneEmbroidery(long id) {      
        return entityToWrapper(embroideryDao.findOne(id));
    }
    
    private EmbroideryWrapper entityToWrapper(Embroidery embroidery){
        EmbroideryWrapper embroideryWrapper = new EmbroideryWrapper();
        embroideryWrapper.setCode(embroidery.getCode());
        embroideryWrapper.setColors(embroidery.getColors());
        embroideryWrapper.setDescription(embroidery.getDescription());
        embroideryWrapper.setDiscontinued(embroidery.isDiscontinued());
        embroideryWrapper.setImage(embroidery.getImage());
        embroideryWrapper.setRetailPrice(embroidery.getRetailPrice());
        embroideryWrapper.setSquareMillimeters(embroidery.getSquareMillimeters());
        embroideryWrapper.setStitches(embroidery.getStitches());
        return embroideryWrapper;
    }

    public boolean embroideryExists(long id) {
        return embroideryDao.exists(id);
    }

}
