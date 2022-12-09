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
@Table(name = Permission.TABLE_NAME)
@DynamicUpdate
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@AttributeOverride(name = "id", column = @Column(name = "account_permission_id"))
public class Permission extends BaseEntity{
    public static final String TABLE_NAME = AppConfigs.TABLE_MANAGER + "account_permission";
    @Column(name = "permision_id")
    private Long permissionId;

    @Column(name = "account_id")
    private Long accountId;

    public static Permission of(Long accountId, Long permissionId){
        return Permission.builder().permissionId(permissionId).accountId(accountId).build();
    }

    public static Permission add(Long accountId, Long permissionId, Long operator){
        var add = of(accountId,permissionId);
        add.setCreatedAt(now());
        add.setCreatedBy(operator);
        return add;
    }
}
