package com.busanit501.boot_test1.dto.publicData;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Body   {

    private Items items;
    private int numOfRows;
    private int pageNo;
    private int totalCount;
}
