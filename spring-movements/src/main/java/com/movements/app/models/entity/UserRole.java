package com.movements.app.models.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
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
	private UserRolePK id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="role_fk")
    @MapsId("roleId")
    private Role role;
 
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="user_fk")
    @MapsId("userId")
    private AppUser user;
	
	@Column(name = "create_at")
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "dd/MM/yyyy hh:mm:ss")
	private Date createAt;

	public UserRole() {
		
	}
	
	public UserRole(AppUser user, Role role) {
		this.user = user;
		this.role = role;
		this.id = new UserRolePK(user.getUsername(), role.getId());
		
	}
		
	
	public UserRolePK getId() {
		return id;
	}

	public void setId(UserRolePK id) {
		this.id = id;
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

	public Date getCreateAt() {
		return createAt;
	}

	public void setCreateAt(Date createAt) {
		this.createAt = createAt;
	}

	public String getDescription() {
		return this.role.getDescription();
	}
	
	@PrePersist
	public void prePersist() {
		createAt = new Date();
	}
	
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
 
        if (o == null || getClass() != o.getClass())
            return false;
 
        UserRole that = (UserRole) o;
        return Objects.equals(user, that.user) &&
               Objects.equals(role, that.role);
    }
 
    @Override
    public int hashCode() {
        return Objects.hash(user, role);
    }
	

	private static final long serialVersionUID = 1L;

}
