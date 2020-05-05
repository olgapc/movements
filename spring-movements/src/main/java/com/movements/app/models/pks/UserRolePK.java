package com.movements.app.models.pks;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;


@Embeddable
public class UserRolePK implements Serializable {

	
	@Column(name = "user_fk")
	private String userId;

	@Column(name = "role_fk")
	private Long roleId;


	public UserRolePK() {
	}

	public UserRolePK(String userId, Long roleId) {
		this.roleId = roleId;
		this.userId = userId;
	}

	
	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public Long getRoleId() {
		return roleId;
	}

	public void setRoleId(Long roleId) {
		this.roleId = roleId;
	}

	@Override
	public int hashCode() {
		
		return Objects.hash(userId,roleId);
	}

	@Override
	public boolean equals(Object obj) {
		
		if(this==obj) {
			return true;
		}
		if (obj == null || getClass() != obj.getClass()) {
			return false;
		}
		
		UserRolePK that = (UserRolePK) obj;
		return Objects.equals(userId, that.userId) && Objects.equals(roleId, that.roleId);
		
	}



	private static final long serialVersionUID = 1L;

}
