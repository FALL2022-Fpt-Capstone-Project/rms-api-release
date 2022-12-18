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

@Entity
@Table(name = SubMoneySource.TABLE_NAME)
@DynamicUpdate
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@AttributeOverride(name = "id", column = @Column(name = "sub_money_source_id"))
public class SubMoneySource extends BaseEntity {

    public static final String TABLE_NAME = AppConfigs.TABLE_MANAGER + "sub_money_source";

    @Column(name = "money")
    private Double money;

    @Column(name = "money_source_id")
    private Long moneySourceId;

    @Column(name = "type")
    private String type;

}
