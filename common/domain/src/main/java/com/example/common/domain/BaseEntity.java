package com.example.common.domain;

import jakarta.persistence.AttributeOverride;
import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;

@Getter
@MappedSuperclass
public abstract class BaseEntity extends BaseTimeEntity {

    @CreatedBy
    @AttributeOverride(name = "id", column = @Column(name = "created_by", nullable = false))
    protected Creator createdBy;

    @LastModifiedBy
    @AttributeOverride(name = "id", column = @Column(name = "last_modified_by", nullable = false))
    protected Modifier lastModifiedBy;
}
