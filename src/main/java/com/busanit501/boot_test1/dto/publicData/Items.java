package com.busanit501.boot_test1.dto.publicData;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import lombok.Data;

import java.util.List;

@Data
@XmlRootElement(name = "items")
@XmlAccessorType(XmlAccessType.FIELD)
public class Items {
    @XmlElement(name = "item")
    private List<PublicDataDTO> item;
}
