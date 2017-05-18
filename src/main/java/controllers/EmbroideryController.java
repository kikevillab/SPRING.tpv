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
        embroideryToBeSaved.setReference(embroideryCreationWrapper.getReference());
        embroideryToBeSaved.setColors(embroideryCreationWrapper.getColors());
        embroideryToBeSaved.setDescription(embroideryCreationWrapper.getDescription());
        embroideryToBeSaved.setDiscontinued(embroideryCreationWrapper.isDiscontinued());
        embroideryToBeSaved.setImage(embroideryCreationWrapper.getImage());
        embroideryToBeSaved.setRetailPrice(embroideryCreationWrapper.getRetailPrice());
        embroideryToBeSaved.setSquareMillimeters(embroideryCreationWrapper.getSquareMillimeters());
        embroideryToBeSaved.setStitches(embroideryCreationWrapper.getStitches());
        embroideryDao.saveAndFlush(embroideryToBeSaved);
    }

    public void updateEmbroidery(String code, EmbroideryUpdateWrapper embroideryUpdateWrapper) {
        Embroidery embroideryToBeUpdated = embroideryDao.findOne(code);
        embroideryToBeUpdated.setCode(embroideryUpdateWrapper.getCode());
        embroideryToBeUpdated.setReference(embroideryUpdateWrapper.getReference());
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

    public EmbroideryWrapper findOneEmbroidery(String code) {      
        return entityToWrapper(embroideryDao.findOne(code));
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

    public boolean embroideryExists(String code) {
        return embroideryDao.exists(code);
    }

    public void deleteEmbroidery(String code) {
        embroideryDao.delete(code);
    }

}
