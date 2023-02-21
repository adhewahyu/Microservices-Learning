package com.dan.msuser.model.entity;

import com.dan.shared.model.entity.BaseEntity;
import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "roles", indexes = {
        @Index(name = "ROLE_INDX_0", columnList = "is_deleted"),
        @Index(name = "ROLE_INDX_1", columnList = "is_active")
})
public class Role extends BaseEntity {

    @Id
//    @GenericGenerator(name = "db-uuid", strategy = "guid")
//    @GeneratedValue(generator = "db-uuid")
    @Column(name = "id", updatable = false, nullable = false, length = 50)
    private String id;

    @Column(name = "name", nullable = false, length = 100)
    private String name;

    @Column(name = "description", length = 100)
    private String description;

    @Column(name = "is_deleted", nullable = false)
    private Boolean isDeleted;

    @Column(name = "is_active", nullable = false)
    private Boolean isActive;

    @Column(name = "permission_list", columnDefinition = "text")
    private String permissionList;

    @Column(name = "user_list", columnDefinition = "text")
    private String userList;
}
