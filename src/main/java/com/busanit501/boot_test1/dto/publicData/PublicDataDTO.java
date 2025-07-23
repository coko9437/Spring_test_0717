package com.busanit501.boot_test1.dto.publicData;


import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JacksonXmlRootElement(localName = "item")
public class PublicDataDTO {
    @JacksonXmlProperty(localName = "bsnsSector")
    private String bsnsSector;

    @JacksonXmlProperty(localName = "bsnsCond")
    private String bsnsCond;

    @JacksonXmlProperty(localName = "bsnsNm")
    private String bsnsNm;

    @JacksonXmlProperty(localName = "addrRoad")
    private String addrRoad;

    @JacksonXmlProperty(localName = "addrJibun")
    private String addrJibun;

    @JacksonXmlProperty(localName = "menu")
    private String menu;

    @JacksonXmlProperty(localName = "tel")
    private String tel;

    @JacksonXmlProperty(localName = "specDate")
    private String specDate;

    @JacksonXmlProperty(localName = "ovrdDate")
    private String ovrdDate;

    @JacksonXmlProperty(localName = "gugun")
    private String gugun;

    @JacksonXmlProperty(localName = "dataDay")
    private String dataDay;

    @JacksonXmlProperty(localName = "lat")
    private String lat;

    @JacksonXmlProperty(localName = "lng")
    private String lng;
}
