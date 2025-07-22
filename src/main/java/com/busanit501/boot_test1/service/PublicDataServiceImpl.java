package com.busanit501.boot_test1.service;

import com.busanit501.boot_test1.dto.PageRequestDTO;
import com.busanit501.boot_test1.dto.publicData.PublicDataDTO;
import com.busanit501.boot_test1.dto.publicData.PublicDataResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
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
public class PublicDataServiceImpl implements PublicDataService{

    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;

    @Value("${publicdata.service-key}")
    private String serviceKey;

    @Override
    public List<PublicDataDTO> getPublicData(PageRequestDTO pageRequestDTO) {

        URI uri = UriComponentsBuilder.fromHttpUrl("https://apis.data.go.kr/6260000/BusanTblFnrstrnStusService/getTblFnrstrnStusInfo")
                .queryParam("_type", "json")
                .queryParam("serviceKey", serviceKey)
                .queryParam("numOfRows", pageRequestDTO.getSize())
                .queryParam("pageNo", pageRequestDTO.getPage())
                .build(true)
                .toUri();

//        log.info("요청 URI 1 : {}", uri);

        try {
            HttpHeaders headers = new HttpHeaders();
            headers.set(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE);  // JSON 응답 요청

            HttpEntity<Void> entity = new HttpEntity<>(headers);

            // API 호출 (GET 방식)
            ResponseEntity<String> response = restTemplate.exchange(
                    uri,
                    HttpMethod.GET,
                    entity,
                    String.class
            );

            String responseJson = response.getBody();

            // 👉 2. 응답 내용 출력 및 JSON 여부 확인
            log.info("⚠️ 응답 내용 시작 ⚠️\n{}\n⚠️ 응답 내용 끝 ⚠️", responseJson);
            if (!responseJson.trim().startsWith("{")) {
                throw new RuntimeException("API 응답이 JSON이 아닙니다. 실제 응답:\n" + responseJson);
            }

            // JSON → Java 객체로 변환
            PublicDataResponse wrapper = objectMapper.readValue(responseJson, PublicDataResponse.class);

            // 내부 구조에 따라 DTO 리스트 추출
            List<PublicDataDTO> dtoList = wrapper.getResponse()
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
            log.info("최종 호출 URL 2 : {}", uri.toString());
            return dtoList;

        } catch (Exception e) {
            log.error("공공데이터 파싱 오류", e);
            throw new RuntimeException("공공데이터 파싱 실패", e);
        }
    }

    @Override
    public PublicDataResponse fetchData(int pageNo, int numOfRows) {
        URI uri = UriComponentsBuilder.fromHttpUrl("https://apis.data.go.kr/6260000/BusanTblFnrstrnStusService/getTblFnrstrnStusInfo")
                .queryParam("_type", "json")
                .queryParam("serviceKey", serviceKey)
                .queryParam("numOfRows", numOfRows)
                .queryParam("pageNo", pageNo)
                .build(true)
                .toUri();

        try {
            HttpHeaders headers = new HttpHeaders();
            headers.set(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE);

            HttpEntity<Void> entity = new HttpEntity<>(headers);

            ResponseEntity<String> response = restTemplate.exchange(
                    uri,
                    HttpMethod.GET,
                    entity,
                    String.class
            );

            String responseJson = response.getBody();

            log.info("⚠️ fetchData 응답 내용 ⚠️\n{}", responseJson);

            if (!responseJson.trim().startsWith("{")) {
                throw new RuntimeException("API 응답이 JSON이 아닙니다. 실제 응답:\n" + responseJson);
            }

            // PublicDataResponse는 내부에 'response' 필드를 갖고 있으므로 이대로 deserialize 가능
            PublicDataResponse publicDataResponse = objectMapper.readValue(responseJson, PublicDataResponse.class);
            return publicDataResponse;

        } catch (Exception e) {
            log.error("fetchData 오류 발생", e);
            throw new RuntimeException("fetchData 중 오류 발생", e);
        }
    }
}