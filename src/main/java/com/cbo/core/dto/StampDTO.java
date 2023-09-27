package com.cbo.core.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class StampDTO {

    private Long organizationUnitId;
    private Long subProcessId;
    private Long processId;
    private MultipartFile stamp;
}
