package com.ngu.Model;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ngu.Enum.NotificationStatus;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "notification")
@AllArgsConstructor
@NoArgsConstructor
public class Notification {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	private String type;
	private String message;
	
	private NotificationStatus status;
	
	private Date date;
	
	@JsonIgnore
	@ManyToOne(cascade = CascadeType.REFRESH,optional = true)
	@JoinColumn(name = "user_id",referencedColumnName = "id")
	private User user;
	
	@JsonIgnore
	@ManyToOne(cascade = CascadeType.REFRESH,optional = true)
	@JoinColumn(name = "post_id",referencedColumnName = "id")
	private Post post;
}
