package com.busanit501.boot_test1.service;

import com.busanit501.boot_test1.dto.PageRequestDTO;
import com.busanit501.boot_test1.dto.publicData.PublicDataDTO;
import com.busanit501.boot_test1.dto.publicData.PublicDataResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Log4j2
@RequiredArgsConstructor
public class PublicDataServiceImpl implements PublicDataService {

    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;
    private final XmlMapper xmlMapper = new XmlMapper();

    @Value("${publicdata.service-key}")
    private String serviceKey;

    private String callApi(int pageNo, int numOfRows) {
        URI uri = UriComponentsBuilder.fromHttpUrl("https://apis.data.go.kr/6260000/BusanTblFnrstrnStusService/getTblFnrstrnStusInfo")
                .queryParam("_type", "json")
                .queryParam("serviceKey", serviceKey)
                .queryParam("numOfRows", numOfRows)
                .queryParam("pageNo", pageNo)
                .build(true)
                .toUri();

        HttpHeaders headers = new HttpHeaders();
        headers.set(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE);
        HttpEntity<Void> entity = new HttpEntity<>(headers);

        log.info("API 호출 URI: {}", uri.toString());
        log.info("헤더: {}", headers);

        ResponseEntity<String> response = restTemplate.exchange(uri, HttpMethod.GET, entity, String.class);

        log.info("응답 상태 코드: {}", response.getStatusCode());
        log.info("응답 헤더: {}", response.getHeaders());
        log.info("응답 본문: {}", response.getBody());

        String responseBody = response.getBody();

//        if (!responseJson.trim().startsWith("{")) {
//            throw new RuntimeException("API 응답이 JSON이 아님:\n" + responseJson);
//        }

        return responseBody;
    }

    @Override
    public List<PublicDataDTO> getPublicData(PageRequestDTO dto) {
        return getPublicData(dto.getPage(), dto.getSize());
    }
//        try {
//            String responseBody = callApi(dto.getPage(), dto.getSize());
//

    /// /            PublicDataResponse wrapper = objectMapper.readValue(responseJson, PublicDataResponse.class);
//            PublicDataResponse wrapper = xmlMapper.readValue(responseBody, PublicDataResponse.class);
//            return wrapper.getResponse().getBody().getItems().getItem()
//                    .stream()
//                    .map(item -> PublicDataDTO.builder()
//                            .bsnsSector(item.getBsnsSector())
//                            .bsnsCond(item.getBsnsCond())
//                            .bsnsNm(item.getBsnsNm())
//                            .addrRoad(item.getAddrRoad())
//                            .addrJibun(item.getAddrJibun())
//                            .menu(item.getMenu())
//                            .tel(item.getTel())
//                            .specDate(item.getSpecDate())
//                            .ovrdDate(item.getOvrdDate())
//                            .gugun(item.getGugun())
//                            .dataDay(item.getDataDay())
//                            .lat(item.getLat())
//                            .lng(item.getLng())
//                            .build())
//                    .collect(Collectors.toList());
//
//        } catch (Exception e) {
//            log.error("공공데이터 파싱 오류", e);
//            throw new RuntimeException("공공데이터 파싱 실패", e);
//        }
//    }
    @Override
    public PublicDataResponse fetchData(int pageNo, int numOfRows) {
        try {
            String responseBody = callApi(pageNo, numOfRows);
//            return objectMapper.readValue(responseJson, PublicDataResponse.class);
            return xmlMapper.readValue(responseBody, PublicDataResponse.class);
        } catch (Exception e) {
            log.error("fetchData 오류", e);
            throw new RuntimeException("fetchData 중 오류 발생", e);
        }
    }

    @Override
    public List<PublicDataDTO> getPublicData(int pageNo, int numOfRows) {
        try {
            String responseBody = callApi(pageNo, numOfRows);

            // XML로 파싱한다고 가정 (xmlMapper 사용)
            PublicDataResponse wrapper = xmlMapper.readValue(responseBody, PublicDataResponse.class);

            return wrapper.getResponse()
                    .getBody()
                    .getItems()
                    .getItem()
                    .stream()
                    .map(item -> PublicDataDTO.builder()
                            .bsnsSector(item.getBsnsSector())
                            .bsnsCond(item.getBsnsCond())
                            .bsnsNm(item.getBsnsNm())
                            .addrRoad(item.getAddrRoad())
                            .addrJibun(item.getAddrJibun())
                            .menu(item.getMenu())
                            .tel(item.getTel())
                            .specDate(item.getSpecDate())
                            .ovrdDate(item.getOvrdDate())
                            .gugun(item.getGugun())
                            .dataDay(item.getDataDay())
                            .lat(item.getLat())
                            .lng(item.getLng())
                            .build())
                    .collect(Collectors.toList());

        } catch (Exception e) {
            log.error("공공데이터 파싱 오류", e);
            throw new RuntimeException("공공데이터 파싱 실패", e);
        }
    }
}