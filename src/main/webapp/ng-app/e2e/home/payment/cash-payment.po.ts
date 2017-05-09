import { element, by } from 'protractor';

export class CalculatorView {

  clickNumber(number:number) {
    return element(by.id(`number${number}Button`)).click(); 
  }

  clickSumButton() {
    return element(by.id('sumButton')).click();
  }

  clickDecimalsButton() {
    return element(by.id('decimalsButton')).click(); 
  }

  clickCalculateButton() {
    return element(by.id('calculateButton')).click(); 
  }

  clickResetButton() {
    return element(by.id('resetButton')).click(); 
  }

  getResultText() {
    return element(by.id('calculatorResult')).getText(); 
  }

}

