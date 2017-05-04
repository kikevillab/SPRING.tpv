import { Component, OnDestroy, NgModule } from '@angular/core';
import { Subscription } from 'rxjs/Subscription';

@Component({
  selector: 'calculator-view',
  templateUrl: './calculator.component.html',
  styles: [`
    b {
      font-size:200%;
    }
    #currentOperator {
      float:right;
    }
    div > div > div {
      padding:0.5em;
    }
  `]
})

export class CalculatorComponent {

  currentOperator = null;
  firstValue = '';
  secondValue = '';
  result = '0';
  decimalsActivated = false;

  ngOnInit(){
  	this.reset();
  }

  reset(){
  	this.currentOperator = null;
  	this.firstValue = '';
  	this.secondValue = '';
  	this.result = '0';
  	this.decimalsActivated = false;
  }

  setValue(number) {
    if (this.currentOperator == null){
      this.firstValue = this.firstValue + number;
      this.result = this.firstValue;
    } else {
      this.secondValue = this.secondValue+ number;
      this.result= this.secondValue;
    }
  }

  setOperator(operator) {
  	if (this.currentOperator != null){
  		this.calculate();
  	}
  	this.currentOperator = operator;
  }

  setDecimals(){
  	if (this.currentOperator == null && this.firstValue.indexOf('.')==-1){
  		this.firstValue+='.';
  		this.decimalsActivated = true;
  	}else if (this.secondValue.indexOf('.')==-1){
  		this.secondValue+='.';
  		this.decimalsActivated = true;
  	}
  }

  calculate(){
  	switch(this.currentOperator){
  		case '+':
      this.firstValue = (Number(this.firstValue) + Number(this.secondValue)).toString();
      break;
      case '-':
      this.firstValue = (Number(this.firstValue) - Number(this.secondValue)).toString();;
      break;
      case '*':
      this.firstValue = (Number(this.firstValue) * Number(this.secondValue)).toString();;
      break;
      case '/':
      this.firstValue = (Number(this.firstValue) / Number(this.secondValue)).toString();;
      break;
      default:
      break;
    }
    this.secondValue = '';
    this.result = this.firstValue;
    this.decimalsActivated = false;
    this.currentOperator = '=';
  }

}
