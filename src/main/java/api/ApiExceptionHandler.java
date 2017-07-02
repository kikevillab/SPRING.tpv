package api;

import java.io.FileNotFoundException;

import org.apache.logging.log4j.LogManager;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import api.exceptions.UserFieldAlreadyExistException;
import api.exceptions.ApiException;
import api.exceptions.ArticleNotFoundException;
import api.exceptions.CategoryComponentNotFoundException;
import api.exceptions.EmbroideryNotFoundException;
import api.exceptions.TicketShoppingListEmptyException;
import api.exceptions.ErrorMessage;
import api.exceptions.TicketShoppingAmountInvalidFieldException;
import api.exceptions.TicketShoppingAmountForUpdateInvalidFieldException;
import api.exceptions.TicketShoppingDiscountInvalidFieldException;
import api.exceptions.UserInvalidFieldException;
import api.exceptions.InvoiceNotFoundException;
import api.exceptions.LastCashierClosedException;
import api.exceptions.LastCashierOpenException;
import api.exceptions.DateMalformedException;
import api.exceptions.HeaderMalformedException;
import api.exceptions.StockNotEnoughException;
import api.exceptions.CashierClosingNotFoundException;
import api.exceptions.ProductCodeNotFoundException;
import api.exceptions.TicketProductCodeNotFoundException;
import api.exceptions.TicketReferenceNotFoundException;
import api.exceptions.UserIdNotFoundException;
import api.exceptions.UserMobileNotFoundException;
import api.exceptions.FileNameNotFoundException;
import api.exceptions.TextilePrintingNotFoundException;
import api.exceptions.TicketUserInvalidException;
import api.exceptions.TicketInvoiceAlreadyAssignedException;
import api.exceptions.TicketNotFoundException;
import api.exceptions.UnauthorizedException;
import api.exceptions.VoucherAlreadyConsumedException;
import api.exceptions.VoucherHasExpiredException;
import api.exceptions.VoucherNotFoundException;

@ControllerAdvice
public class ApiExceptionHandler {

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler({UserIdNotFoundException.class, FileNameNotFoundException.class, ProductCodeNotFoundException.class,
            FileNotFoundException.class, TicketNotFoundException.class, InvoiceNotFoundException.class, UserMobileNotFoundException.class,
            TicketReferenceNotFoundException.class, TicketProductCodeNotFoundException.class, VoucherNotFoundException.class,
            EmbroideryNotFoundException.class, ArticleNotFoundException.class, TextilePrintingNotFoundException.class,
            TicketReferenceNotFoundException.class, TicketProductCodeNotFoundException.class, CashierClosingNotFoundException.class,
            CategoryComponentNotFoundException.class})

    @ResponseBody
    public ErrorMessage notFoundRequest(ApiException exception) {
        ErrorMessage apiErrorMessage = new ErrorMessage(exception);
        return apiErrorMessage;
    }

    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler({UnauthorizedException.class})
    public void unauthorized(ApiException exception) {
        ErrorMessage apiErrorMessage = new ErrorMessage(exception);
        LogManager.getLogger(this.getClass()).info("  ERROR: UNAUTHORIZED, " + apiErrorMessage);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({HeaderMalformedException.class, UserInvalidFieldException.class, TicketShoppingListEmptyException.class,
            TicketShoppingAmountInvalidFieldException.class, TicketShoppingAmountForUpdateInvalidFieldException.class,
            TicketShoppingDiscountInvalidFieldException.class, TicketInvoiceAlreadyAssignedException.class, TicketUserInvalidException.class,
            DateMalformedException.class})
    @ResponseBody
    public ErrorMessage badRequest(ApiException exception) {
        ErrorMessage apiErrorMessage = new ErrorMessage(exception);
        return apiErrorMessage;
    }

    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler({UserFieldAlreadyExistException.class, UserFieldAlreadyExistException.class, StockNotEnoughException.class,
            VoucherAlreadyConsumedException.class, VoucherHasExpiredException.class, LastCashierClosedException.class,
            LastCashierOpenException.class})

    @ResponseBody
    public ErrorMessage conflictRequest(ApiException exception) {
        ErrorMessage apiErrorMessage = new ErrorMessage(exception);
        return apiErrorMessage;
    }

    // @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    // @ExceptionHandler({Exception.class})
    // @ResponseBody
    // public ErrorMessage exception(Exception exception) {
    // ErrorMessage apiErrorMessage = new ErrorMessage(exception);
    // return apiErrorMessage;
    // }

}
