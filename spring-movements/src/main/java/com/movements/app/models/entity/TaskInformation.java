package com.movements.app.models.entity;

import java.io.Serializable;
import java.util.Date;

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

import javax.validation.constraints.NotNull;

import com.movements.app.models.pks.TaskInformationPK;

@Entity
@Table(name = "task_informations")
public class TaskInformation implements Serializable {

	@EmbeddedId
	private TaskInformationPK taskInformationPK;

	@NotNull
	@Column(name = "create_at")
	@Temporal(TemporalType.TIMESTAMP)
	private Date createAt;

	private String comment;

	/*
	 * @ManyToOne(fetch = FetchType.LAZY)
	 * 
	 * @MapsId("information_id")
	 * 
	 * @JoinColumn(name = "information_fk") private Information information;
	 * 
	 * @ManyToOne(fetch = FetchType.LAZY)
	 * 
	 * @MapsId("task_id")
	 * 
	 * @JoinColumn(name = "task_fk") private Task task;
	 */
	
	
	@Column(name = "done_at")
	@Temporal(TemporalType.TIMESTAMP)
	private Date doneAt;

	@Column(name = "done")
	private boolean done;

	public TaskInformation() {

	}

	public TaskInformation(TaskInformationPK taskInformationPK) {
		this.taskInformationPK = taskInformationPK;
		// taskInformationPK.getTask().addTaskInformation(this);
	}

	public TaskInformation(TaskInformationPK taskInformationPK, String comment, Date doneAt, boolean done) {
		this.taskInformationPK = taskInformationPK;
		this.createAt = new Date();
		this.comment = comment;
		this.doneAt = doneAt;
		this.done = done;
	}


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
		return taskInformationPK.getInformation();
	}

	public void setInformation(Information information) {
		this.taskInformationPK.setInformation(information);
	}

	public Task getTask() {
		return taskInformationPK.getTask();
	}

	public void setTask(Task task) {
		this.taskInformationPK.setTask(task);
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
