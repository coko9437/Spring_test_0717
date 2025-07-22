package com.busanit501.boot_test1.service;

import com.busanit501.boot_test1.dto.PageRequestDTO;
import com.busanit501.boot_test1.dto.publicData.PublicDataDTO;
import com.busanit501.boot_test1.dto.publicData.PublicDataResponse;

import java.util.List;

public interface PublicDataService {
    List<PublicDataDTO> getPublicData(PageRequestDTO pageRequestDTO);
    PublicDataResponse fetchData(int pageNo, int numOfRows);
}
