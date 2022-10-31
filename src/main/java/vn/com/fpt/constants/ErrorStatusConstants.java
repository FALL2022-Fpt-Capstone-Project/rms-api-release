package vn.com.fpt.constants;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor

public enum ErrorStatusConstants {

    UNKNOWN(HttpStatus.INTERNAL_SERVER_ERROR, 500001, "Không rõ!!"),

    INTERNAL_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, 500000, "Đã có lỗi xảy ra. Vui lòng liên hệ với CSKH để được hỗ trợ. Sđt: 0944808998"),

    BAD_REQUEST(HttpStatus.BAD_REQUEST, 400000, "Yêu cầu không hợp lệ, vui lòng kiểm tra lại!!"),

    INVALID_ROLE(HttpStatus.BAD_REQUEST, 400001, "Quyền không tồn tại, vui lòng kiểm tra lại!!"),

    EXISTED_ACCOUNT(HttpStatus.BAD_REQUEST, 400002, "Tài khoản đã tồn tại, vui lòng kiểm tra lại!!"),

    WRONG_LOGIN_INFORMATION(HttpStatus.FORBIDDEN, 403001, "Sai thông tin đăng nhập, vui lòng kiểm tra lại!!"),

    USER_NOT_FOUND(HttpStatus.FORBIDDEN, 403002, "Không tìm thấy tài khoản, vui lòng kiểm tra lại!!"),

    STAFF_NOT_FOUND(HttpStatus.BAD_REQUEST, 403003, "Không tìm thấy khách thuê, vui lòng kiểm tra lại!!"),

    GENERAL_SERVICE_NOT_FOUND(HttpStatus.BAD_REQUEST, 403004, "Không tìm thấy dịch vụ chung, vui kiểm tra lại!!"),

    GENERAL_SERVICE_EXISTED(HttpStatus.BAD_REQUEST, 403005, "Dịch vụ chung đã tồn tại, vui lòng kiểm tra lại"),

    UNAUTHORIZED_ERROR(HttpStatus.UNAUTHORIZED, 401001, "Bạn không có quyền truy cập!!"),

    LOCKED_ACCOUNT(HttpStatus.UNAUTHORIZED, 401002, "Tài khoản của bạn đã bị khóa!!");


    private final HttpStatus errorStatus;

    private final Integer errorCode;

    private final String message;

}
