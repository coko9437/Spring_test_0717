package com.busanit501.boot_test1.dto.publicData;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import lombok.Data;
//@XmlElement(name = "item")
@XmlRootElement(name = "items")
@XmlAccessorType(XmlAccessType.FIELD)
@Data
public class Body   {
    @XmlElement(name = "item")
    private Items items;
    private int numOfRows;
    private int pageNo;
    private int totalCount;
}
