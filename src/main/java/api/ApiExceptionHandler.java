package api;

import java.io.FileNotFoundException;

import org.apache.logging.log4j.LogManager;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import api.exceptions.AlreadyExistUserFieldException;
import api.exceptions.ApiException;
import api.exceptions.ArticleNotFoundException;
import api.exceptions.CategoryComponentNotFound;
import api.exceptions.EmbroideryNotFoundException;
import api.exceptions.EmptyShoppingListException;
import api.exceptions.ErrorMessage;
import api.exceptions.InvalidProductAmountInNewTicketException;
import api.exceptions.InvalidProductAmountInUpdateTicketException;
import api.exceptions.InvalidProductDiscountException;
import api.exceptions.InvalidUserFieldException;
import api.exceptions.InvoiceDoesNotAllowNotClosedTicketsException;
import api.exceptions.InvoiceNotFoundException;
import api.exceptions.LastCashierClosureIsClosedException;
import api.exceptions.LastCashierClosureIsOpenYetException;
import api.exceptions.MalformedDateException;
import api.exceptions.MalformedHeaderException;
import api.exceptions.NotEnoughStockException;
import api.exceptions.NotExistsCashierClosuresException;
import api.exceptions.NotFoundProductCodeException;
import api.exceptions.NotFoundProductCodeInTicketException;
import api.exceptions.NotFoundTicketReferenceException;
import api.exceptions.NotFoundUserIdException;
import api.exceptions.NotFoundUserMobileException;
import api.exceptions.NotFoundYamlFileException;
import api.exceptions.TextilePrintingNotFoundException;
import api.exceptions.TicketHasInvalidUserException;
import api.exceptions.TicketIsAlreadyAssignedToInvoiceException;
import api.exceptions.TicketNotFoundException;
import api.exceptions.UnauthorizedException;
import api.exceptions.VoucherAlreadyConsumedException;
import api.exceptions.VoucherHasExpiredException;
import api.exceptions.VoucherNotFoundException;

@ControllerAdvice
public class ApiExceptionHandler {

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler({NotFoundUserIdException.class, NotFoundYamlFileException.class, NotFoundProductCodeException.class,
            FileNotFoundException.class, TicketNotFoundException.class, InvoiceNotFoundException.class, NotFoundUserMobileException.class,
            NotFoundTicketReferenceException.class, NotFoundProductCodeInTicketException.class, VoucherNotFoundException.class,
            EmbroideryNotFoundException.class, ArticleNotFoundException.class, TextilePrintingNotFoundException.class,
            NotFoundTicketReferenceException.class, NotFoundProductCodeInTicketException.class, NotExistsCashierClosuresException.class,
            CategoryComponentNotFound.class})

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
    @ExceptionHandler({MalformedHeaderException.class, InvalidUserFieldException.class, EmptyShoppingListException.class,
            InvalidProductAmountInNewTicketException.class, InvalidProductAmountInUpdateTicketException.class,
            InvalidProductDiscountException.class, InvoiceDoesNotAllowNotClosedTicketsException.class,
            TicketIsAlreadyAssignedToInvoiceException.class, TicketHasInvalidUserException.class, MalformedDateException.class})
    @ResponseBody
    public ErrorMessage badRequest(ApiException exception) {
        ErrorMessage apiErrorMessage = new ErrorMessage(exception);
        return apiErrorMessage;
    }

    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler({AlreadyExistUserFieldException.class, AlreadyExistUserFieldException.class, NotEnoughStockException.class,
            VoucherAlreadyConsumedException.class, VoucherHasExpiredException.class, LastCashierClosureIsClosedException.class,
            LastCashierClosureIsOpenYetException.class})

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
