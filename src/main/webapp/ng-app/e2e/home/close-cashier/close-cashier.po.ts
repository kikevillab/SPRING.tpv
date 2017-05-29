/**
  * @author Sergio Banegas Cortijo
  * Github: https://github.com/sergiobanegas 
*/
import { browser, element, by } from 'protractor';

export class CloseCashierView {

  navigateTo() {
    return browser.get('/home/close-cashier');
  }

  fillForm() {
    element(by.id('balance-counted-input')).sendKeys(1120);
  }

  getCloseCashierButton() {
    return element(by.id('close-cashier-button'));
  }

  clickCloseCashierButton() {
    return element(by.id('close-cashier-button')).click();
  }

}