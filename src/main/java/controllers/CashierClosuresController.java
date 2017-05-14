package controllers;

import daos.core.CashierClosuresDao;
import entities.core.CashierClosures;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import wrappers.CashierClosuresWrapper;

import java.util.GregorianCalendar;

@Controller
public class CashierClosuresController {

    private CashierClosuresDao cashierClosuresDao;

    @Autowired
    public void setCashierClosuresDao(CashierClosuresDao cashierClosures) {
        this.cashierClosuresDao = cashierClosuresDao;
    }


    public CashierClosures createCashierClosures() {

        CashierClosures cashierClosures = new CashierClosures();

        cashierClosures.setAmount(cashierClosuresDao.findFirstByOrderByOpeningDateDesc().getAmount());
        cashierClosures.setOpeningDate(new GregorianCalendar());
        cashierClosuresDao.saveAndFlush(cashierClosures);

        return cashierClosures;
    }

    public boolean isLastCashierClosuresClosed() {
        return cashierClosuresDao.findFirstByOrderByOpeningDateDesc().getClosureDate() != null;
    }

    public CashierClosures getLastCashierClosure() {
        return cashierClosuresDao.findFirstByOrderByOpeningDateDesc();
    }

    public CashierClosures closeCashierRequest(int amount, String comment) {
        CashierClosures lastCashierClosure = this.getLastCashierClosure();
        lastCashierClosure.setAmount(amount);
        lastCashierClosure.setComment(comment);
        lastCashierClosure.setClosureDate(new GregorianCalendar());

        cashierClosuresDao.saveAndFlush(lastCashierClosure);

        return lastCashierClosure;
    }

    public CashierClosures depositCashierRequest(int amount) {
        CashierClosures lastCashierClosures = this.getLastCashierClosure();
        lastCashierClosures.setAmount(lastCashierClosures.getAmount() + amount);
        cashierClosuresDao.saveAndFlush(lastCashierClosures);

        return lastCashierClosures;
    }

    public CashierClosures withDrawCashierRequest(int amount) {
        CashierClosures lastCashierClosures = this.getLastCashierClosure();
        lastCashierClosures.setAmount(lastCashierClosures.getAmount() - amount);
        cashierClosuresDao.saveAndFlush(lastCashierClosures);

        return lastCashierClosures;
    }
}
