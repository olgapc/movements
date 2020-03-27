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
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name = "task_informations")
public class TaskInformation implements Serializable {

	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotNull
	@Column(name = "create_at")
	@Temporal(TemporalType.TIMESTAMP)
	private Date createAt;
	
	private String comment;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="information_fk")
	private Information information;
	
	@Column(name = "done_at")
	@Temporal(TemporalType.TIMESTAMP)
	private Date doneAt;
	
	@Column(name = "done")
	private boolean done ;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="task_fk")
	private Task task;

	public Date getCreateAt() {
		return createAt;
	}

	public void setCreateAt(Date createAt) {
		this.createAt = createAt;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public Information getInformation() {
		return information;
	}

	public void setInformation(Information information) {
		this.information = information;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}


	public Task getTask() {
		return task;
	}

	public void setTask(Task task) {
		this.task = task;
	}

	@PrePersist
	public void prePersist() {
		createAt = new Date();
	}

	public Date getDoneAt() {
		return doneAt;
	}

	public void setDoneAt(Date doneAt) {
		this.doneAt = doneAt;
	}

	public boolean isDone() {
		return done;
	}

	public void setDone(boolean done) {
		this.done = done;
	}

	private static final long serialVersionUID = 1L;

}
