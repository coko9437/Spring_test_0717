package com.busanit501.boot_test1.dto.publicData;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PublicDataResponse {

//    @JsonProperty("response")
    private GetTblFnrstrnStusInfo response;
}
// PublicDataResponse → GetTblFnrstrnStusInfo → Body → Items → List<Item>