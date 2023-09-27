package com.cbo.core.dto;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Setter
@Getter
public class PageDTO implements Serializable {

    private static final long serialVersionUID = 6328361336342412365L;

    private int currentPage;
    private long totalRows;
    private int pageSize;
    private String sortColumn;
    private String sortDirection = "desc";

}
