package vn.com.fpt.common.response;

import lombok.experimental.UtilityClass;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import vn.com.fpt.common.constants.ErrorStatusConstants;

@UtilityClass
public class AppResponse {

    public static <T> ResponseEntity<BaseResponse<T>> success(T data) {
        return ResponseEntity.ok(BaseResponse.success(data));
    }

    public static <T> ResponseEntity<BaseResponse<T>> error(HttpStatus statusCode, BaseResponse<T> data) {
        return ResponseEntity.status(statusCode).body(data);
    }

    public static <T> ResponseEntity<BaseResponse<T>> error(HttpStatus statusCode, String message, T data) {
        return ResponseEntity.status(statusCode).body(BaseResponse.error(statusCode.value(), message, data));
    }

    public static <T> ResponseEntity<BaseResponse<T>> error(HttpStatus statusCode, String message) {
        return ResponseEntity.status(statusCode).body(BaseResponse.error(statusCode.value(), message));
    }

    public static <T> ResponseEntity<BaseResponse<T>> error(HttpStatus statusCode, String message, Integer code) {
        return ResponseEntity.status(statusCode).body(BaseResponse.error(code, message));
    }

    public static <T> ResponseEntity<BaseResponse<T>> error(ErrorStatusConstants error) {
        return ResponseEntity.status(error.getErrorStatus()).body(BaseResponse.error(error.getErrorCode(), error.getMessage()));
    }

    public static <T> ResponseEntity<BaseResponse<T>> error(ErrorStatusConstants error, T data) {
        return ResponseEntity.status(error.getErrorStatus()).body(BaseResponse.error(error.getErrorCode(), error.getMessage(), data));
    }
}