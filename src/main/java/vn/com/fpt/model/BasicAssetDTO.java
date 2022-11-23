package vn.com.fpt.model;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


import javax.persistence.*;
import java.io.Serializable;
import java.math.BigInteger;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@Entity
@SqlResultSetMapping(name = BasicAssetDTO.SQL_RESULT_SET_MAPPING,
        classes = @ConstructorResult(
                targetClass = BasicAssetDTO.class,
                columns = {
                        @ColumnResult(name = "asset_id", type = BigInteger.class),
                        @ColumnResult(name = "asset_name", type = String.class),
                        @ColumnResult(name = "asset_type_id", type = BigInteger.class),
                        @ColumnResult(name = "asset_type_name", type = String.class),
                        @ColumnResult(name = "asset_type_show_name", type = String.class)
                }))
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class BasicAssetDTO implements Serializable {
    public static final String SQL_RESULT_SET_MAPPING = "BasicAssetDTO";
    @Id
    private BigInteger basicAssetId;

    private String basicAssetName;

    private BigInteger assetTypeId;

    private String assetTypeName;

    private String assetTypeShowName;

    public Long getBasicAssetId() {
        return basicAssetId.longValue();
    }

    public Long getAssetTypeId() {
        return assetTypeId.longValue();
    }

    public BasicAssetDTO(BigInteger assetId,
                         String assetName,
                         BigInteger assetTypeId,
                         String assetTypeName,
                         String assetTypeShowName) {
        this.basicAssetId = assetId;
        this.basicAssetName = assetName;
        this.assetTypeId = assetTypeId;
        this.assetTypeName = assetTypeName;
        this.assetTypeShowName = assetTypeShowName;
    }
}
