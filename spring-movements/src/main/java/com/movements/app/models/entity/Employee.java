package com.movements.app.models.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
//import javax.validation.constraints.Pattern;
//import javax.validation.constraints.Size;
import javax.validation.constraints.Past;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.movements.app.models.enums.TypeNif;

@Entity
@Table(name = "employees")
public class Employee implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "employee_name")
	//@NotEmpty
	//@Size(min = 2, max = 50)
	private String name;

	//@Pattern(regexp = "[X-Y]?[0-9]{8}[A-Z]?")
	private String nif;
	
	@Column(name="type_nif")
	private TypeNif typeNif;

	//@Pattern(regexp = "[\\d]{1,2}[-][\\d]{7,8}[-][\\d]{2}")
	private String naf;

	@Email
	private String email;

	private String phone;
	
	@Past
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date birthDate;	
	
	@NotEmpty
	private String gender;
	
	private Boolean enable;
	
	private String comment;
	


	
	@Column(name = "create_at")
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "dd/MM/yyyy hh:mm:ss")
	private Date createAt;

	
	@JsonIgnoreProperties({"employees", "hibernateLazyInitializer"})
	@ManyToOne(fetch = FetchType.LAZY, optional = false//, cascade = CascadeType.ALL
	)
	@JoinColumn(name = "company_fk")
	private Company company;

	
	@OneToMany(mappedBy = "employee", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JsonIgnore
	private List<Task> tasks;

	public Employee() {
		tasks = new ArrayList<Task>();
	}

	public Company getCompany() {
		return company;
	}

	public void setCompany(Company company) {
		this.company = company;
	}

	public List<Task> getTasks() {
		return tasks;
	}

	public void setTasks(List<Task> tasks) {
		this.tasks = tasks;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getNif() {
		return nif;
	}

	public void setNif(String nif) {
		this.nif = nif;
	}

	public TypeNif getTypeNif() {
		return typeNif;
	}

	public void setTypeNif(TypeNif typeNif) {
		this.typeNif = typeNif;
	}
	
	public String getNaf() {
		return naf;
	}

	public void setNaf(String naf) {
		this.naf = naf;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	
	public Date getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(Date birthDate) {
		this.birthDate = birthDate;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}
	
	public Boolean getEnable() {
		return enable;
	}

	public void setEnable(Boolean enable) {
		this.enable = enable;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public Date getCreateAt() {
		return createAt;
	}

	public void setCreateAt(Date createAt) {
		this.createAt = createAt;
	}

	public void addTask(Task task) {
		tasks.add(task);
	}

	@PrePersist
	public void prePersist() {
		createAt = new Date();
	}

	@Override
	public String toString() {
		return name.toUpperCase()+ ". \n NIF: " + nif + ", NAF:" + naf + ", " + email;
	}

	


	private static final long serialVersionUID = 1L;
}
