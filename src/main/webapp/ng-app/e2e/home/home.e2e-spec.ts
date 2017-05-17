/**
  * @author Sergio Banegas Cortijo
  * Github: https://github.com/sergiobanegas 
*/
import { HomePage } from './home.po';
import { CartView } from './cart/cart.po';
import { CalculatorView } from './cart/calculator.po';
import { PaymentView } from './payment/payment.po';
import { MovementView } from './movement/movement.po';

describe('Page: Home', () => {

	let page: HomePage;
	let cartView: CartView;
	let calculatorView: CalculatorView;
	let paymentView: PaymentView;
	let movementView: MovementView;

	beforeAll(() => {
		page = new HomePage();
		cartView = new CartView();
		calculatorView = new CalculatorView();
		paymentView = new PaymentView();
		movementView = new MovementView();
		page.navigateTo();
	});

	it(`should display a 'Products' title'`, () => {
		expect(page.getPageTitleText()).toEqual('Products');
	});

	it(`should display the product with code 'article0' when submitted`, () => {
		page.clickCartButton();
		let totalPriceBefore = cartView.getTotalValueText();
		cartView.submitProductCode('article0');
		let totalPriceAfter = cartView.getTotalValueText();
		expect(totalPriceBefore).not.toBe(totalPriceAfter);
	});

	it(`should open the calculator and calculate a sum correctly`, () => {
		cartView.clickOpenCalculatorButton();
		calculatorView.clickNumber(4);
		calculatorView.clickDecimalsButton();
		calculatorView.clickNumber(2);
		calculatorView.clickNumber(5);
		calculatorView.clickSumButton();
		calculatorView.clickNumber(7);
		calculatorView.clickCalculateButton();
		expect(calculatorView.getResultText()).toBe('11.25');
	});

	it(`should empty the cart when 'X' button is clicked`, () => {
		page.navigateTo();
		page.clickCartButton();
		cartView.submitProductCode('article0');
		cartView.clickClearCartButton();
		expect(cartView.getCartInputs()).toBe(0);
	});


	it(`should redirect to home after make a movement`, () => {
		movementView.navigateTo();
		expect(movementView.getSubmitButton().isEnabled()).toBe(false);
		movementView.fillForm();
		expect(movementView.getSubmitButton().isEnabled()).toBe(true);
	});

	// Commented because the dialog opening delay makes impossible to handle it correctly
	// it(`should do the cash payment properly`, (done) => {
	// 	paymentView.navigateTo();
	// 	paymentView.clickCashPaymentButton();
	// 	setTimeout(()=>{
	// 		paymentView.clickMoneyButton(1);
	// 		paymentView.clickMoneyButton(20);
	// 		paymentView.clickMoneyButton(5);
	// 		paymentView.clickMoneyButton(0.5);
	// 		expect(paymentView.getMoneyChargedText()).toEqual('26.5');
	// 		paymentView.clickRemoveMoneyButton(1);
	// 		expect(paymentView.getMoneyChargedText()).toEqual('25.5');
	// 		done();
	// 	}, 7000);
	// });

	afterAll(() => {
		cartView.clickClearCartButton();
	});

});
