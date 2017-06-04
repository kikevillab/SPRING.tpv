package api;

import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import services.RemoveTokenExpiredService;

@RestController
@EnableScheduling
@RequestMapping(Uris.VERSION)
public class BacthResource {

    private RemoveTokenExpiredService removeTokenExpiredService;

    @Autowired
    public void setRemoveTokenExpiredService(RemoveTokenExpiredService removeTokenExpiredService) {
        this.removeTokenExpiredService = removeTokenExpiredService;
    }

    @RequestMapping(value = Uris.DELETE_TOKEN_EXPIRED)
    public void EliminarTokensCaducados()  {
        removeTokenExpiredService.removeTokenExpired();
    }
}
