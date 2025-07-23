package com.busanit501.boot_test1.service;

import com.busanit501.boot_test1.dto.publicData.PublicDataDTO;
import com.busanit501.boot_test1.dto.publicData.PublicDataPage;
import com.busanit501.boot_test1.dto.publicData.PublicDataResponse;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;

@Service
@RequiredArgsConstructor
@Log4j2
public class PublicDataServiceImpl implements PublicDataService {

    @Value("${publicdata.service-key}")
    private String serviceKey;

    @Override
    public PublicDataPage getPublicData(int pageNo, int numOfRows) throws Exception {
        String apiUrl = "http://apis.data.go.kr/6260000/BusanTblFnrstrnStusService/getTblFnrstrnStusInfo"
                + "?serviceKey=" + serviceKey
                + "&numOfRows=" + numOfRows
                + "&pageNo=" + pageNo;

        URI uri = new URI(apiUrl);
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder(uri).build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        String xml = response.body();
        log.info("== API 응답 XML ==\n{}", xml);

        XmlMapper xmlMapper = new XmlMapper();
        PublicDataResponse publicDataResponse = xmlMapper.readValue(xml, PublicDataResponse.class);

        if (!"00".equals(publicDataResponse.getHeader().getResultCode())) {
            throw new IllegalStateException("공공데이터 API 호출 실패: "
                    + publicDataResponse.getHeader().getResultMsg());
        }

        // 페이징 관련 데이터 추출
        PublicDataPage page = new PublicDataPage();
        page.setItems(publicDataResponse.getBody().getItems().getItem());
        page.setTotalCount(publicDataResponse.getBody().getTotalCount());
        page.setPageNo(publicDataResponse.getBody().getPageNo());
        page.setNumOfRows(publicDataResponse.getBody().getNumOfRows());

        log.info("가져온 데이터 개수: {}", page.getItems().size());
        log.info("전체 데이터 개수: {}", page.getTotalCount());

        return page;
    }

    @Override
    public String getRawPublicDataXml(int pageNo, int numOfRows) throws Exception {
        String apiUrl = "http://apis.data.go.kr/6260000/BusanTblFnrstrnStusService/getTblFnrstrnStusInfo"
                + "?serviceKey=" + serviceKey
                + "&numOfRows=" + numOfRows
                + "&pageNo=" + pageNo;

        URI uri = new URI(apiUrl);
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder(uri).build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        String xml = response.body();
        log.info("== API 원본 XML 응답 ==\n{}", xml);

        return xml;
    }
}