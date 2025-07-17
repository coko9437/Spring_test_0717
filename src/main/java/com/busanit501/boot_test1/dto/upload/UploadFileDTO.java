package com.busanit501.boot_test1.dto.upload;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Data
public class UploadFileDTO {
    private List<MultipartFile> files;
}
