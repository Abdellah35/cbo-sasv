package com.cbo.core.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Setter
@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class SignatureDTO extends BaseDTO{
    private Long employeeId;
    private MultipartFile signature;
}
