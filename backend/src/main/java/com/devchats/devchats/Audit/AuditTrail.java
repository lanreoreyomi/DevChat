package com.devchats.devchats.Audit;


import java.util.Date;
import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Getter
@Setter

@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
@ToString
public abstract class AuditTrail {

 @Column(name = "created_by", nullable = false, updatable = false)
 @CreatedBy
 private String createdBy;

 @Temporal(TemporalType.TIMESTAMP)
 @Column(name = "created_at", nullable = false, updatable = false)
 @CreatedDate
 private Date createdAt;

 @Temporal(TemporalType.TIMESTAMP)
 @Column(name = "updated_at", nullable = false)
 @LastModifiedDate
 private Date updatedAt;

}


