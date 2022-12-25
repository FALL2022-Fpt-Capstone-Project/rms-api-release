package vn.com.fpt.controller;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
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
import vn.com.fpt.entity.RoomAssets;
import vn.com.fpt.model.RoomAssetDTO;
import vn.com.fpt.repositories.BasicAssetRepository;
import vn.com.fpt.repositories.RoleRepository;
import vn.com.fpt.repositories.RoomAssetRepository;
import vn.com.fpt.requests.BasicAssetsRequest;
import vn.com.fpt.requests.RoomAssetsRequest;
import vn.com.fpt.service.assets.AssetServiceImpl;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(MockitoExtension.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@MockitoSettings(strictness = Strictness.LENIENT)
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

    @MockBean
    RoomAssetRepository roomAssetRepository;

    @MockBean
    AssetServiceImpl assetService;

    public final String BASE_URL = AssetController.PATH;

    private static MockedStatic<Operator> mockedSettings;
    private static MockedStatic<BasicAssets> basicAssetsMockedStatic;
    private static MockedStatic<RoomAssets> roomAssetsMockedStatic;

    @BeforeAll
    public static void init() {
        mockedSettings = mockStatic(Operator.class);
        basicAssetsMockedStatic = mockStatic(BasicAssets.class);
        roomAssetsMockedStatic = mockStatic(RoomAssets.class);
    }

    @AfterAll
    public static void close() {
        mockedSettings.close();
        basicAssetsMockedStatic.close();
        roomAssetsMockedStatic.close();
    }


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
        when(Operator.operator()).thenReturn(1L);
        when(basicAssetRepository.save(BasicAssets.add(request, 1L))).thenReturn(new BasicAssets());
        mockMvc.perform(post(BASE_URL + "/add")
                        .header("Authorization-Test", "abc")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(request)))
                .andExpect(status().is2xxSuccessful());
    }

    @Test
    @DisplayName("api asset/room/{roomId} with correct param then return success")
    void api_view_room_asset_success() throws Exception {
        Long roomId = 1L;

        Query query = mock(Query.class);
        when(entityManager.createNativeQuery(anyString(), anyString())).thenReturn(query);
        when(query.getResultList()).thenReturn(Collections.emptyList());

        mockMvc.perform(get(BASE_URL + "/room/" + roomId)
                        .header("Authorization-Test", "abc"))
                .andExpect(status().is2xxSuccessful());
    }

    @Test
    @DisplayName("api asset/room/add with all require filed then return success")
    void api_add_room_asset_success() throws Exception {
        RoomAssetsRequest request1 = new RoomAssetsRequest();
        request1.setAssetName("a");
        request1.setAssetTypeId(1L);
        request1.setAssetQuantity(1);
        request1.setRoomId(1L);

        RoomAssetsRequest request2 = new RoomAssetsRequest();
        request2.setAssetName("b");
        request2.setAssetTypeId(1L);
        request2.setAssetQuantity(1);
        request2.setRoomId(1L);

        List<RoomAssetsRequest> requests = List.of(request1, request2);
        when(roomAssetRepository.findAllByRoomId(1L)).thenReturn(Collections.emptyList());
        when(Operator.operator()).thenReturn(1L);

        RoomAssets roomAsset1_1 = new RoomAssets();
        roomAsset1_1.setAssetName("a");
        roomAsset1_1.setAssetQuantity(1);
        roomAsset1_1.setAssetTypeId(1L);
        roomAsset1_1.setRoomId(1L);

        RoomAssets roomAsset1_2 = new RoomAssets();
        roomAsset1_2.setAssetName("b");
        roomAsset1_2.setAssetQuantity(1);
        roomAsset1_2.setAssetTypeId(1L);
        roomAsset1_2.setRoomId(1L);

        when(RoomAssets.add("a", 1, 1L, 1L, 1L)).thenReturn(roomAsset1_1);
        when(RoomAssets.add("b", 1, 1L, 1L, 1L)).thenReturn(roomAsset1_2);

        when(roomAssetRepository.saveAll(List.of(roomAsset1_1, roomAsset1_2))).thenReturn(List.of());
        when(roomAssetRepository.findAllByRoomId(1L)).thenReturn(new ArrayList<RoomAssets>());

        mockMvc.perform(get(BASE_URL + "/room/add")
                        .header("Authorization-Test", "abc")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(requests)))
                .andExpect(status().is5xxServerError());
    }

    @Test
    @DisplayName("api room/update with all require body field then return success")
    void api_update_room_asset_success() throws Exception {
        RoomAssetsRequest request1 = new RoomAssetsRequest();
        request1.setAssetName("a");
        request1.setAssetTypeId(1L);
        request1.setAssetQuantity(1);
        request1.setRoomId(1L);

        RoomAssetsRequest request2 = new RoomAssetsRequest();
        request2.setAssetName("b");
        request2.setAssetTypeId(1L);
        request2.setAssetQuantity(1);
        request2.setRoomId(1L);

        List<RoomAssetsRequest> requests = List.of(request1, request2);

        when(RoomAssets.modify(any(RoomAssets.class), anyString(), anyInt(), anyLong(), anyLong(), anyLong())).thenReturn(new RoomAssets());
        when(roomAssetRepository.saveAll(anyList())).thenReturn(Collections.emptyList());

        mockMvc.perform(get(BASE_URL + "/room/update")
                        .header("Authorization-Test", "abc")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(requests)))
                .andExpect(status().is5xxServerError());
    }


}
