package com.movements.app.models.pks;

import java.io.Serializable;

import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.movements.app.models.entity.Task;
import com.movements.app.models.entity.Information;;

@Embeddable
public class TaskInformationPK implements Serializable {

	@ManyToOne
	@JoinColumn(name = "task_fk")
	private Task task;

	@ManyToOne
	@JoinColumn(name = "information_fk")
	private Information information;

	public TaskInformationPK() {

	}
	
	public TaskInformationPK(Task task, Information information) {
		this.task = task;
		this.information = information;
	}

	public Task getTask() {
		return task;
	}

	public void setTask(Task task) {
		this.task = task;
	}

	public Information getInformation() {
		return information;
	}

	public void setInformation(Information information) {
		this.information = information;
	}

	private static final long serialVersionUID = 1L;

}
