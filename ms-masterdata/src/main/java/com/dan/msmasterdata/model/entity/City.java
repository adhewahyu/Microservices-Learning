package com.dan.msmasterdata.model.entity;

import com.dan.shared.model.entity.BaseEntity;
import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "cities", indexes = {
        @Index(name = "CITY_INDX_0", columnList = "is_deleted"),
        @Index(name = "CITY_INDX_1", columnList = "is_active")
})
public class City extends BaseEntity {

    @Id
//    @GenericGenerator(name = "db-uuid", strategy = "guid")
//    @GeneratedValue(generator = "db-uuid")
    @Column(name = "id", updatable = false, nullable = false, length = 50)
    private String id;

    @Column(name = "city_code", length = 20)
    private String cityCode;

    @Column(name = "city", length = 150)
    private String cityName;

    @Column(name = "is_deleted")
    private Boolean isDeleted;

    @Column(name = "is_active")
    private Boolean isActive;

    //foreign
    @Column(name = "province_code", length = 20, nullable = false)
    private String provinceCode;
}
