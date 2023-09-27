package com.cbo.core.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;


@JsonIgnoreProperties(ignoreUnknown = true)
public class ResultWrapper<T> {

    private static final long serialVersionUID = -2973881509735735595L;

    private PageDTO page = new PageDTO();

    private String message;
    private boolean status;

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public ResultWrapper() {
    }

    public ResultWrapper(T obj) {
        this.result = obj;
    }

    private T result;

    public T getResult() {
        return result;
    }

    public void setResult(T result) {
        this.result = result;
    }

    public PageDTO getPage() {
        return page;
    }

    public void setPage(PageDTO page) {
        this.page = page;
    }

    public boolean hasResult() {
        return this.getResult() != null;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
