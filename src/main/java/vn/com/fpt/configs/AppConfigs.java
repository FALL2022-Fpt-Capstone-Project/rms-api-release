package vn.com.fpt.configs;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.servers.Server;
import lombok.Getter;
import lombok.Setter;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Getter
@Setter
@Configuration
@SecurityScheme(
        name = "BearerAuth",
        type = SecuritySchemeType.HTTP,
        bearerFormat = "JWT",
        scheme = "bearer"
)
@OpenAPIDefinition(
        servers = {
                @Server(url = "http://localhost:8080", description = "Local")
        },
        info = @Info(title = "RMS API", version = "v1")
)
public class AppConfigs {
    // base path
    public static final String V1_PATH = "/v1";
    public static final String V2_PATH = "/v2";

    public static final String AUTHEN_PATH = "/api/auth";
    public static final String MANAGER_PATH = "/api/manager";
    //request path
    public static final String CONTRACT_PATH = "/contract";
    public static final String SERVICE_PATH = "/service";
    public static final String RENTER_PATH = "/renter";
    public static final String STAFF_PATH = "/staff";
    public static final String ROOM_PATH = "/room";
    public static final String ASSET_PATH = "/asset";
    public static final String GROUP_PATH = "/group";

    //table name
    public static final String TABLE_AUTHENTICATION = "authentication.";

    public static final String TABLE_MANAGER = "manager.";

}
