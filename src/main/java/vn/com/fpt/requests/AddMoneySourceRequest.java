package vn.com.fpt.requests;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class AddMoneySourceRequest {

    private Long groupId;

    @NotBlank(message = "Thời gian tạo không được đ trống")
    private String time;

    private Double roomGroupMoney;
    private Double serviceMoney;
    private Double otherMoney;

}
