/**
  * @author Sergio Banegas Cortijo
  * Github: https://github.com/sergiobanegas 
*/
import { browser, element, by } from 'protractor';

export class PurchaseView {

  navigateTo() {
    return browser.get('/home/purchase/payment');
  }

  clickCashPaymentButton() {
    return element(by.id('cash-paymentButton')).click();
  }

  clickMoneyButton(quantity:number) {
    return element(by.id(`number-${quantity}`)).click();
  }

  clickRemoveMoneyButton(quantity:number) {
    return element(by.id(`remove-${quantity}`)).click();
  }

  getMoneyChargedText() {
    return element(by.id(`money-charged`)).getText();
  }

}