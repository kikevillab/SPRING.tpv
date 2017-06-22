package api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import controllers.RemoveTokenExpiredController;

@RestController
@EnableScheduling
@RequestMapping(Uris.VERSION)
public class BacthResource {

    private RemoveTokenExpiredController RemoveTokenExpiredController;

    
    @Autowired
    public void setRemoveTokenExpiredController(RemoveTokenExpiredController RemoveTokenExpiredController) {
        this.RemoveTokenExpiredController = RemoveTokenExpiredController;
    }

    @RequestMapping(value = Uris.DELETE_TOKEN_EXPIRED)
    public Boolean EliminarTokensCaducados()  {
        int retorno= RemoveTokenExpiredController.removeTokenExpired();
        if (retorno==0) 
            return false;
        else
            return true;
     
    }
}
