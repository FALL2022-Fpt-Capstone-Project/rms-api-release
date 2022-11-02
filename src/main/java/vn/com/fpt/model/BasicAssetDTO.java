package vn.com.fpt.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.Serializable;
import java.math.BigInteger;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class BasicAssetDTO implements Serializable {
    @Id
    private BigInteger basicAssetId;

    private String basicAssetName;

    private BigInteger assetTypeId;

    private String assetName;
}
