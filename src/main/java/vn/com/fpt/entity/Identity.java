package vn.com.fpt.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.*;
import org.hibernate.annotations.DynamicUpdate;
import vn.com.fpt.configs.AppConfigs;

import javax.persistence.*;

@Entity
@Table(name = Identity.TABLE_NAME)
@DynamicUpdate
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@AttributeOverride(name = "id", column = @Column(name = "identity_id"))
public class Identity extends BaseEntity {

    public static final String TABLE_NAME = AppConfigs.TABLE_MANAGER + "identity";

    @Column(name = "identity_front_img")
    private String identityFrontImg;

    @Column(name = "identity_back_img")
    private String identityBackImg;

    @OneToOne(mappedBy = "renterIdentity")
    @JsonIgnore
    private Renters renters;
}
