package vn.byt.qlds.exception;

import vn.byt.qlds.core.base.ErrorResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings({"unchecked","rawtypes"})
@RestControllerAdvice
public class GlobalControllerExceptionHandler extends ResponseEntityExceptionHandler {


    @ExceptionHandler(Exception.class)
    public final ResponseEntity<Object> handleAllExceptions(Exception ex, WebRequest request) {
        List<String> details = new ArrayList<>();
        details.add(ex.getLocalizedMessage());
        ErrorResponse error = new ErrorResponse(500, ex.getMessage());
        return new ResponseEntity(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse handleNotFound(NotFoundException ex, WebRequest request) {
        List<String> details = new ArrayList<>();
        details.add(ex.getLocalizedMessage());
        return new ErrorResponse(404, details.get(0));
    }

    @ExceptionHandler(BadCredentialsException.class)
    @ResponseStatus(HttpStatus.PRECONDITION_FAILED)
    public ErrorResponse handleBadCredentials(BadCredentialsException ex, WebRequest request) {
        List<String> details = new ArrayList<>();
        details.add(ex.getLocalizedMessage());
        return new ErrorResponse(412, details.get(0));
    }

    @ExceptionHandler(BadRequestException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleNotFound(BadRequestException ex, WebRequest request) {
        List<String> details = new ArrayList<>();
        details.add(ex.getLocalizedMessage());
        return new ErrorResponse(400, details.get(0));
    }

    @ExceptionHandler(InvalidTokenException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ErrorResponse handleTokenInvalid(RuntimeException ex) {
        List<String> details = new ArrayList<>();
        details.add(ex.getLocalizedMessage());
        return new ErrorResponse(401, details.get(0));
    }

    @ExceptionHandler(UsernameNotFoundException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ErrorResponse handleUserNotFound(RuntimeException ex) {
        List<String> details = new ArrayList<>();
        details.add(ex.getLocalizedMessage());
        return new ErrorResponse(401, details.get(0));
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        List<String> details = new ArrayList<>();
        for(ObjectError error : ex.getBindingResult().getAllErrors()) {
            details.add(error.getDefaultMessage());
        }
        ErrorResponse error = new ErrorResponse(400, details.get(0));
        return new ResponseEntity(error, HttpStatus.BAD_REQUEST);
    }
}