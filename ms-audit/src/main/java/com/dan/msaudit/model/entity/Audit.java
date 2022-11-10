package com.dan.msaudit.model.entity;

import com.dan.shared.model.entity.BaseEntity;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Data
@Entity
@Table(name = "audits", indexes = {
    @Index(name = "AUDITS_INDX_0", columnList = "module")
})
public class Audit extends BaseEntity {

    @Id
//    @GenericGenerator(name = "db-uuid", strategy = "guid")
//    @GeneratedValue(generator = "db-uuid")
    @Column(name = "id", updatable = false, nullable = false, length = 50)
    private String id;

    @Column(name="module", length = 25)
    private String module;

    @Column(name="activity", columnDefinition = "text")
    private String activity;

    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_date")
    private Date createdDate;

    @Column(name = "created_by", length = 50)
    private String createdBy;

}
