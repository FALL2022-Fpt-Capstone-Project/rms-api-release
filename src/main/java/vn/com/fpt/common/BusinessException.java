package vn.com.fpt.common;

import lombok.Getter;
import vn.com.fpt.constants.ErrorStatusConstants;

@Getter
public class BusinessException extends RuntimeException {

    private ErrorStatusConstants errorStatus;
    private String msg;


    public BusinessException(ErrorStatusConstants errorStatus, String msg) {
        super(msg);
        this.errorStatus = errorStatus;
        this.msg = msg;
    }

    public BusinessException(String msg){
        super(msg);
    }

    public BusinessException(String message, Throwable cause, ErrorStatusConstants errorStatus, String msg) {
        super(message, cause);
        this.errorStatus = errorStatus;
        this.msg = msg;
    }

    public BusinessException(Throwable cause, ErrorStatusConstants errorStatus, String msg) {
        super(cause);
        this.errorStatus = errorStatus;
        this.msg = msg;
    }

    public BusinessException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace, ErrorStatusConstants errorStatus, String msg) {
        super(message, cause, enableSuppression, writableStackTrace);
        this.errorStatus = errorStatus;
        this.msg = msg;
    }
}
