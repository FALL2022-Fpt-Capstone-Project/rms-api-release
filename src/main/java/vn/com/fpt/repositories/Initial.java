package vn.com.fpt.repositories;

import io.sentry.Sentry;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import vn.com.fpt.entity.*;
import vn.com.fpt.entity.authentication.Account;
import vn.com.fpt.entity.authentication.Role;
import vn.com.fpt.security.ERole;

import java.util.List;
import java.util.Set;

@Component
@RequiredArgsConstructor
public class Initial implements ApplicationRunner {
    private final BasicAssetRepository basicAssetRepository;
    private final BasicServicesRepository basicServicesRepository;
    private final AssetTypesRepository assetTypesRepository;
    private final ServiceTypesRepository serviceTypesRepository;
    private final RoleRepository roleRepository;
    private final AccountRepository accountRepository;

    @Override
    public void run(ApplicationArguments args) throws Exception {

        List<Role> roles = List.of(new Role(ERole.ROLE_STAFF), new Role(ERole.ROLE_ADMIN));
        Account account = new Account();
        account.setUserName("phaphp");
        account.setPassword("123456");
        account.setFullName("Nguyễn Đức Pháp");
        account.setGender(true);
        account.setPhoneNumber("0944808998");
        account.setRoles(Set.of(new Role(ERole.ROLE_ADMIN)));
        account.setAddress(new Address("Hải Phòng", "Văn Cao", "Khu Hàn Quốc", ""));

        List<BasicAssets> basicAssets = List.of(
                new BasicAssets("Máy giặt", 5L),
                new BasicAssets("Điều hòa", 5L),
                new BasicAssets("Tủ lạnh", 3L),
                new BasicAssets("Tủ quần áo", 4L),
                new BasicAssets("Bàn học", 4L),
                new BasicAssets("Ghế văn phòng", 4L)
        );

        List<AssetTypes> assetTypes = List.of(
                new AssetTypes("BATH_ROOM", "Phòng tắm"),
                new AssetTypes("LIVING_ROOM", "Phòng khách"),
                new AssetTypes("KITCHEN", "Phòng bếp"),
                new AssetTypes("BED_ROOM", "Phòng ngủ"),
                new AssetTypes("OTHER", "Khác")
        );

        List<BasicServices> basicServices = List.of(
                new BasicServices("electric", "Dịch vụ điện"),
                new BasicServices("water", "Dịch vụ nước"),
                new BasicServices("vehicles", "Dịch vụ xe"),
                new BasicServices("internet", "Dịch vụ internet"),
                new BasicServices("cleaning", "Vệ sinh"),
                new BasicServices("other", "Khác")
        );

        List<ServiceTypes> serviceTypes = List.of(
                new ServiceTypes("Đồng hồ điện/nước"),
                new ServiceTypes("Tháng"),
                new ServiceTypes("Người")
        );

        try {
            if (basicAssetRepository.findAll().isEmpty()) {
                basicAssetRepository.saveAll(basicAssets);
            }
            if (basicServicesRepository.findAll().isEmpty()) {
                basicServicesRepository.saveAll(basicServices);
            }
            if (assetTypesRepository.findAll().isEmpty()) {
                assetTypesRepository.saveAll(assetTypes);
            }
            if (serviceTypesRepository.findAll().isEmpty()) {
                serviceTypesRepository.saveAll(serviceTypes);
            }
            if (roleRepository.findAll().isEmpty()) {
                roleRepository.saveAll(roles);
            }
            if (accountRepository.findAll().isEmpty()) {
                accountRepository.save(account);
            }
            Sentry.captureMessage("Innit success");
        } catch (Exception e) {
            Sentry.captureMessage("Innit fail");
        }

    }
}
