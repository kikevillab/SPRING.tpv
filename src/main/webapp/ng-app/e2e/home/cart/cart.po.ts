/**
  * @author Sergio Banegas Cortijo
  * Github: https://github.com/sergiobanegas 
*/
import { element, by } from 'protractor';

export class CartView {

  clickClearCartButton() {
    return element(by.id('clearCartButton')).click();
  }

  getTotalValueText() {
  	return element(by.id('totalPrice')).getText(); 
  }

  clickOpenCalculatorButton() {
    return element(by.id('openCalculatorButton')).click();
  }

  clickCheckoutButton() {
    return element(by.id('checkoutButton')).click();
  }

  submitProductCode(code:string) {
  	let codeInput = element(by.id('codeInput'));
  	codeInput.sendKeys(code);
  	return element(by.id('submitCodeInputButton')).click();
  }

  getProductsTable() {
  	return element(by.css('ngx-datatable'));
  }

  getCartInputs() {
  	return element.all(by.css('ngx-datatable input')).count();
  }

}