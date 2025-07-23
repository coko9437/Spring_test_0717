package com.busanit501.boot_test1.controller;

import com.busanit501.boot_test1.dto.PageRequestDTO;
import com.busanit501.boot_test1.dto.publicData.PublicDataDTO;
import com.busanit501.boot_test1.dto.publicData.PublicDataResponse;
import com.busanit501.boot_test1.service.PublicDataService;
import lombok.RequiredArgsConstructor;
import com.busanit501.boot_test1.dto.publicData.PublicDataPage;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/")
public class PublicDataController {

    private final PublicDataService publicDataService;

    @GetMapping(value = "/public-data", produces = "application/xml; charset=UTF-8")
    public ResponseEntity<String> getRawPublicData(
            @RequestParam(defaultValue = "1") int pageNo,
            @RequestParam(defaultValue = "10") int numOfRows) throws Exception {

        String xml = publicDataService.getRawPublicDataXml(pageNo, numOfRows);
        return ResponseEntity
                .ok()
                .header("Content-Type", "application/xml; charset=UTF-8")
                .body(xml);
    }
}