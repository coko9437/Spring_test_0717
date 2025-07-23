package com.busanit501.boot_test1.dto.publicData;

import lombok.Data;

import java.util.List;

@Data
public class PublicDataPage {
    private List<PublicDataDTO> items;
    private int pageNo;
    private int numOfRows;
    private int totalCount;
}

