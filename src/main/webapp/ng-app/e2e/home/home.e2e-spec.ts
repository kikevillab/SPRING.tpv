/**
  * @author Sergio Banegas Cortijo
  * Github: https://github.com/sergiobanegas 
*/
import { HomePage } from './home.po';
import { CartView } from './cart/cart.po';
import { CalculatorView } from './cart/calculator.po';
import { PaymentView } from './payment/payment.po';
import { MovementView } from './movement/movement.po';
import { CloseCashierView } from './close-cashier/close-cashier.po';


describe('Page: Home', () => {

	let page: HomePage;
	let cartView: CartView;
	let calculatorView: CalculatorView;
	let paymentView: PaymentView;
	let movementView: MovementView;
	let closeCashierView: CloseCashierView;

	beforeAll(() => {
		page = new HomePage();
		cartView = new CartView();
		calculatorView = new CalculatorView();
		paymentView = new PaymentView();
		movementView = new MovementView();
		closeCashierView = new CloseCashierView();
		page.navigateTo();
		page.openCashier();
	});

	it(`should display a 'Products' title'`, () => {
		expect(page.getPageTitleText()).toEqual('Products');
	});

	it(`should display the product with code '84000001113' when submitted`, () => {
		page.clickCartButton();
		let totalPriceBefore = cartView.getTotalValueText();
		cartView.submitProductCode('84000001113');
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
		cartView.submitProductCode('84000001113');
		cartView.clickClearCartButton();
		expect(cartView.getCartInputs()).toBe(0);
	});


	it(`should show the movement submit button after filling the form`, () => {
		movementView.navigateTo();
		expect(movementView.getSubmitButton().isEnabled()).toBe(false);
		movementView.fillForm();
		expect(movementView.getSubmitButton().isEnabled()).toBe(true);
	});

	it(`should show the close cashier button after filling the form`, () => {
		closeCashierView.navigateTo();
		closeCashierView.fillForm();
		expect(closeCashierView.getCloseCashierButton().isEnabled()).toBe(true);
	});

	afterAll(() => {
		closeCashierView.clickCloseCashierButton();
	});

});
