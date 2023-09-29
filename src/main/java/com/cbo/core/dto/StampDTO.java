package com.cbo.core.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Setter
@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class StampDTO extends BaseDTO{
    private Long organizationUnitId;
    private Long subProcessId;
    private Long processId;
    private MultipartFile stamp;
}
