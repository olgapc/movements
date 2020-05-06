package com.movements.app.models.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Convert;
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
import javax.validation.constraints.Size;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.NaturalId;
import org.hibernate.annotations.NaturalIdCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.movements.app.UserRoleConverter;
import com.movements.app.models.pks.UserRolePK;
import com.movements.app.models.service.IUserService;

@Entity
@Table(name = "users")
@NaturalIdCache
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class AppUser implements Serializable {
//https://vladmihalcea.com/the-best-way-to-map-a-many-to-many-association-with-extra-columns-when-using-jpa-and-hibernate/
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NaturalId
	@NotEmpty
	@Size(min = 4, max = 60)
	@Column(/* length = 30, */ unique = true)
	private String username;

	@NotEmpty
	@Size(min = 4, max = 60)
	@Column(/* length = 60 */)
	private String password;

	private Boolean enabled;

	private Boolean tokenExpired;

	@Column(name = "create_at")
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "dd/MM/yyyy hh:mm:ss")
	private Date createAt;

	@Convert(converter = UserRoleConverter.class)
	@JsonIgnoreProperties({ "user", "hibernateLazyInitializer" })
	@OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
	private List<UserRole> roles = new ArrayList<>();

	public AppUser() {
		
	}

	public AppUser(String username) {
		this.username = username;
	}
	
	public String getId() {
		return username;
	}

	public void setId(String id) {
		this.username = id;
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

	public Boolean getTokenExpired() {
		return tokenExpired;
	}

	public void setTokenExpired(Boolean tokenExpired) {
		this.tokenExpired = tokenExpired;
	}

	public List<UserRole> getRoles() {
		System.out.println("getroles");
		return roles;
	}

	public void setRoles(List<UserRole> roles) {
		System.out.println("setroles");
		this.roles = roles;
	}

	public void addRole(Role role) {
		System.out.println("addroles");
		UserRole userRole = new UserRole(this, role);
		roles.add(userRole);
	}
	
	public void removeRole(Role role) {
		for (Iterator<UserRole> iterator = roles.iterator();
				iterator.hasNext();) {
			UserRole userRole = iterator.next();
			
			if(userRole.getRole().equals(role) && userRole.getUser().equals(this)) {
				iterator.remove();
				userRole.setRole(null);
				userRole.setUser(null);
			}
		}
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
	
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AppUser user = (AppUser) o;
        return Objects.equals(username, user.username);
    }
 
    @Override
    public int hashCode() {
        return Objects.hash(username);
    }

	private static final long serialVersionUID = 1L;

}
