/**
  * @author Sergio Banegas Cortijo
  * Github: https://github.com/sergiobanegas 
*/
import { browser, element, by } from 'protractor';

export class MovementView {

  navigateTo() {
    return browser.get('/home/movement');
  }

  fillForm() {
    element(by.id('amount-input')).sendKeys(1);
    element(by.id('cashier-closure-radio-button')).click();
  }

  getSubmitButton() {
    return element(by.id('submit-button'));
  }

}