package services;

import java.util.ArrayList;
import java.util.List;

import entities.core.Article;
import entities.core.CashierClosure;
import entities.core.CategoryComponent;
import entities.core.CategoryComposite;
import entities.core.Embroidery;
import entities.core.Invoice;
import entities.core.ProductCategory;
import entities.core.Provider;
import entities.core.TextilePrinting;
import entities.core.Ticket;
import entities.core.Voucher;
import entities.users.Authorization;
import entities.users.Token;
import entities.users.User;

public class TpvGraph {

    private List<User> userList;

    private List<Authorization> authorizationList;

    private List<Token> tokenList;

    private List<Voucher> voucherList;

    private List<Provider> providerList;

    private List<Article> articleList;

    private List<Embroidery> embroideryList;

    private List<TextilePrinting> textilePrintingList;

    private List<Ticket> ticketList;

    private List<Invoice> invoiceList;

    private List<CategoryComponent> categoryComponentList;

    private List<CashierClosure> cashierClosureList;
    
    private List<CategoryComposite> categoryCompositeList;
    
    private List<ProductCategory>  productCategoryList;

    public TpvGraph() {
        super();
        userList = new ArrayList<>();
        authorizationList = new ArrayList<>();
        tokenList = new ArrayList<>();
        voucherList = new ArrayList<>();
        articleList = new ArrayList<>();
        embroideryList = new ArrayList<>();
        textilePrintingList = new ArrayList<>();
        providerList = new ArrayList<>();
        ticketList = new ArrayList<>();
        invoiceList = new ArrayList<>();

        categoryComponentList = new ArrayList<>();
        productCategoryList = new ArrayList<>();
        cashierClosureList = new ArrayList<>();

    }

    public List<User> getUserList() {
        return userList;
    }

    public void setUserList(List<User> userList) {
        this.userList = userList;
    }

    public List<Authorization> getAuthorizationList() {
        return authorizationList;
    }

    public void setAuthorizationList(List<Authorization> authorizationList) {
        this.authorizationList = authorizationList;
    }

    public List<Token> getTokenList() {
        return tokenList;
    }

    public void setTokenList(List<Token> tokenList) {
        this.tokenList = tokenList;
    }

    public List<Voucher> getVoucherList() {
        return voucherList;
    }

    public void setVoucherList(List<Voucher> voucherList) {
        this.voucherList = voucherList;
    }

    public List<Provider> getProviderList() {
        return providerList;
    }

    public void setProviderList(List<Provider> providerList) {
        this.providerList = providerList;
    }

    public List<Article> getArticleList() {
        return articleList;
    }

    public void setArticleList(List<Article> articleList) {
        this.articleList = articleList;
    }

    public List<Embroidery> getEmbroideryList() {
        return embroideryList;
    }

    public void setEmbroideryList(List<Embroidery> embroideryList) {
        this.embroideryList = embroideryList;
    }

    public List<TextilePrinting> getTextilePrintingList() {
        return textilePrintingList;
    }

    public void setTextilePrintingList(List<TextilePrinting> textilePrintingList) {
        this.textilePrintingList = textilePrintingList;
    }

    public List<Ticket> getTicketList() {
        return ticketList;
    }

    public void setTicketList(List<Ticket> ticketList) {
        this.ticketList = ticketList;
    }

    public List<Invoice> getInvoiceList() {
        return invoiceList;
    }

    public void setInvoiceList(List<Invoice> invoiceList) {
        this.invoiceList = invoiceList;
    }

    public List<CategoryComponent> getCategoryComponentList() {
        return categoryComponentList;
    }

    public void setCategoryComponentList(List<CategoryComponent> categoryComponentList) {
        this.categoryComponentList = categoryComponentList;
    }

    public List<CashierClosure> getCashierClosureList() {
        return cashierClosureList;
    }

    public void setCashierClosureList(List<CashierClosure> cashierClosureList) {
        this.cashierClosureList = cashierClosureList;
    }

    public List<CategoryComposite> getCategoryCompositeList() {
        return categoryCompositeList;
    }

    public void setCategoryCompositeList(List<CategoryComposite> categoryCompositeList) {
        this.categoryCompositeList = categoryCompositeList;
    }

    public List<ProductCategory> getProductCategoryList() {
        return productCategoryList;
    }

    public void setProductCategoryList(List<ProductCategory> productCategoryList) {
        this.productCategoryList = productCategoryList;
    }

    
}
