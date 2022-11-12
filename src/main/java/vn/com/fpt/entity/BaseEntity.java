package vn.com.fpt.entity;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import vn.com.fpt.common.utils.DateUtils;

import javax.persistence.*;
import java.util.Date;

@MappedSuperclass
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class BaseEntity {

    public BaseEntity(Long id) {
        this.id = id;
    }

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "created_by")
    private Long createdBy;

    @Column(name = "created_at")
    @JsonIgnore
    private Date createdAt;

    @Column(name = "modified_by")
    @JsonIgnore
    private Long modifiedBy;

    @Column(name = "modified_at")
    private Date modifiedAt;

    @JsonGetter("created_at")
    public String getCreatedAtt(){
        return DateUtils.format(this.createdAt, DateUtils.DATE_FORMAT_3);
    }

    @JsonGetter("modified_at")
    public String getModifyAtt(){
        return DateUtils.format(this.modifiedAt, DateUtils.DATE_FORMAT_3);
    }
}
