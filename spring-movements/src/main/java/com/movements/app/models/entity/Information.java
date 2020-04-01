package com.movements.app.models.entity;

import java.io.Serializable;
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
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "informations")
public class Information implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotEmpty
	@Size(min = 2, max = 250)
	private String description;

	@Column(name = "create_at")
	@Temporal(TemporalType.TIMESTAMP)
	private Date createAt;

	//@JsonIgnoreProperties(value = {"information"})
	//@OneToMany(mappedBy="information", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	//private List<TaskInformation> taskInformations;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Date getCreateAt() {
		return createAt;
	}

	public void setCreateAt(Date createAt) {
		this.createAt = createAt;
	}

	/*
	 * public List<TaskInformation> getTaskInformations() { return taskInformations;
	 * }
	 * 
	 * public void setTaskInformations(List<TaskInformation> taskInformations) {
	 * this.taskInformations = taskInformations; }
	 * 
	 * public void addTaskInformation(TaskInformation taskInformation) {
	 * taskInformations.add(taskInformation); }
	 */


	@PrePersist
	public void prePersist() {
		createAt = new Date();
	}

	@Override
	public String toString() {
		return description.toUpperCase();
	}
	
	private static final long serialVersionUID = 1L;
}
