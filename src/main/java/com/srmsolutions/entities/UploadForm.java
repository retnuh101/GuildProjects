package com.srmsolutions.entities;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
public class UploadForm {
    private String description;

    // Upload files.
    private MultipartFile[] fileDatas;
}
