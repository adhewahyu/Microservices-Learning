package com.dan.msuser.model.entity;

import com.dan.shared.model.entity.BaseEntity;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
@Table(name = "users", indexes = {
        @Index(name = "USER_INDX_0", columnList = "is_deleted"),
        @Index(name = "USER_INDX_1", columnList = "is_active")
})
public class User extends BaseEntity {

    @Id
//    @GenericGenerator(name = "db-uuid", strategy = "guid")
//    @GeneratedValue(generator = "db-uuid")
    @Column(name = "id", updatable = false, nullable = false, length = 50)
    private String id;

    @Column(name = "email", unique = true, nullable = false, length = 150)
    private String email;

    @Column(name = "password", nullable = false, length = 150)
    private String password;

    @Column(name = "fullname", nullable = false, length = 150)
    private String fullname;

    @Column(name = "is_deleted", nullable = false)
    private Boolean isDeleted;

    @Column(name = "is_active", nullable = false)
    private Boolean isActive;

    @Column(name = "wrong_password")
    private Integer wrongPassword;

    @Column(name = "last_login_date")
    private Date lastLoginDate;

    @Column(name = "role_id", nullable = false, length = 50)
    private String roleId;

}
