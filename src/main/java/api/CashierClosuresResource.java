package api;

import api.exceptions.LastCashierClosedException;
import api.exceptions.LastCashierOpenException;
import api.exceptions.CashierClosingNotFoundException;
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
    public void setCashierClosuresController(CashierClosuresController cashierClosuresController) {
        this.cashierClosuresController = cashierClosuresController;
    }

    @RequestMapping(method = RequestMethod.POST)
    public CashierClosuresCreationWrapper createCashierClosures() throws LastCashierOpenException {
        if (!cashierClosuresController.isLastCashierClosuresClosed()) {
            throw new LastCashierOpenException();
        }

        return new CashierClosuresCreationWrapper(cashierClosuresController.createCashierClosures());
    }

    @RequestMapping(method = RequestMethod.GET, path = Uris.CASHIER_CLOSURES_LAST)
    public CashierClosuresWrapper getLastCashierClosure() throws CashierClosingNotFoundException {
        if (cashierClosuresController.getLastCashierClosure() == null) {
            throw new CashierClosingNotFoundException();
        }

        return new CashierClosuresWrapper(cashierClosuresController.getLastCashierClosure());
    }

    @RequestMapping(method = RequestMethod.PUT, path = Uris.CASHIER_CLOSURES_CLOSE)
    public CashierClosuresWrapper closeCashierRequest(@RequestBody CashierClosingWrapper cashierClosingWrapper)
            throws LastCashierClosedException {
        if (cashierClosuresController.isLastCashierClosuresClosed()) {
            throw new LastCashierClosedException();
        }

        return new CashierClosuresWrapper(
                cashierClosuresController.closeCashierRequest(cashierClosingWrapper.getAmount(), cashierClosingWrapper.getComment()));
    }

    @RequestMapping(method = RequestMethod.PUT, path = Uris.CASHIER_CLOSURES_DEPOSIT)
    public CashierClosuresWrapper depositCashierRequest(@RequestBody AmountWrapper amountWrapper)
            throws LastCashierClosedException {
        if (cashierClosuresController.isLastCashierClosuresClosed()) {
            throw new LastCashierClosedException();
        }
        return new CashierClosuresWrapper(cashierClosuresController.depositCashierRequest(amountWrapper.getAmount()));
    }

    @RequestMapping(method = RequestMethod.PUT, path = Uris.CASHIER_CLOSURES_WITHDRAW)
    public CashierClosuresWrapper withDrawCashierRequest(@RequestBody AmountWrapper amountWrapper)
            throws LastCashierClosedException {
        if (cashierClosuresController.isLastCashierClosuresClosed()) {
            throw new LastCashierClosedException();
        }

        return new CashierClosuresWrapper(cashierClosuresController.withDrawCashierRequest(amountWrapper.getAmount()));

    }

}
