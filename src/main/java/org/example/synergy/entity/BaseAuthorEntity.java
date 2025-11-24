package org.example.synergy.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

@MappedSuperclass
@Data
@EntityListeners({AuditingEntityListener.class})
public abstract class BaseAuthorEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;


    @Column(columnDefinition = "Boolean default false")
    private Boolean isDeleted = false;

    @CreatedBy
    @Column(name = "created_by", updatable = false)
    private String createdBY;

    @CreatedDate
    @Column(name = "created_date", updatable = false)
    private LocalDateTime createdDate;

    @LastModifiedBy
    @Column(name = "updated_user")
    private String updatedUser;

    @LastModifiedDate
    @Column(name = "updated_date")
    private LocalDateTime updatedDate;

}
