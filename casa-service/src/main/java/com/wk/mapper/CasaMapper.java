package com.wk.mapper;

import com.wk.domain.CasaAccount;
import com.wk.model.CasaAccountDto;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface CasaMapper {
    CasaAccountDto toDto(CasaAccount casaAccount);
}
