package vn.com.fpt.service.config_value;

import vn.com.fpt.entity.config.Month;
import vn.com.fpt.entity.config.TotalFloor;
import vn.com.fpt.entity.config.TotalRoom;
import vn.com.fpt.model.DistrictDTO;

import java.util.List;

public interface ConfigService {
    List<Month> listConfigMonth();

    List<TotalRoom> listConfigRoom();

    List<TotalFloor> listConfigFloor();

    List<DistrictDTO> listAddedDistrict();
}
