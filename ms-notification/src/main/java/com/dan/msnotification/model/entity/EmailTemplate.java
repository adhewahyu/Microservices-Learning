package com.dan.msnotification.model.entity;

import com.dan.shared.model.entity.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "email_templates", indexes = {
        @Index(name = "EMAIL_TMP_IDX_0", columnList = "type")
})
public class EmailTemplate extends BaseEntity {

    @Id
    @Column(name = "id", nullable = false, updatable = false)
    private String id;

    @Column(name = "type", length = 50, nullable = false)
    private String type;

    @Column(name = "template", columnDefinition = "text")
    private String template;

    @Column(name = "deleted")
    private Boolean deleted;

}
