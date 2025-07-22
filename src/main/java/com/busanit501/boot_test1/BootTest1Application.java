package com.busanit501.boot_test1;

import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.xml.MappingJackson2XmlHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
@Configuration
public class BootTest1Application {

    public static void main(String[] args) {
        SpringApplication.run(BootTest1Application.class, args);
    }

    @Bean
    public RestTemplate restTemplate() {
        RestTemplate restTemplate = new RestTemplate();

        // XML 컨버터 추가
        restTemplate.getMessageConverters().add(new MappingJackson2XmlHttpMessageConverter(new XmlMapper()));

        return restTemplate;
    }

    @Bean
    public XmlMapper xmlMapper() {
        return new XmlMapper();
    }

}
