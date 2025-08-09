package com.wk.mapper;

import com.wk.domain.Permission;
import com.wk.domain.Role;
import com.wk.model.RoleDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.Named;

import java.util.Set;
import java.util.stream.Collectors;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface RoleMapper {

    @Mapping(source = "permissions", target = "permissions", qualifiedByName = "permissionsToKeys")
    RoleDto toDto(Role role);

    @Named("permissionsToKeys")
    default Set<String> mapPermissionsToKeys(Set<Permission> permissions) {
        if (permissions == null) {
            return null;
        }
        return permissions.stream()
                .map(Permission::getPermissionKey)
                .collect(Collectors.toSet());
    }
}
