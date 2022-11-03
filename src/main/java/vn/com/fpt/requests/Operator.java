package vn.com.fpt.requests;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import vn.com.fpt.common.utils.DateUtils;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class Operator {

    private Long createdBy;

    private Date createdAt;

    private Long modifyBy;

    private Date modifyAt;

    public Operator() {
        this.createdAt = DateUtils.now();
        this.modifyAt = DateUtils.now();
    }
}
