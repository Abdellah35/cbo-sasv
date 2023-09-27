package com.cbo.core.mappers;

import com.cbo.core.dto.StampDTO;
import com.cbo.core.persistence.model.Stamp;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

import java.util.ArrayList;
import java.util.List;

@Mapper(componentModel = "spring")
public interface StampMapper {

    StampMapper INSTANCE = Mappers.getMapper(StampMapper.class);

    StampDTO toDTO(Stamp authority);

    void copyToDTO(Stamp accountInfo, @MappingTarget StampDTO authorityDTO);

    Stamp toEntity(StampDTO authorityDTO);

    default List<StampDTO> stampsToStampDTOs(List<Stamp> accountInfos) {
        if (accountInfos == null) {
            return null;
        }

        List<StampDTO> list = new ArrayList<StampDTO>(accountInfos.size());
        for (Stamp accountInfo : accountInfos) {
            list.add(toDTO(accountInfo));
        }

        return list;
    }
}
