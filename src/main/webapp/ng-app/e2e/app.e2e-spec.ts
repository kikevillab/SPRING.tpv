import { TpvPage } from './app.po';

describe('tpv App', () => {
  let page: TpvPage;

  beforeEach(() => {
    page = new TpvPage();
  });

  it('should display message saying app works', () => {
    page.navigateTo();
    expect(page.getParagraphText()).toEqual('app works!');
  });
});
