package com.dan.mstask.model.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.sql.Timestamp;

@Data
@Entity
@Table(name = "shedlock")
public class Shedlock implements Serializable {

    @Id
    @Column(name = "name", length = 64)
    private String name;

    @Column(name = "lock_until")
    private Timestamp lockUntil;

    @Column(name = "locked_at")
    private Timestamp lockedAt;

    @Column(name = "locked_by", length = 64)
    private String lockedBy;

}
