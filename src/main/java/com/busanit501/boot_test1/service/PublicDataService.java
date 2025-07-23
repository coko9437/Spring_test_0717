package com.busanit501.boot_test1.service;

import com.busanit501.boot_test1.dto.publicData.PublicDataPage;

public interface PublicDataService {
    PublicDataPage getPublicData(int pageNo, int numOfRows) throws Exception;

    String getRawPublicDataXml(int pageNo, int numOfRows) throws Exception;
}
