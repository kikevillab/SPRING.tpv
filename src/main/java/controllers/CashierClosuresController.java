package controllers;

import daos.core.CashierClosuresDao;
import entities.core.CashierClosures;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.util.GregorianCalendar;

@Controller
public class CashierClosuresController {

	@Autowired
	private CashierClosuresDao cashierClosuresDao;

	public CashierClosures createCashierClosures() {

		CashierClosures cashierClosures = new CashierClosures();

		cashierClosures.setAmount(getLastCashierClosureAmount());
		cashierClosures.setOpeningDate(new GregorianCalendar());
		cashierClosuresDao.saveAndFlush(cashierClosures);

		return cashierClosures;
	}

	private int getLastCashierClosureAmount() {
		return getLastCashierClosure() != null ? getLastCashierClosure().getAmount() : 0;
	}

	public boolean isLastCashierClosuresClosed() {
		return cashierClosuresDao.findFirstByOrderByOpeningDateDesc().getClosureDate() != null;
	}

	public CashierClosures getLastCashierClosure() {
		return cashierClosuresDao.findFirstByOrderByOpeningDateDesc();
	}

	public CashierClosures closeCashierRequest(int amount, String comment) {
		CashierClosures lastCashierClosure = this.getLastCashierClosure();
		lastCashierClosure.setAmount(getLastCashierClosureAmount() + amount);
		lastCashierClosure.setComment(comment);
		lastCashierClosure.setClosureDate(new GregorianCalendar());

		cashierClosuresDao.saveAndFlush(lastCashierClosure);

		return lastCashierClosure;
	}

	public CashierClosures depositCashierRequest(int amount) {
		CashierClosures lastCashierClosures = this.getLastCashierClosure();
		lastCashierClosures.setAmount(getLastCashierClosureAmount() + amount);
		cashierClosuresDao.saveAndFlush(lastCashierClosures);

		return lastCashierClosures;
	}

	public CashierClosures withDrawCashierRequest(int amount) {
		CashierClosures lastCashierClosures = this.getLastCashierClosure();
		lastCashierClosures.setAmount(getLastCashierClosureAmount() - amount);
		cashierClosuresDao.saveAndFlush(lastCashierClosures);

		return lastCashierClosures;
	}
}
