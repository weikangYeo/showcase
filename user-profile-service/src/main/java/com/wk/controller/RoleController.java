package com.wk.controller;

import com.wk.model.RoleDto;
import com.wk.service.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("roles")
@RequiredArgsConstructor
public class RoleController {
    private final RoleService roleService;
    @PostMapping
    public RoleDto createRole(@RequestBody RoleDto roleDto) {
        return roleService.createRole(roleDto);
    }

    @PutMapping("{roleId}")
    public RoleDto updateRole(@PathVariable Long roleId, @RequestBody RoleDto roleDto) {
        return roleService.updateRole(roleId, roleDto);
    }
}
