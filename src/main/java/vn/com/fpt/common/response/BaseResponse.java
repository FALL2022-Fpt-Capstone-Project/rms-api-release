package vn.com.fpt.common.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import vn.com.fpt.constants.ErrorStatusConstants;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.NONE)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class BaseResponse<T> {

    private Meta meta;
    private T data;

    public static <T> BaseResponse<T> success(T data) {
        BaseResponse<T> response = new BaseResponse<>();
        response.setData(data);
        response.setMeta(Meta.of(200000, "Thành công!!"));
        return response;
    }

    public static <T> BaseResponse<T> success() {
        return success(null);
    }

    public static <T> BaseResponse<T> error(Integer code, String message, T data) {
        BaseResponse<T> response = new BaseResponse<>();
        response.setMeta(Meta.of(code, message));
        response.setData(data);
        return response;
    }

    public static <T> BaseResponse<T> error(ErrorStatusConstants errorStatusConstants, T data) {
        BaseResponse<T> response = new BaseResponse<>();
        response.setMeta(Meta.of(errorStatusConstants.getErrorCode(), errorStatusConstants.getMessage()));
        response.setData(data);
        return response;
    }

    public static <T> BaseResponse<T> error(Integer code, String message) {
        return error(code, message, null);
    }

}