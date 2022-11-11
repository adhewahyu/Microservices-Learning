package com.dan.msmasterdata.model.entity;

import com.dan.shared.model.entity.BaseEntity;
import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "provinces", indexes = {
        @Index(name = "PROVINCE_INDX_0", columnList = "is_deleted"),
        @Index(name = "PROVINCE_INDX_1", columnList = "is_active")
})
public class Province extends BaseEntity {

    @Id
//    @GenericGenerator(name = "db-uuid", strategy = "guid")
//    @GeneratedValue(generator = "db-uuid")
    @Column(name = "id", updatable = false, nullable = false, length = 50)
    private String id;

    @Column(name = "province_code", length = 20)
    private String provinceCode;

    @Column(name = "province_name", length = 150)
    private String provinceName;

    @Column(name = "is_deleted")
    private Boolean isDeleted;

    @Column(name = "is_active")
    private Boolean isActive;

}
