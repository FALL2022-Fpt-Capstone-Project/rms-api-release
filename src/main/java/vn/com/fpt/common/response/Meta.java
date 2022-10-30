package vn.com.fpt.common.response;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class Meta {
    private Integer code;

    private String message;

    public static Meta of(Integer code, String message) {
        return Meta.builder().code(code).message(message).build();
    }
}