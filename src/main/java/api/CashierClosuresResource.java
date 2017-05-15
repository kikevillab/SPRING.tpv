package api;

import api.exceptions.LastCashierClosureIsClosedException;
import api.exceptions.LastCashierClosureIsOpenYetException;
import api.exceptions.NotExistsCashierClosuresException;
import controllers.CashierClosuresController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import wrappers.CashierClosuresCreationWrapper;
import wrappers.CashierClosuresWrapper;

@RestController
@RequestMapping(Uris.VERSION + Uris.CASHIER_CLOSURES)
public class CashierClosuresResource {

    private CashierClosuresController cashierClosuresController;


    @Autowired
    public void setCashierClosuresController(CashierClosuresController cashierClosuresController) { this.cashierClosuresController = cashierClosuresController; }

    @RequestMapping(method = RequestMethod.POST)
    public CashierClosuresCreationWrapper createCashierClosures() throws LastCashierClosureIsOpenYetException {
        if(!cashierClosuresController.isLastCashierClosuresClosed() ) {
            throw new LastCashierClosureIsOpenYetException();
        }

        return new CashierClosuresCreationWrapper( cashierClosuresController.createCashierClosures() );
    }

    @RequestMapping(method = RequestMethod.GET, path = Uris.CASHIER_CLOSURES_LAST )
    public CashierClosuresWrapper getLastCashierClosure() throws NotExistsCashierClosuresException {
    	if(cashierClosuresController.getLastCashierClosure() == null){
    		throw new NotExistsCashierClosuresException();
    	}
    		
    	return new CashierClosuresWrapper( cashierClosuresController.getLastCashierClosure() );
    
    }

    @RequestMapping(method = RequestMethod.PUT, path = Uris.CASHIER_CLOSURES_CLOSE )
    public CashierClosuresWrapper closeCashierRequest(@RequestBody int amount, @RequestBody String comment) throws LastCashierClosureIsClosedException {
        if(cashierClosuresController.isLastCashierClosuresClosed()) {
            throw new LastCashierClosureIsClosedException();
        }

        return new CashierClosuresWrapper( cashierClosuresController.closeCashierRequest(amount, comment) );
    }

    @RequestMapping(method = RequestMethod.PUT, path = Uris.CASHIER_CLOSURES_DEPOSIT)
    public CashierClosuresWrapper depositCashierRequest(@RequestBody int amount) throws LastCashierClosureIsClosedException {
        if(cashierClosuresController.isLastCashierClosuresClosed()) {
            throw new LastCashierClosureIsClosedException();
        }
        return new CashierClosuresWrapper( cashierClosuresController.depositCashierRequest(amount));
    }

    @RequestMapping(method = RequestMethod.PUT, path = Uris.CASHIER_CLOSURES_WITHDRAW )
    public CashierClosuresWrapper withDrawCashierRequest(@RequestBody int amount) throws LastCashierClosureIsClosedException {
        if(cashierClosuresController.isLastCashierClosuresClosed()) {
            throw new LastCashierClosureIsClosedException();
        }

        return new CashierClosuresWrapper( cashierClosuresController.withDrawCashierRequest(amount));

    }


}
