package vn.com.fpt.service.assets;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import vn.com.fpt.entity.AssetTypes;
import vn.com.fpt.entity.BasicAssets;
import vn.com.fpt.model.BasicAssetDTO;
import vn.com.fpt.repositories.AssetTypesRepository;
import vn.com.fpt.repositories.BasicAssetRepository;
import vn.com.fpt.repositories.RoomAssetRepository;
import vn.com.fpt.requests.BasicAssetsRequest;

import vn.com.fpt.service.contract.ContractService;

import javax.persistence.EntityManager;
import javax.persistence.Query;
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
    private ContractService contractService;

    @Mock
    private RoomAssetRepository roomAssetRepository;

    private AssetService assetServiceTest;

    @BeforeEach
    void setUp() {
        assetServiceTest = new AssetServiceImpl(entityManager,
                assetTypesRepository,
                basicAssetRepository,
                contractService,
                roomAssetRepository);
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
        Query query = mock(Query.class);
        when(entityManager.createNativeQuery(anyString(), anyString()))
                .thenReturn(query);
        when(query.getResultList()).thenReturn(basicAssetsList);
        //run test
        List<BasicAssetDTO> result = assetServiceTest.listBasicAsset();
        //verify result
        Assertions.assertEquals(1, result.size());
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

}
