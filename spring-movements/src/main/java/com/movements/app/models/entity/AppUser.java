package com.movements.app.models.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name = "users")
public class AppUser implements Serializable {

	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotEmpty
	@Column(length = 30, unique = true)
	private String username;

	@NotEmpty
	@Column(length = 60)
	private String password;

	private Boolean enabled;
	
	private Boolean tokenExpired;
	
	@Column(name = "create_at")
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "dd/MM/yyyy hh:mm:ss")
	private Date createAt;

	@NotEmpty
	@OneToMany(mappedBy="userRolePK.user", fetch=FetchType.LAZY, cascade = CascadeType.ALL)
	private List<UserRole> roles;
	
    
	public AppUser() {
		this.roles = new ArrayList<UserRole>();
	}


	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Boolean getEnabled() {
		return enabled;
	}

	public void setEnabled(Boolean enabled) {
		this.enabled = enabled;
	}

	public Date getCreateAt() {
		return createAt;
	}

	public void setCreateAt(Date createAt) {
		this.createAt = createAt;
	}

	
	
	/*
	 * public void setUserAuthorities(List<UserAuthority> userAuthorities) {
	 * this.userAuthorities = userAuthorities; }
	 * 
	 * public List<UserAuthority> getUserAuthorities() { return userAuthorities; }
	 * 
	 * public void addUserAuthority(UserAuthority userAuthority) {
	 * this.userAuthorities.add(userAuthority); }
	 */
	
	public Boolean getTokenExpired() {
		return tokenExpired;
	}

	public void setTokenExpired(Boolean tokenExpired) {
		this.tokenExpired = tokenExpired;
	}

	public List<UserRole> getRoles() {
		return roles;
	}

	public void setRoles(List<UserRole> roles) {
		this.roles = roles;
	}
	
	public void addRoles(UserRole role) {
		roles.add(role);
	}

	public boolean isTokenExpired() {
		return tokenExpired;
	}

	public void setTokenExpired(boolean tokenExpired) {
		this.tokenExpired = tokenExpired;
	}

	@PrePersist
	public void prePersist() {
		createAt = new Date();
	}
	
	private static final long serialVersionUID = 1L;



	
	

}
