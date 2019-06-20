package com.bookstore.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.DateSerializer;

@EntityListeners(AuditingEntityListener.class)
@MappedSuperclass
@lombok.Getter @lombok.Setter
public abstract class AuditEntity implements Serializable {

    /**
     * Serial Version UID
     */
    private static final long serialVersionUID = 2069826414448967527L;

    @CreatedDate
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "CREATED_ON", nullable = false, updatable = false)
    @JsonSerialize(using = DateSerializer.class)
    private Date createdOn;

    @LastModifiedDate
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "UPDATED_ON", nullable = false)
    @JsonSerialize(using = DateSerializer.class)
    private Date updatedOn;

}
