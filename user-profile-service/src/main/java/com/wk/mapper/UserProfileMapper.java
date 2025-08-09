package com.wk.mapper;

import com.wk.domain.UserProfile;
import com.wk.model.KeyCloakUserClaimDto;
import com.wk.model.UserProfileDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, uses = {RoleMapper.class})
public interface UserProfileMapper {

    UserProfileDto toDto(UserProfile userProfile);

    @Mapping(source = "name", target = "username")
    UserProfile toEntity(UserProfileDto dto);

    @Mapping(source = "id", target = "userId")
    @Mapping(source = "email", target = "userEmail")
    @Mapping(source = "username", target = "userName")
    @Mapping(source = "roles", target = "roles")
    KeyCloakUserClaimDto toKeyCloakUserClaimDto(UserProfile userProfile);
}
