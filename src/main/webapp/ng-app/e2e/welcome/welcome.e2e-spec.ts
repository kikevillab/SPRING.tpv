import { WelcomePage } from './welcome.po';

describe('Page: Welcome', () => {
	
	let page: WelcomePage;

	beforeEach(() => {
		page = new WelcomePage();
	});

	it('should display a Welcome message', () => {
		page.navigateTo();
		expect(page.getParagraphText()).toEqual('Welcome page of TPV Online');
	});
});
