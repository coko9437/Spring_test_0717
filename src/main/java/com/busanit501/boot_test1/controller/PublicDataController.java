package com.busanit501.boot_test1.controller;

import com.busanit501.boot_test1.dto.PageRequestDTO;
import com.busanit501.boot_test1.dto.publicData.PublicDataDTO;
import com.busanit501.boot_test1.dto.publicData.PublicDataResponse;
import com.busanit501.boot_test1.service.PublicDataService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/publicdata")
public class PublicDataController {
    private final PublicDataService publicDataService;

    public PublicDataController(PublicDataService publicDataService) {
        this.publicDataService = publicDataService;
    }

    @GetMapping
    public ResponseEntity<PublicDataResponse> getPublicData(
            @RequestParam(defaultValue = "1") int pageNo,
            @RequestParam(defaultValue = "10") int numOfRows) {

        PublicDataResponse data = publicDataService.fetchData(pageNo, numOfRows);
        return ResponseEntity.ok(data);
    }
}