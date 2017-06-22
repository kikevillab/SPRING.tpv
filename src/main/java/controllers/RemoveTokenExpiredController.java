package controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import services.RemoveTokenExpiredService;

@Controller
public class RemoveTokenExpiredController {
 
    private RemoveTokenExpiredService removeTokenExpiredService;

    
    @Autowired
    public void setRemoveTokenExpiredService(RemoveTokenExpiredService removeTokenExpiredService) {
        this.removeTokenExpiredService = removeTokenExpiredService;
    }
    
    public int removeTokenExpired() {
        return removeTokenExpiredService.removeTokenExpired();

    }
}
