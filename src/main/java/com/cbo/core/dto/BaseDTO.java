package com.cbo.core.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public abstract class BaseDTO implements Serializable {
    private static final long serialVersionUID = 1922960209727830410L;

    protected Long id;

    @JsonIgnore
    private LocalDateTime createdTimestamp;

    private LocalDateTime modifiedTimestamp;

}
