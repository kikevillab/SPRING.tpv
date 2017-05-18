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
    element(by.id('amountInput')).sendKeys(1);
    element(by.id('cashierClosureRadioButton')).click();
  }

  getSubmitButton() {
    return element(by.id('submitButton'));
  }

}