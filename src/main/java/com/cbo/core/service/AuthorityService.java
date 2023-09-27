package com.cbo.core.service;


import com.cbo.core.dto.AuthorityDTO;
import com.cbo.core.dto.ResultWrapper;
import com.cbo.core.persistence.model.Authority;

import java.util.List;

public interface AuthorityService {

    ResultWrapper<AuthorityDTO> registerAuthority(AuthorityDTO authorityDTO);

    ResultWrapper<List<AuthorityDTO>> findAllAuthorities();

    ResultWrapper<AuthorityDTO> updateAuthority(AuthorityDTO authorityDTO);

    ResultWrapper<AuthorityDTO> findAuthorityById(AuthorityDTO authorityDTO);

    Authority findAuthority(Long id);

    ResultWrapper<List<AuthorityDTO>> findAllActiveAuthorities();

    ResultWrapper<AuthorityDTO> deleteAuthority(AuthorityDTO authorityDTO);


}