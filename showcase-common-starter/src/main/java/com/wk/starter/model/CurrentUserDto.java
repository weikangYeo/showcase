package com.wk.starter.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.context.annotation.SessionScope;

@Data
@SessionScope
@AllArgsConstructor
@NoArgsConstructor
public class CurrentUserDto {

    @JsonProperty("userId")
    private Long userId;
    @JsonProperty("userName")
    private String username;
    @JsonProperty("userEmail")
    private String email;
    @JsonProperty("roles")
    private List<CurrentUserRolesDto> roles;

    @Data
    @AllArgsConstructor
    public static class CurrentUserRolesDto {

        private Long id;
        private String roleName;
        private List<String> permissions;
    }
}
