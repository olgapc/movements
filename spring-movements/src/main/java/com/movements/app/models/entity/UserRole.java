package com.movements.app.models.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.format.annotation.DateTimeFormat;

import com.movements.app.models.pks.UserRolePK;;

@Entity
@Table(name = "users_roles")
public class UserRole implements Serializable {

	@EmbeddedId
	private UserRolePK userRolePK;

	@Column(name = "create_at")
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "dd/MM/yyyy hh:mm:ss")
	private Date createAt;

	public UserRole() {}
	
	public UserRole(UserRolePK userRolePK) {
		this.userRolePK = userRolePK;
	}
	

	public AppUser getUser() {
		return userRolePK.getUser();
	}
	
	public void setUser(AppUser user) {
		userRolePK.setUser(user);
	}
	
	public Role getRole() {
		return userRolePK.getRole();
	}
	
	public void setRole(Role role) {
		userRolePK.setRole(role);
	}
	
	public String getDescription() {
		return userRolePK.getRole().getDescription();
	}
	
	
	public Date getCreateAt() {
		return createAt;
	}

	public void setCreateAt(Date createAt) {
		this.createAt = createAt;
	}

	@PrePersist
	public void prePersist() {
		createAt = new Date();
	}

	private static final long serialVersionUID = 1L;

}
