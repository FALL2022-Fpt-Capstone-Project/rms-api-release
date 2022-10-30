package vn.com.fpt.entity.authentication;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.*;
import org.hibernate.annotations.DynamicUpdate;
import vn.com.fpt.configs.AppConfigs;
import vn.com.fpt.entity.BaseEntity;
import vn.com.fpt.security.ERole;

import javax.persistence.*;

@Entity
@Table(name = Role.TABLE_NAME)
@DynamicUpdate
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@AttributeOverride(name = "id", column = @Column(name = "role_id"))
public class Role extends BaseEntity {

    public static final String TABLE_NAME = AppConfigs.TABLE_AUTHENTICATION + "role";

    @Enumerated(EnumType.STRING)
    @Column(name = "name", length = 20)
    private ERole name;
}
