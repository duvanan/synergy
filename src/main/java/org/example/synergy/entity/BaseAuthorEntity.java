package org.example.synergy.entity;

import jakarta.persistence.*;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.io.Serializable;
import java.util.Date;

@MappedSuperclass
@EntityListeners({AuditingEntityListener.class})
public abstract class BaseAuthorEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(columnDefinition = "Boolean default true")
    private Boolean isActive = true;

    @Column(columnDefinition = "Boolean default false")
    private Boolean isDeleted = false;

    @CreatedBy
    @Column(name = "created_user", updatable = false)
    private String createdUser;

    @CreatedDate
    @Column(name = "created_date", updatable = false)
    private Date createdDate;

    @LastModifiedBy
    @Column(name = "updated_user")
    private String updatedUser;

    @LastModifiedDate
    @Column(name = "updated_date")
    private Date updatedDate;

    // Getters & Setters
    public Long getId() { return id; }

    public Boolean getIsActive() { return isActive; }

    public Boolean getIsDeleted() { return isDeleted; }

    public String getCreatedUser() { return createdUser; }

    public Date getCreatedDate() { return createdDate; }

    public String getUpdatedUser() { return updatedUser; }

    public Date getUpdatedDate() { return updatedDate; }

    public void setId(final Long id) { this.id = id; }

    public void setIsActive(final Boolean isActive) { this.isActive = isActive; }

    public void setIsDeleted(final Boolean isDeleted) { this.isDeleted = isDeleted; }

    public void setCreatedUser(final String createdUser) { this.createdUser = createdUser; }

    public void setCreatedDate(final Date createdDate) { this.createdDate = createdDate; }

    public void setUpdatedUser(final String updatedUser) { this.updatedUser = updatedUser; }

    public void setUpdatedDate(final Date updatedDate) { this.updatedDate = updatedDate; }
}
