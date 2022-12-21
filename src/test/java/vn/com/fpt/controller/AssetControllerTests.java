package vn.com.fpt.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import vn.com.fpt.common.utils.Operator;
import vn.com.fpt.controller.manager.AssetController;
import vn.com.fpt.entity.BasicAssets;
import vn.com.fpt.model.RoomAssetDTO;
import vn.com.fpt.repositories.BasicAssetRepository;
import vn.com.fpt.repositories.RoleRepository;
import vn.com.fpt.requests.BasicAssetsRequest;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.math.BigInteger;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(MockitoExtension.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class AssetControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper mapper;

    @MockBean
    RoleRepository roleRepository;

    @MockBean
    BasicAssetRepository basicAssetRepository;

    @MockBean
    EntityManager entityManager;

    public final String BASE_URL = AssetController.PATH;

    @Test
    @DisplayName("api /asset/type return success")
    void api_get_asset_type_success() throws Exception {
        mockMvc.perform(get(BASE_URL + "/type")
                        .header("Authorization-Test", "abc")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is2xxSuccessful());
    }

    @Test
    @DisplayName("api /asset/ return success")
    void api_get_basic_assets_success() throws Exception {
        mockMvc.perform(get(BASE_URL + "/")
                        .header("Authorization-Test", "abc")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is2xxSuccessful());
    }

    @Test
    @DisplayName("api /asset/id/{id} return success")
    void api_get_basic_asset_success() throws Exception {
        when(basicAssetRepository.findById(1L)).thenReturn(Optional.of(new BasicAssets("assetName", 1L, 1)));
        mockMvc.perform(get(BASE_URL + "/id/" + 1L)
                        .header("Authorization-Test", "abc")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is2xxSuccessful());
    }

    @Test
    @DisplayName("api /asset/room/{roomId} return success")
    void api_get_room_asset_success() throws Exception {
        RoomAssetDTO roomAssetDTO = new RoomAssetDTO();
        roomAssetDTO.setRoomAssetId(BigInteger.ONE);
        roomAssetDTO.setRoomId(BigInteger.ONE);
        roomAssetDTO.setGroupId(BigInteger.ONE);
        roomAssetDTO.setAssetName("asset name");
        roomAssetDTO.setAssetQuantity(1);
        roomAssetDTO.setAssetTypeId(BigInteger.ONE);
        roomAssetDTO.setAssetTypeName("type name");
        roomAssetDTO.setAssetTypeShowName("show name");
        Query query = mock(Query.class);
        when(entityManager.createNativeQuery(anyString(), eq("RoomAssetDTO"))).thenReturn(query);
        when(query.getResultList()).thenReturn(List.of(roomAssetDTO));

        mockMvc.perform(get(BASE_URL + "/room/" + 1L)
                        .header("Authorization-Test", "abc")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is2xxSuccessful());
    }

    @Test
    @DisplayName("api /add with all require field then return success")
    void api_add_basic_asset_success() throws Exception {
        BasicAssetsRequest request = new BasicAssetsRequest();
        request.setId(1L);
        request.setAssetName("assetName");
        request.setAssetTypeId(1L);
        Operator operators = mock(Operator.class);
        when(operators.operator()).thenReturn(1L);
        mockMvc.perform(post(BASE_URL + "/add")
                        .header("Authorization-Test", "abc")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(request)))
                .andExpect(status().is2xxSuccessful());
    }


}
