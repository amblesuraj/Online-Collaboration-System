package com.ngu.Model;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id")
@JsonInclude(JsonInclude.Include.NON_NULL)	
@JsonIgnoreProperties(value = "profilePicture")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Scope(proxyMode = ScopedProxyMode.TARGET_CLASS,value = "request")
@Table(name = "user")
public class User implements Serializable{


    private static final long serialVersionUID = 1L;

    @Id
//	    @GeneratedValue(generator="sequence_user_id")
//		@GenericGenerator(name="sequence_user_id", strategy = "com.ngu.CustomIdGenerator.CustomUserId")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotEmpty(message = "This field should not be empty")
    @Column(name = "fname")
    private String fname;

    @NotEmpty(message = "This field should not be empty")
    @Column(name = "lname")
    private String lname;

    @Email
    @NotEmpty(message = "This field should not be empty")
    @Column(name = "email")
    private String email;

    @NotEmpty(message = "This field should not be empty")
    @Column(name = "username")
    private String username;

    @Transient
    @Column(name = "confirmPassword")
    private String confirmPassword;

    @NotEmpty(message = "This field should not be empty")
    @Column(name = "password")
    private String password;

    @Column(name = "phone")
    private long phone;

    @Column(name = "active")
    private boolean active;

//    @JsonIgnore
    @Column(name = "profilePicture")
    public String profilePicture;

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(name = "user_role", joinColumns = {@JoinColumn(name = "user_id")}, inverseJoinColumns = {@JoinColumn(name = "role_id")})
    private Set<Role> roles = new HashSet<>();

    private Date lastLogin;

    private String resetToken;
    
    
    
    @OneToOne(cascade = CascadeType.ALL,mappedBy = "user",fetch = FetchType.EAGER)
    private Profile profile;
    
    @OneToOne(cascade = CascadeType.ALL,mappedBy = "user",fetch = FetchType.EAGER)
    private Post post;
    
    

}
