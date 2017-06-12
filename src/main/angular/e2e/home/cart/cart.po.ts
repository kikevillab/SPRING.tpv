/**
  * @author Sergio Banegas Cortijo
  * Github: https://github.com/sergiobanegas 
*/
import { element, by } from 'protractor';

export class CartView {

    clickClearCartButton() {
        return element(by.id('clear-cart-button')).click();
    }

    getTotalValueText() {
        return element(by.id('total-price')).getText();
    }

    clickOpenCalculatorButton() {
        return element(by.id('open-calculator-button')).click();
    }

    clickCheckoutButton() {
        return element(by.id('checkout-button')).click();
    }

    submitProductCode(code: string) {
        let codeInput = element(by.id('code-input'));
        codeInput.sendKeys(code);
        return element(by.id('submit-code-input-button')).click();
    }

    getProductsTable() {
        return element(by.css('ngx-datatable'));
    }

    getCartInputs() {
        return element.all(by.css('ngx-datatable input')).count();
    }

}