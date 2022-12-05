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

import static vn.com.fpt.common.utils.DateUtils.now;

@Entity
@Table(name = TableChangeLog.TABLE_NAME)
@DynamicUpdate
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@AttributeOverride(name = "id", column = @Column(name = "bill_log_id"))
public class TableChangeLog extends BaseEntity {
    public static final String TABLE_NAME = AppConfigs.TABLE_MANAGER + "bill_change_log";

    @Column(name = "property")
    private String property;

    @Column(name = "old_value")
    private String oldValue;

    @Column(name = "new_value")
    private String newValue;

    @Column(name = "table_name")
    private String tableName;

    @Column(name = "table_id")
    private Long tableId;

    @Column(name = "created_name")
    private String createdName;

    @Column(name = "event_type")
    private String eventType;

    public static TableChangeLog of(String property,
                                    String oldValue,
                                    String newValue,
                                    String createdName,
                                    String tableName,
                                    Long tableId,
                                    String eventType) {
        return TableChangeLog.builder()
                .property(property)
                .oldValue(oldValue)
                .newValue(newValue)
                .tableName(tableName)
                .createdName(createdName)
                .eventType(eventType)
                .tableId(tableId)
                .build();
    }

    public static TableChangeLog add(String property,
                                     String oldValue,
                                     String newValue,
                                     String tableName,
                                     String createdName,
                                     Long tableId,
                                     String eventType) {
        var add = of(property, oldValue, newValue, createdName, tableName , tableId, eventType);
        add.setCreatedAt(now());
        return add;
    }


}
