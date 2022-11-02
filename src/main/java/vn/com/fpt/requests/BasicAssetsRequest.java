package vn.com.fpt.requests;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BasicAssetsRequest {
    private Long id;

    private String assetName;

    private Long assetTypeId;
}
