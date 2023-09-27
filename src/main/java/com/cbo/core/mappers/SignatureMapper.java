package com.cbo.core.mappers;

import com.cbo.core.dto.SignatureDTO;
import com.cbo.core.persistence.model.Signature;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

import java.util.ArrayList;
import java.util.List;

@Mapper(componentModel = "spring")
public interface SignatureMapper {

    SignatureMapper INSTANCE = Mappers.getMapper(SignatureMapper.class);

    SignatureDTO toDTO(Signature authority);

    void copyToDTO(Signature accountInfo, @MappingTarget SignatureDTO authorityDTO);

    Signature toEntity(SignatureDTO authorityDTO);

    default List<SignatureDTO> signatureToSignatureDTOs(List<Signature> accountInfos) {
        if (accountInfos == null) {
            return null;
        }

        List<SignatureDTO> list = new ArrayList<SignatureDTO>(accountInfos.size());
        for (Signature accountInfo : accountInfos) {
            list.add(toDTO(accountInfo));
        }

        return list;
    }
}