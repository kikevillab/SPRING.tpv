package controllers;

import daos.core.CashierClosureDao;
import entities.core.CashierClosure;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.util.GregorianCalendar;

@Controller
public class CashierClosuresController {

    @Autowired
    private CashierClosureDao cashierClosureDao;

    public CashierClosure createCashierClosures() {
        CashierClosure cashierClosure = new CashierClosure();
        cashierClosure.setAmount(getLastCashierClosureAmount());
        cashierClosure.setOpeningDate(new GregorianCalendar());
        cashierClosureDao.saveAndFlush(cashierClosure);
        return cashierClosure;
    }

    private double getLastCashierClosureAmount() {
        return getLastCashierClosure() != null ? getLastCashierClosure().getAmount() : 0;
    }

    public boolean isLastCashierClosuresClosed() {
        if (this.getLastCashierClosure() == null) {
            return true;
        }
        return getLastCashierClosure().getClosureDate() != null;
    }

    public CashierClosure getLastCashierClosure() throws NullPointerException {
        return cashierClosureDao.findFirstByOrderByOpeningDateDesc();
    }

    public CashierClosure closeCashierRequest(double amount, String comment) {
        CashierClosure lastCashierClosure = this.getLastCashierClosure();
        lastCashierClosure.setAmount(amount);
        lastCashierClosure.setComment(comment);
        lastCashierClosure.setClosureDate(new GregorianCalendar());

        cashierClosureDao.saveAndFlush(lastCashierClosure);

        return lastCashierClosure;
    }

    public CashierClosure depositCashierRequest(double amount) {
        CashierClosure lastCashierClosures = this.getLastCashierClosure();
        lastCashierClosures.setAmount(getLastCashierClosureAmount() + amount);
        cashierClosureDao.saveAndFlush(lastCashierClosures);

        return lastCashierClosures;
    }

    public CashierClosure withDrawCashierRequest(double amount) {
        CashierClosure lastCashierClosures = this.getLastCashierClosure();
        lastCashierClosures.setAmount(getLastCashierClosureAmount() - amount);
        cashierClosureDao.saveAndFlush(lastCashierClosures);

        return lastCashierClosures;
    }
}
