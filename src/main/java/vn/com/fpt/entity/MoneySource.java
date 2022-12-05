package vn.com.fpt.entity;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.*;
import org.hibernate.annotations.DynamicUpdate;
import vn.com.fpt.configs.AppConfigs;

import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Date;

@Entity
@Table(name = MoneySource.TABLE_NAME)
@DynamicUpdate
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@AttributeOverride(name = "id", column = @Column(name = "money_source_id"))
public class MoneySource extends BaseEntity {
    public static final String TABLE_NAME = AppConfigs.TABLE_MANAGER + "money_source";

    @Column(name = "money_source_description")
    private String description;

    @Column(name = "money_source_total_money")
    private Double totalMoney;

    @Column(name = "money_source_type")
    private String moneyType;

    @Column(name = "money_source_time")
    private Date moneySourceTime;
}
