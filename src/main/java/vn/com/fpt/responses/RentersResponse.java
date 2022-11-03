package vn.com.fpt.responses;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.*;
import vn.com.fpt.entity.Address;
import vn.com.fpt.entity.Identity;
import vn.com.fpt.entity.Renters;

import java.io.Serializable;

@Getter
@Setter
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class RentersResponse implements Serializable {
    private String renterFullName;

    private Boolean gender;

    private String phoneNumber;

    private String email;

    private String identityNumber;

    private Long roomId;

    private Boolean represent;

    private Identity renterIdentity;

    private Address address;

    public static RentersResponse of(Renters renters) {
        return RentersResponse.builder()
                .renterFullName(renters.getRenterFullName())
                .gender(renters.getGender())
                .phoneNumber(renters.getPhoneNumber())
                .email(renters.getEmail())
                .identityNumber(renters.getIdentityNumber())
                .roomId(renters.getRoomId())
                .represent(renters.getRepresent())
                .renterIdentity(renters.getRenterIdentity())
                .address(renters.getAddress())
                .build();
    }
}
