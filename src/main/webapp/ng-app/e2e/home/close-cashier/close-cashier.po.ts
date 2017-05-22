/**
  * @author Sergio Banegas Cortijo
  * Github: https://github.com/sergiobanegas 
*/
import { browser, element, by } from 'protractor';

export class CloseCashierView {

  navigateTo() {
    return browser.get('/home/closecashier');
  }

  fillForm() {
    element(by.id('balanceCountedInput')).sendKeys(1120);
  }

  getCloseCashierButton() {
    return element(by.id('closeCashierButton'));
  }

  clickCloseCashierButton() {
    return element(by.id('closeCashierButton')).click();
  }

}