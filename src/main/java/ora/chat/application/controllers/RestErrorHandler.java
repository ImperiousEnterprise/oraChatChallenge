package ora.chat.application.controllers;

import ora.chat.application.models.wrapper.OutputtedErrors;

import org.springframework.context.annotation.Bean;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.List;
import java.util.Locale;
import java.util.Set;

@ControllerAdvice
public class RestErrorHandler {


    @Bean(name = "messageSource")
    public ReloadableResourceBundleMessageSource messageSource() {
        ReloadableResourceBundleMessageSource messageBundle = new ReloadableResourceBundleMessageSource();
        messageBundle.setBasename("classpath:messages/messages");
        messageBundle.setDefaultEncoding("UTF-8");
        return messageBundle;
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public OutputtedErrors processValidationError(MethodArgumentNotValidException ex) {
        BindingResult result = ex.getBindingResult();
        List<FieldError> fieldErrors = result.getFieldErrors();
        return processFieldError(fieldErrors);

    }

    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public OutputtedErrors processValidationError(ConstraintViolationException e) {
        Set<ConstraintViolation<?>> violations = e.getConstraintViolations();
        return processSetFieldError(violations);
    }
    @ExceptionHandler(DataIntegrityViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public OutputtedErrors processValidationError(DataIntegrityViolationException e) {
        OutputtedErrors message = new OutputtedErrors("DataIntegrityViolationException");
        message.addFieldError("DBError",e.getLocalizedMessage());
        return message;
    }


    private OutputtedErrors processFieldError( List<FieldError> error) {
        OutputtedErrors message = new OutputtedErrors("MethodArgumentNotValidException");
        if (error != null) {
            LocaleContextHolder.setLocale(Locale.ENGLISH);
            Locale currentLocale = LocaleContextHolder.getLocale();
            for (FieldError fieldError: error) {
                String localizedErrorMessage =  messageSource().getMessage(fieldError.getDefaultMessage(), null, currentLocale);
                //String attribute = fieldError.getField().substring(fieldError.getField().indexOf(".")+1, fieldError.getField().lastIndexOf("."));
                message.addFieldError(fieldError.getField(), localizedErrorMessage);
            }
        }
        return message;
    }

    private OutputtedErrors processSetFieldError( Set<ConstraintViolation<?>> error) {
        OutputtedErrors message = new OutputtedErrors("ConstraintViolationException");
        if (error != null) {
            Locale currentLocale = LocaleContextHolder.getLocale();
            for (ConstraintViolation fieldError: error) {
                String localizedErrorMessage =  messageSource().getMessage(fieldError.getMessage(), null, currentLocale);
                String attribute = fieldError.getMessageTemplate().substring(fieldError.getMessageTemplate().indexOf(".")+1, fieldError.getMessageTemplate().lastIndexOf("."));
                message.addFieldError(attribute, localizedErrorMessage);
            }
        }
        return message;
    }
}