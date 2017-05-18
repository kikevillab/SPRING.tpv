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

import wrappers.AmountWrapper;
import wrappers.CashierClosingWrapper;
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
    public CashierClosuresWrapper closeCashierRequest(@RequestBody CashierClosingWrapper cashierClosingWrapper) throws LastCashierClosureIsClosedException {
        if(cashierClosuresController.isLastCashierClosuresClosed()) {
            throw new LastCashierClosureIsClosedException();
        }

        return new CashierClosuresWrapper( cashierClosuresController.closeCashierRequest(cashierClosingWrapper.getAmount(), cashierClosingWrapper.getComment()) );
    }

    @RequestMapping(method = RequestMethod.PUT, path = Uris.CASHIER_CLOSURES_DEPOSIT)
    public CashierClosuresWrapper depositCashierRequest(@RequestBody AmountWrapper amountWrapper) throws LastCashierClosureIsClosedException {
        if(cashierClosuresController.isLastCashierClosuresClosed()) {
            throw new LastCashierClosureIsClosedException();
        }
        return new CashierClosuresWrapper( cashierClosuresController.depositCashierRequest(amountWrapper.getAmount()));
    }

    @RequestMapping(method = RequestMethod.PUT, path = Uris.CASHIER_CLOSURES_WITHDRAW )
    public CashierClosuresWrapper withDrawCashierRequest(@RequestBody AmountWrapper amountWrapper) throws LastCashierClosureIsClosedException {
        if(cashierClosuresController.isLastCashierClosuresClosed()) {
            throw new LastCashierClosureIsClosedException();
        }

        return new CashierClosuresWrapper( cashierClosuresController.withDrawCashierRequest(amountWrapper.getAmount()));

    }


}
