package com.dan.msuser.model.entity;

import com.dan.shared.model.entity.BaseEntity;
import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "permissions", indexes = {
        @Index(name = "PERMISSION_INDX_0", columnList = "is_deleted"),
        @Index(name = "PERMISSION_INDX_1", columnList = "is_active")
})
public class Permission extends BaseEntity {

    @Id
//    @GenericGenerator(name = "db-uuid", strategy = "guid")
//    @GeneratedValue(generator = "db-uuid")
    @Column(name = "id", updatable = false, nullable = false, length = 50)
    private String id;

    @Column(name = "description", length = 150)
    private String description;

    @Column(name = "group", length = 100)
    private String group;

    @Column(name = "level")
    private Integer level;

    @Column(name = "is_third_party")
    private Boolean isThirdParty;

    @Column(name = "api_path", length = 150)
    private String apiPath;

}
