package api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import controllers.RemoveTokenExpiredController;
import io.swagger.annotations.ApiOperation;

@RestController
@EnableScheduling
@RequestMapping(Uris.VERSION)
public class BacthResource {

    private RemoveTokenExpiredController RemoveTokenExpiredController;

    
    @Autowired
    public void setRemoveTokenExpiredController(RemoveTokenExpiredController RemoveTokenExpiredController) {
        this.RemoveTokenExpiredController = RemoveTokenExpiredController;
    }

    @ApiOperation(value = "Remove Token expired",
            notes = "If response is true then remove all tokens expired else there are not tokens expired",
            response = Boolean.class)
    @RequestMapping(value = Uris.DELETE_TOKEN_EXPIRED+"edu33",method = RequestMethod.DELETE)
    public Boolean EliminarTokensCaducados()  {
        int retorno= RemoveTokenExpiredController.removeTokenExpired();
        if (retorno==0) 
            return false;
        else
            return true;
     
    }
}
