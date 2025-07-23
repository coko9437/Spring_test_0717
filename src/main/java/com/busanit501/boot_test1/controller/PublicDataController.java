package com.busanit501.boot_test1.controller;

import com.busanit501.boot_test1.dto.PageRequestDTO;
import com.busanit501.boot_test1.dto.publicData.PublicDataDTO;
import com.busanit501.boot_test1.dto.publicData.PublicDataResponse;
import com.busanit501.boot_test1.service.PublicDataService;
import lombok.RequiredArgsConstructor;
import com.busanit501.boot_test1.dto.publicData.PublicDataPage;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/")
public class PublicDataController {

    private final PublicDataService publicDataService;

    @GetMapping("/public-data")
    public List<PublicDataDTO> getPublicData(
            @RequestParam(defaultValue = "1") int pageNo,
            @RequestParam(defaultValue = "10") int numOfRows)
                throws Exception {

        return publicDataService.getPublicData(pageNo, numOfRows).getItems();
    }
}