package com.wk.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class Permission {
    @Id
    private String permissionKey;
    private String name;
    private String description;
}
