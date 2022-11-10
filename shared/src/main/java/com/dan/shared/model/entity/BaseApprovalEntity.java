package com.dan.shared.model.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import java.util.Date;

@Data
@MappedSuperclass
public class BaseApprovalEntity extends BaseEntity {

    @Column(name = "approved_date")
    private Date approvedDate;

    @Column(name = "approved_by", length = 50)
    private String approvedBy;

    @Column(name = "rejected_date")
    private Date rejectedDate;

    @Column(name = "rejected_by", length = 50)
    private String rejectedBy;

}
