package com.wk.model;
import lombok.Data;
import java.util.Set;

@Data
public class RoleDto {
    private Long id;
    private String roleName;
    private Set<String> permissions;
}

