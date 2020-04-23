package com.movements.app.models.pks;

import java.io.Serializable;

import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.movements.app.models.entity.AppUser;
import com.movements.app.models.entity.Role;

@Embeddable
public class UserRolePK implements Serializable {

	@ManyToOne
	@JoinColumn(name = "role_fk")
	private Role role;

	@ManyToOne
	@JoinColumn(name = "user_fk")
	private AppUser user;

	public UserRolePK() {
	}

	public UserRolePK(Role role, AppUser user) {
		this.role = role;
		this.user = user;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	public AppUser getUser() {
		return user;
	}

	public void setUser(AppUser user) {
		this.user = user;
	}

	private static final long serialVersionUID = 1L;

}
