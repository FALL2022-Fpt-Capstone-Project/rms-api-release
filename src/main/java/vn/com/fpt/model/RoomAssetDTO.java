package vn.com.fpt.model;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigInteger;


@Getter
@Setter
@NoArgsConstructor
@Entity
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@SqlResultSetMapping(name = HandOverGeneralServiceDTO.SQL_RESULT_SETS_MAPPING,
        classes = @ConstructorResult(
                targetClass = HandOverGeneralServiceDTO.class,
                columns = {
                        @ColumnResult(name = "room_asset_id", type = BigInteger.class),
                        @ColumnResult(name = "room_id", type = BigInteger.class),
                        @ColumnResult(name = "group_id", type = BigInteger.class),
                        @ColumnResult(name = "asset_name", type = String.class),
                        @ColumnResult(name = "asset_quantity", type = Integer.class),
                        @ColumnResult(name = "asset_type_id", type = BigInteger.class),
                        @ColumnResult(name = "asset_type_name", type = String.class),
                        @ColumnResult(name = "asset_type_show_name", type = String.class)
                }))
public class RoomAssetDTO {
    public static final String SQL_RESULT_SETS_MAPPING = "RoomAssetDTO";
    @Id
    private BigInteger roomAssetId;

    private BigInteger roomId;
    private BigInteger groupId;

    private String assetName;
    private Integer assetQuantity;
    private BigInteger assetTypeId;
    private String assetTypeName;
    private String assetTypeShowName;

    public Long getRoomAssetId() {
        return roomAssetId.longValue();
    }

    public Long getRoomId() {
        return roomId.longValue();
    }

    public Long getGroupId() {
        return groupId.longValue();
    }

    public Long getAssetTypeId() {
        return assetTypeId.longValue();
    }

    public RoomAssetDTO(BigInteger roomAssetId,
                        BigInteger roomId,
                        BigInteger groupId,
                        String assetName,
                        Integer assetQuantity,
                        BigInteger assetTypeId,
                        String assetTypeName,
                        String assetTypeShowName) {
        this.roomAssetId = roomAssetId;
        this.roomId = roomId;
        this.groupId = groupId;
        this.assetName = assetName;
        this.assetQuantity = assetQuantity;
        this.assetTypeId = assetTypeId;
        this.assetTypeName = assetTypeName;
        this.assetTypeShowName = assetTypeShowName;
    }
}
