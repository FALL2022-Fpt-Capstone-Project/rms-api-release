package vn.com.fpt.requests;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class RenterRequest {

    private String name;

    private Boolean gender;

    private String email;

    private String phoneNumber;

    private String identityCard;

    private String licensePlates;

    private Long roomId;

    private String addressCity;

    private String addressDistrict;

    private String addressWards;

    private String addressMoreDetail;

    private String address;

    private Boolean represent;

    public static RenterRequest contractOf(RoomContractRequest request){
        return RenterRequest.builder()
                .name(request.getRenterName())
                .phoneNumber(request.getRenterPhoneNumber())
                .email(request.getRenterEmail())
                .gender(request.getRenterGender())
                .identityCard(request.getRenterIdentityCard())
                // khách thuê đại diện ký
                .represent(true)
                .roomId(request.getRoomId())

                .build();
    }
}
