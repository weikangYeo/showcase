package com.wk.service;

import com.wk.domain.Permission;
import com.wk.model.PermissionDto;
import com.wk.repo.PermissionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class PermissionService {

    private final PermissionRepository permissionRepository;

    public PermissionDto createPermission(PermissionDto permissionDto) {
        Permission permission = new Permission();
        permission.setPermissionKey(permissionDto.getPermissionKey());
        permission.setName(permissionDto.getName());
        permission.setDescription(permissionDto.getDescription());

        Permission saved = permissionRepository.save(permission);
        return mapToDto(saved);
    }

    private PermissionDto mapToDto(Permission permission) {
        PermissionDto dto = new PermissionDto();
        dto.setPermissionKey(permission.getPermissionKey());
        dto.setName(permission.getName());
        dto.setDescription(permission.getDescription());
        return dto;
    }
}
