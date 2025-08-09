package com.wk.controller;

import com.wk.model.PermissionDto;
import com.wk.service.PermissionService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("permissions")
@RequiredArgsConstructor
public class PermissionController {
    private final PermissionService permissionService;

    @PostMapping
    public PermissionDto createPermission(@RequestBody PermissionDto permissionDto) {
        return permissionService.createPermission(permissionDto);
    }
}
