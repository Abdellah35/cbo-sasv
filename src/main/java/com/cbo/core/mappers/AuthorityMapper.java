package com.cbo.core.mappers;

import com.cbo.core.dto.AuthorityDTO;
import com.cbo.core.persistence.model.Authority;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

import java.util.ArrayList;
import java.util.List;

@Mapper(componentModel = "spring")
public interface AuthorityMapper {

    AuthorityMapper INSTANCE = Mappers.getMapper(AuthorityMapper.class);

    AuthorityDTO toDTO(Authority authority);

    void copyToDTO(Authority accountInfo, @MappingTarget AuthorityDTO authorityDTO);

    Authority toEntity(AuthorityDTO authorityDTO);

    default List<AuthorityDTO> authoritiesToAuthorityDTOs(List<Authority> accountInfos) {
        if (accountInfos == null) {
            return null;
        }

        List<AuthorityDTO> list = new ArrayList<AuthorityDTO>(accountInfos.size());
        for (Authority accountInfo : accountInfos) {
            list.add(toDTO(accountInfo));
        }

        return list;
    }
}