package com.busanit501.boot_test1.controller;

import com.busanit501.boot_test1.dto.PageRequestDTO;
import com.busanit501.boot_test1.dto.publicData.PublicDataDTO;
import com.busanit501.boot_test1.dto.publicData.PublicDataResponse;
import com.busanit501.boot_test1.service.PublicDataService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/publicdata")
public class PublicDataController {
    private final PublicDataService publicDataService;

    public PublicDataController(PublicDataService publicDataService) {
        this.publicDataService = publicDataService;
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)  // JSON 응답 명시
    public ResponseEntity<List<PublicDataDTO>> getPublicData(
            @RequestParam(defaultValue = "1") int pageNo,
            @RequestParam(defaultValue = "10") int numOfRows) {

        List<PublicDataDTO> dataList = publicDataService.getPublicData(pageNo, numOfRows);

        return ResponseEntity.ok(dataList);
    }
}