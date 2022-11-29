package vn.com.fpt.service.assets;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import vn.com.fpt.common.BusinessException;
import vn.com.fpt.entity.AssetTypes;
import vn.com.fpt.entity.BasicAssets;
import vn.com.fpt.entity.Contracts;
import vn.com.fpt.entity.HandOverAssets;
import vn.com.fpt.model.BasicAssetDTO;
import vn.com.fpt.model.HandOverAssetsDTO;
import vn.com.fpt.repositories.AssetTypesRepository;
import vn.com.fpt.repositories.BasicAssetRepository;
import vn.com.fpt.repositories.HandOverAssetsRepository;
import vn.com.fpt.repositories.RoomAssetRepository;
import vn.com.fpt.requests.BasicAssetsRequest;
import vn.com.fpt.requests.HandOverAssetsRequest;
import vn.com.fpt.service.contract.ContractService;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static vn.com.fpt.common.constants.ErrorStatusConstants.ASSET_OUT_OF_STOCK;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class AssetServiceImplTest {
    @Mock
    private EntityManager entityManager;

    @Mock
    private AssetTypesRepository assetTypesRepository;

    @Mock
    private BasicAssetRepository basicAssetRepository;

    @Mock
    private HandOverAssetsRepository handOverAssetsRepository;

    @Mock
    private ContractService contractService;

    @Mock
    private RoomAssetRepository roomAssetRepository;

    private AssetService assetServiceTest;

    @BeforeEach
    void setUp() {
        assetServiceTest = new AssetServiceImpl(entityManager,
                assetTypesRepository,
                basicAssetRepository,
                handOverAssetsRepository,
                contractService,
                roomAssetRepository);
    }

    @Test
    void testListHandOverAsset() {
        HandOverAssetsDTO handOverAssetsDTO = new HandOverAssetsDTO();
        handOverAssetsDTO.setAssetId(BigInteger.valueOf(1));
        handOverAssetsDTO.setHandOverAssetQuantity(2);
        List<HandOverAssetsDTO> handOverAssetsDTOList = new ArrayList<>();
        handOverAssetsDTOList.add(handOverAssetsDTO);

        //mock result
        Query query = mock(Query.class);
        when(entityManager.createNativeQuery(anyString(), anyString()))
                .thenReturn(query);
        when(query.getResultList()).thenReturn(handOverAssetsDTOList);
        //run test
        List<HandOverAssetsDTO> result = assetServiceTest.listHandOverAsset(1l);
        //verify result
        Assertions.assertEquals(1, result.size());
    }

    @Test
    void testListAssetType() {
        List<AssetTypes> assetTypesList = new ArrayList<>();
        assetTypesList.add(new AssetTypes());
        //mock result
        when(assetTypesRepository.findAll()).thenReturn(assetTypesList);
        //run test
        List<AssetTypes> result = assetServiceTest.listAssetType();
        //verify result
        Assertions.assertEquals(1, result.size());
    }

    @Test
    void testListBasicAsset() {
        List<BasicAssets> basicAssetsList = new ArrayList<>();
        basicAssetsList.add(new BasicAssets());
        //mock result
        when(basicAssetRepository.findAll()).thenReturn(basicAssetsList);
        //run test
        List<BasicAssetDTO> result = assetServiceTest.listBasicAsset();
        //verify result
        Assertions.assertEquals(1, result.size());
    }

    @Test
    void testUpdateHandOverAsset() {
        HandOverAssets old = new HandOverAssets();
        HandOverAssetsRequest request = new HandOverAssetsRequest();
        Long operator = 1l;
        Long contractId = 2l;
        Date dateDelivery = new Date();

        HandOverAssets mockHandOverAssets = HandOverAssets.builder().assetId(1l).build();
        when(handOverAssetsRepository.save(any(HandOverAssets.class))).thenReturn(mockHandOverAssets);
        //run test
        var result = assetServiceTest.updateHandOverAsset(old, request, operator, contractId, dateDelivery);
        //vefiry
        Assertions.assertEquals(1l, result.getAssetId());
    }

    @Test
    void testBasicAssets() {
        Long id = 1l;
        BasicAssets basicAssets = new BasicAssets();
        basicAssets.setAssetName("name");
        //mock result
        when(basicAssetRepository.findById(id)).thenReturn(Optional.of(basicAssets));
        //run test
        BasicAssets result = assetServiceTest.basicAssets(id);
        //verify result
        Assertions.assertEquals("name", result.getAssetName());
    }

    @Test
    void testAdd() {
        BasicAssetsRequest request = new BasicAssetsRequest();
        Long operator = 1l;
        BasicAssets basicAssets = new BasicAssets();
        basicAssets.setAssetName("name");
        //mock reuslt
        when(basicAssetRepository.save(any(BasicAssets.class))).thenReturn(basicAssets);
        //run test
        BasicAssets result = assetServiceTest.add(request, operator);
        //verify
        Assertions.assertEquals("name", result.getAssetName());
    }

    @Test
    void testAddHandOverAsset() {
        HandOverAssetsRequest request = new HandOverAssetsRequest();
        Long operator = 1l;
        Long contractId = 1l;
        Date dateDelivery = new Date();

        HandOverAssets handOverAssets = HandOverAssets.builder().assetId(1l).build();

        //mock result
        when(handOverAssetsRepository.save(any(HandOverAssets.class)))
                .thenReturn(handOverAssets);
        //run test
        HandOverAssets result = assetServiceTest.addHandOverAsset(request, operator, contractId, dateDelivery);
        //verify
        Assertions.assertEquals(1l, result.getAssetId());
    }

    @Test
    void testAddAdditionalAsset() {
        //set up
        HandOverAssetsRequest request = HandOverAssetsRequest.builder()
                .handOverAssetQuantity(100)
                .assetId(1l)
                .assetsAdditionalName("name")
                .assetsAdditionalType(1l)
                .handOverDateDelivery("2022-10-11")
                .build();
        Long contractId = 1l;
        Long operator = 1l;

        var groupContractId = new Contracts();
        groupContractId.setGroupId(1l);

        //mock result
        HandOverAssets mockHandOverAssets = HandOverAssets.builder().assetId(2l).build();

        when(contractService.contract(contractId)).thenReturn(groupContractId);
        when(handOverAssetsRepository.save(any(HandOverAssets.class)))
                .thenReturn(mockHandOverAssets);

        var toUpdate = HandOverAssets.builder().quantity(200).build();
        when(handOverAssetsRepository.findByContractIdAndAndAssetId(groupContractId.getGroupId(), request.getAssetId()))
                .thenReturn(toUpdate);
        //run test
        HandOverAssets result = assetServiceTest.addAdditionalAsset(request, contractId, 1, operator);
        //verify
        Assertions.assertEquals(2l, result.getAssetId());
    }

    @Test
    void testAddGeneralAsset() {
        HandOverAssetsRequest request = new HandOverAssetsRequest();
        Long operator = 1l;
        Long contractId = 1l;
        Date dateDelivery = new Date();

        //mock result
        HandOverAssets mockHandOverAssets = HandOverAssets.builder().assetId(1l).build();

        when(handOverAssetsRepository.save(any(HandOverAssets.class)))
                .thenReturn(mockHandOverAssets);
        //run test
        HandOverAssets result = assetServiceTest.addGeneralAsset(request, operator, contractId, dateDelivery);
        //verify
        Assertions.assertEquals(1l, result.getAssetId());
    }

    @Test
    void testUpdateGeneralAssetQuantity() {
        Long contractId = 1l;
        Long assetId = 2l;
        Integer quantity = 100;
        //mock result
        HandOverAssets toUpdate = HandOverAssets.builder().quantity(200).build();

        when(handOverAssetsRepository.findByContractIdAndAndAssetId(contractId, assetId))
                .thenReturn(toUpdate);
        HandOverAssets mockHandOverAssets = HandOverAssets.builder().assetId(4l).build();
        when(handOverAssetsRepository.save(toUpdate)).thenReturn(mockHandOverAssets);
        //run test
        HandOverAssets result = assetServiceTest.updateGeneralAssetQuantity(contractId, assetId, quantity);
        //verify
        Assertions.assertEquals(4L, result.getAssetId());

    }

    @Test
    void testUpdateGeneralAssetQuantityThrowException() {
        Long contractId = 1l;
        Long assetId = 2l;
        Integer quantity = 300;
        //mock result
        HandOverAssets toUpdate = HandOverAssets.builder().quantity(200).build();

        when(handOverAssetsRepository.findByContractIdAndAndAssetId(contractId, assetId))
                .thenReturn(toUpdate);

        BusinessException businessException = Assertions.assertThrows(BusinessException.class,
                () -> assetServiceTest.updateGeneralAssetQuantity(contractId, assetId, quantity));
        Assertions.assertEquals(ASSET_OUT_OF_STOCK, businessException.getErrorStatus());
    }

    @Test
    void testUpdate() {
        Long id = 1l;
        BasicAssetsRequest request = new BasicAssetsRequest();
        request.setAssetName("name");
        request.setAssetTypeId(1l);
        Long operator = 2l;

        //mock result
        BasicAssets oldAsset = new BasicAssets();
        oldAsset.setCreatedBy(1l);
        oldAsset.setCreatedAt(new Date());
        oldAsset.setId(1l);

        BasicAssets mockBasicAssets = new BasicAssets();
        mockBasicAssets.setId(1l);
        when(basicAssetRepository.findById(id)).thenReturn(Optional.of(oldAsset));
        when(basicAssetRepository.save(any(BasicAssets.class))).thenReturn(mockBasicAssets);
        //run test
        var result = assetServiceTest.update(id, request, operator);
        //verify
        Assertions.assertEquals(1l, result.getId());
    }

    @Test
    void testDeleteBasicAsset() {
        Long id = 1l;
        String result = assetServiceTest.deleteBasicAsset(id);
        Assertions.assertNull(result);
    }

    @Test
    void testHandOverAsset() {
        Long id = 1l;

        HandOverAssets mockHandOverAssets = HandOverAssets.builder().assetId(1l).build();

        when(handOverAssetsRepository.findById(id)).thenReturn(Optional.of(mockHandOverAssets));
        var result = assetServiceTest.handOverAsset(id);
        //verify
        Assertions.assertEquals(1l, result.getAssetId());
    }
}
