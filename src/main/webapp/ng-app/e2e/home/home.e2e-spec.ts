import { HomePage } from './home.po';
import { CartView } from './cart/cart.po';
import { CalculatorView } from './cart/calculator.po';

describe('Page: Home', () => {

	let page: HomePage;
	let cartView: CartView;
	let calculatorView: CalculatorView;

	beforeAll(() => {
		page = new HomePage();
		cartView = new CartView();
		calculatorView = new CalculatorView();
		page.navigateTo();
	});

	it('should display a Home title', () => {
		expect(page.getPageTitleText()).toEqual('Home');
	});

	it('should display the product with CODE1 when submitted', () => {
		page.clickCartButton();
		let totalPriceBefore = cartView.getTotalValueText();
		cartView.submitProductCode('article0');
		let totalPriceAfter = cartView.getTotalValueText();
		expect(totalPriceBefore).not.toBe(totalPriceAfter);
	});

	it(`should empty the cart when 'X' button is clicked`, () => {
		expect(cartView.getCartInputs()).not.toBe(0);
		cartView.clickClearCartButton();
		expect(cartView.getCartInputs()).toBe(0);
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

	afterAll(() => {
		cartView.clickClearCartButton();
	});

});
