package com.wk.mapper;

import com.wk.domain.UserProfile;
import com.wk.model.UserProfileDto;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface UserProfileMapper {

    UserProfileDto toDto(UserProfile userProfile);
    UserProfile toEntity(UserProfileDto dto);
}
