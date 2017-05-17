/**
  * @author Sergio Banegas Cortijo
  * Github: https://github.com/sergiobanegas 
*/
import { browser, element, by } from 'protractor';

export class HomePage {

	navigateTo() {
		return browser.get('/home');
	}
	
	getPageTitleText() {
		return element(by.id('pageTitle')).getText();
	}

	clickCartButton() {
		return element(by.id('cartButton')).click(); 
	}

}

