package com.cbo.core.service;

import com.cbo.core.dto.ResultWrapper;
import com.cbo.core.dto.SignatureDTO;
import com.cbo.core.dto.StampDTO;
import com.cbo.core.response.ImageRes;

import java.io.IOException;

public interface ImageService {
    ResultWrapper<SignatureDTO> addEmployeeSignature(SignatureDTO signatureDTO) throws IOException;

    ResultWrapper<StampDTO> addStamp(StampDTO stampDTO) throws IOException;

    ResultWrapper<SignatureDTO> getEmployeeSignature(Long employeeId);

    ResultWrapper<StampDTO> getStampByOrgUnitId(Long orgUnitId);

    ResultWrapper<StampDTO> getStampBySubProcessId(Long subProcessId);

    ResultWrapper<StampDTO> getStampByProcessId(Long processId);

    ImageRes getImages(Long authorityId) throws IOException;
}
