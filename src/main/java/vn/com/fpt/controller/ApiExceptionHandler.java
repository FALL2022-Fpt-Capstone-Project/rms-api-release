package vn.com.fpt.controller;

import io.sentry.Sentry;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import vn.com.fpt.common.BusinessException;
import vn.com.fpt.common.response.AppResponse;
import vn.com.fpt.common.response.BaseResponse;
import vn.com.fpt.constants.ErrorStatusConstants;


import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import static vn.com.fpt.constants.ErrorStatusConstants.*;

@Slf4j
@RestControllerAdvice
public class ApiExceptionHandler {

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<BaseResponse<Object>> handleException(Exception e) {
        Sentry.captureException(e);
        Sentry.addBreadcrumb(e.getMessage());
        e.printStackTrace();
        log.error("Đã có lỗi xảy ra, chi tiết: {}", e.getMessage());
        return AppResponse.error(ErrorStatusConstants.INTERNAL_ERROR, e.getMessage());
    }

    @ExceptionHandler(BusinessException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<BaseResponse<Object>> handleBusinessException(BusinessException e) {
        Sentry.captureException(e);
        Sentry.addBreadcrumb(e.getErrorStatus().getMessage());
        log.error("Đã có lỗi xảy ra, chi tiết: {}", e.getErrorStatus().getMessage());
        return AppResponse.error(e.getErrorStatus(), e.getMessage());
    }


    @ExceptionHandler(BindException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<BaseResponse<Map<String, String>>> handleBindException(BindException e) {
        Map<String, String> errors = e
                .getBindingResult()
                .getFieldErrors()
                .stream()
                .collect(Collectors.toMap(FieldError::getField, Objects.requireNonNull(FieldError::getDefaultMessage)));
        errors.values().forEach(Sentry::addBreadcrumb);
        return AppResponse.error(BAD_REQUEST, errors);
    }

    @ExceptionHandler(AuthenticationException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ResponseEntity<BaseResponse<Object>> handleAuthenticationException(AuthenticationException e) {
        Sentry.addBreadcrumb(e.getMessage());
        if (e instanceof BadCredentialsException) {
            Sentry.captureException(e);
            return AppResponse.error(WRONG_LOGIN_INFORMATION);
        }
        if (e instanceof DisabledException) {
            Sentry.captureException(e);
            return AppResponse.error(LOCKED_ACCOUNT, e.getMessage());
        }
        return AppResponse.error(UNAUTHORIZED_ERROR);
    }

}
