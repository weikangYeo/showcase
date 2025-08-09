package com.wk.model;

import java.util.List;
import lombok.Data;

@Data
public class KeyCloakUserClaimDto {
    private Long userId;
    private String userEmail;
    private String userName;
    private List<RoleDto> roles;
}
