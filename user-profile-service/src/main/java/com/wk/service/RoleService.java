package com.wk.service;

import com.wk.domain.Permission;
import com.wk.domain.Role;
import com.wk.model.RoleDto;
import com.wk.repo.PermissionRepository;
import com.wk.repo.RoleRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RoleService {
    private final RoleRepository roleRepository;
    private final PermissionRepository permissionRepository;

    public Role findRoleById(Long id) {
        return roleRepository.findById(id).orElseThrow(EntityNotFoundException::new);
    }

    @Transactional
    public RoleDto createRole(RoleDto roleDto) {
        Role role = new Role();
        role.setRoleName(roleDto.getRoleName());
        Set<Permission> permissions = getPermissionsFromKeys(roleDto.getPermissions());
        role.setPermissions(permissions);
        Role saved = roleRepository.save(role);
        return mapToDto(saved);
    }

    @Transactional
    public RoleDto updateRole(Long roleId, RoleDto roleDto) {
        Role role = roleRepository.findById(roleId)
                .orElseThrow(() -> new EntityNotFoundException("Role not found"));

        Set<Permission> permissions = getPermissionsFromKeys(roleDto.getPermissions());
        role.setRoleName(roleDto.getRoleName());
        role.setPermissions(permissions);
        Role updated = roleRepository.save(role);
        return mapToDto(updated);
    }

    private Set<Permission> getPermissionsFromKeys(Set<String> permissionKeys) {
        return permissionKeys.stream()
                .map(key -> permissionRepository.findById(key)
                        .orElseThrow(() -> new EntityNotFoundException("Permission not found: " + key)))
                .collect(Collectors.toSet());
    }

    private RoleDto mapToDto(Role role) {
        RoleDto dto = new RoleDto();
        dto.setId(role.getId());
        dto.setRoleName(role.getRoleName());
        dto.setPermissions(role.getPermissions().stream()
                .map(Permission::getPermissionKey)
                .collect(Collectors.toSet()));
        return dto;
    }
}
