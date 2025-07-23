package com.busanit501.boot_test1.dto.publicData;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import lombok.*;

import java.util.List;

@Data
public class Body {

    @JacksonXmlProperty(localName = "items")
    private Items items;

    @JacksonXmlProperty(localName = "numOfRows")
    private int numOfRows;

    @JacksonXmlProperty(localName = "pageNo")
    private int pageNo;

    @JacksonXmlProperty(localName = "totalCount")
    private int totalCount;
}

//    @JacksonXmlProperty(localName = "totalCount")
//    private int totalCount;
//
//    @JacksonXmlElementWrapper(useWrapping = false)
//    @JacksonXmlProperty(localName = "item")
//    private List<PublicDataDTO> items;
//
//    @JacksonXmlProperty(localName = "pageNo")
//    private int pageNo;
//
//    @JacksonXmlProperty(localName = "numOfRows")
//    private int numOfRows;
//}

