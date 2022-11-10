package com.dan.mstask.model.entity;

import com.dan.shared.model.entity.BaseApprovalEntity;
import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "tasks", indexes = {
        @Index(name = "TASKS_INDX_0", columnList = "module")
})
public class Task extends BaseApprovalEntity {

    @Id
//    @GenericGenerator(name = "db-uuid", strategy = "guid")
//    @GeneratedValue(generator = "db-uuid")
    @Column(name = "id", updatable = false, nullable = false, length = 50)
    private String id;

    @Column(name="module", length = 25)
    private String module;

    @Column(name = "action", length = 10)
    private String action;

    @Column(name = "task_before", columnDefinition = "text")
    private String taskBefore;

    @Column(name = "task_after", columnDefinition = "text")
    private String taskAfter;

    @Column(name = "status")
    private Integer status;

}
