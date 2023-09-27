package com.cbo.core.controller;


import com.cbo.core.dto.BaseDTO;
import com.cbo.core.exception.AppRuntimeException;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.http.HttpEntity;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;

public abstract class BaseApplicationController {


    protected void validateRequest(HttpEntity<? extends BaseDTO> requestData) {

        if (requestData == null || requestData.getBody() == null) {
            throw new AppRuntimeException("Your request cannot be processed, please cont with ICT support");
        }
    }

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
    }
}
