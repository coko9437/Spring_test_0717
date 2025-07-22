package com.busanit501.boot_test1.dto.publicData;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlRootElement;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@XmlRootElement(name = "item")
@XmlAccessorType(XmlAccessType.FIELD)
public class PublicDataDTO {
    private String bsnsSector;     // 업종
    private String bsnsCond;       // 업태
    private String bsnsNm;         // 업소명
    private String addrRoad;       // 도로명 주소
    private String addrJibun;      // 지번 주소
    private String menu;           // 대표 메뉴
    private String tel;            // 전화번호
    private String specDate;       // 지정일자
    private String ovrdDate;       // 재지정일자
    private String gugun;          // 구군명
    private String dataDay;        // 데이터 기준일자
    private double lat;            // 위도
    private double lng;            // 경도
}
