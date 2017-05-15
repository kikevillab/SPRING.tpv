package controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import daos.core.TextilePrintingDao;
import entities.core.TextilePrinting;
import wrappers.TextilePrintingCreationWrapper;
import wrappers.TextilePrintingUpdateWrapper;
import wrappers.TextilePrintingWrapper;

@Controller
public class TextilePrintingController {

    private TextilePrintingDao textilePrintingDao;
    
    @Autowired
    public void setTextilePrintingDao(TextilePrintingDao textilePrintingDao){
        this.textilePrintingDao = textilePrintingDao;
    }

    public void createTextilePrinting(TextilePrintingCreationWrapper textilePrintingCreationWrapper) {
        TextilePrinting textilePrintingToBeSaved = new TextilePrinting();
        textilePrintingToBeSaved.setCode(textilePrintingCreationWrapper.getCode());
        textilePrintingToBeSaved.setDescription(textilePrintingCreationWrapper.getDescription());
        textilePrintingToBeSaved.setDiscontinued(textilePrintingCreationWrapper.isDiscontinued());
        textilePrintingToBeSaved.setImage(textilePrintingCreationWrapper.getImage());
        textilePrintingToBeSaved.setRetailPrice(textilePrintingCreationWrapper.getRetailPrice());
        textilePrintingToBeSaved.setType(textilePrintingCreationWrapper.getType());
        textilePrintingDao.saveAndFlush(textilePrintingToBeSaved);
    }

    public void updateTextilePrinting(TextilePrintingUpdateWrapper textilePrintingUpdateWrapper) {
        TextilePrinting textilePrintingToBeUpdated = textilePrintingDao.findOne(textilePrintingUpdateWrapper.getId());
        textilePrintingToBeUpdated.setCode(textilePrintingUpdateWrapper.getCode());
        textilePrintingToBeUpdated.setDescription(textilePrintingUpdateWrapper.getDescription());
        textilePrintingToBeUpdated.setDiscontinued(textilePrintingUpdateWrapper.isDiscontinued());
        textilePrintingToBeUpdated.setImage(textilePrintingUpdateWrapper.getImage());
        textilePrintingToBeUpdated.setRetailPrice(textilePrintingUpdateWrapper.getRetailPrice());
        textilePrintingToBeUpdated.setType(textilePrintingUpdateWrapper.getType());
        textilePrintingDao.saveAndFlush(textilePrintingToBeUpdated);       
    }

    public List<TextilePrintingWrapper> findAllTextilePrintings() {
        List<TextilePrintingWrapper> textilePrintingWrappers = new ArrayList<>();
        for(TextilePrinting textilePrinting : textilePrintingDao.findAll()){
            textilePrintingWrappers.add(entityToWrapper(textilePrinting));
        }     
        return textilePrintingWrappers;
    }

    public TextilePrintingWrapper findOneTextilePrinting(long id) {      
        return entityToWrapper(textilePrintingDao.findOne(id));
    }
    
    private TextilePrintingWrapper entityToWrapper(TextilePrinting textilePrinting){
        TextilePrintingWrapper textilePrintingWrapper = new TextilePrintingWrapper();
        textilePrintingWrapper.setCode(textilePrinting.getCode());
        textilePrintingWrapper.setDescription(textilePrinting.getDescription());
        textilePrintingWrapper.setDiscontinued(textilePrinting.isDiscontinued());
        textilePrintingWrapper.setImage(textilePrinting.getImage());
        textilePrintingWrapper.setRetailPrice(textilePrinting.getRetailPrice());
        return textilePrintingWrapper;
    }

    public boolean textilePrintingExists(long id) {
        return textilePrintingDao.exists(id);
    }  
}
