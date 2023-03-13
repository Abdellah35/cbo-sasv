package com.cbo.core.model;

import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Authority {


    private Long divisionId;
    private Long employeeId;
}
