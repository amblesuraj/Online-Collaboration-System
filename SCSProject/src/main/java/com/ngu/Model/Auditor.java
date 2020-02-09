/**
 * 
 */
package com.ngu.Model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author SURAJ
 *@Date Nov 26, 2019
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class Auditor<U> implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@CreatedBy
	private U CreatedBy;
	
	@CreatedDate
	@Temporal(TemporalType.TIMESTAMP)
	private Date CreatedDate;
	
	@LastModifiedBy
	private U LastModifiedBy;

	@LastModifiedDate
	@Temporal(TemporalType.TIMESTAMP)
	private Date LastModifiedDate;
	
	
}


//
//{
//    "id": 3,
//    "fname": "user3",
//    "lname": "suername3",
//    "email": "user3@gmail.com",
//    "username": "user3"
//}