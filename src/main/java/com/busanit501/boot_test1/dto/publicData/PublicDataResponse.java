package com.busanit501.boot_test1.dto.publicData;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PublicDataResponse {

    @JsonProperty("response")
    private GetTblFnrstrnStusInfo response;
}
