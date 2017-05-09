import { browser, element, by } from 'protractor';

export class PaymentView {

  navigateTo() {
    return browser.get('/home/payment');
  }

  clickCashPaymentButton() {
    return element(by.id('cashPaymentButton')).click();
  }

  clickMoneyButton(quantity:number) {
    return element(by.id(`number${quantity}`)).click();
  }

  clickRemoveMoneyButton(quantity:number) {
    return element(by.id(`remove${quantity}`)).click();
  }

  getMoneyChargedText() {
    return element(by.id(`moneyCharged`)).getText();
  }

}