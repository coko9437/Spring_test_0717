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
public class GetTblFnrstrnStusInfo {
    @XmlElement(name = "item")
    private Header header;
    private Body body;
}
