package com.busanit501.boot_test1.dto.publicData;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Items {

    @JsonProperty("item")
    private List<PublicDataDTO> item;
}
