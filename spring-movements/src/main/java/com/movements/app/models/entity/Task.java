package com.movements.app.models.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
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
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.movements.app.models.enums.TypeCalculationDeadline;

@Entity
@Table(name = "tasks")
public class Task implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotEmpty
	private String description;

	@Column(name = "optional_subtask")
	private boolean optionalSubtask;

	@Column(name = "to_send")
	private boolean toSend;

	@Column(name = "template")
	private boolean template;

	@Column(name = "name_template")
	private String nameTemplate;

	@Column(name = "number_to_calculate_deadline_to_alarm")
	private String numberToCalculateDeadlineToAlarm;

	@Column(name = "days_to_fix_error")
	private Integer daysToFixError;

	@Enumerated(value = EnumType.STRING)
	@Column(name = "type_calculation_deadline")
	private TypeCalculationDeadline typeCalculationDeadline;

	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	@NotNull
	private Date deadline;

	@Column(name = "create_at")
	@Temporal(TemporalType.TIMESTAMP)
	private Date createAt;

	//@JsonIgnoreProperties({ "tasks", "hibernateLazyInitializer" })
	@ManyToOne(optional = true, fetch = FetchType.LAZY)
	@JoinColumn(name = "company_fk")
	private Company company;

	//@JsonIgnoreProperties({ "tasks", "hibernateLazyInitializer" })
	@ManyToOne(optional = true, fetch = FetchType.LAZY)
	@JoinColumn(name = "employee_fk")
	private Employee employee;

	@JsonIgnoreProperties({ "task", "hibernateLazyInitializer" })
	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name="task_fk")
	private List<TaskInformation> taskInformations;

	@Column(name = "done")
	private boolean done;

	@Column(name = "done_at")
	@Temporal(TemporalType.TIMESTAMP)
	private Date doneAt;

	@JsonIgnoreProperties(value = { "subtasks" })
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "maintask_fk")
	private Task mainTask;

	@JsonIgnoreProperties(value = { "mainTask" })
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "mainTask", cascade = CascadeType.ALL)
	private List<Task> subtasks;

	public Task() {
		taskInformations = new ArrayList<TaskInformation>();
	}

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

	public boolean isOptionalSubtask() {
		return optionalSubtask;
	}

	public boolean isToSend() {
		return toSend;
	}

	public boolean isTemplate() {
		return template;
	}

	public String getNameTemplate() {
		return nameTemplate;
	}

	public void setNameTemplate(String nameTemplate) {
		this.nameTemplate = nameTemplate;
	}

	public String getNumberToCalculateDeadlineToAlarm() {
		return numberToCalculateDeadlineToAlarm;
	}

	public void setNumberToCalculateDeadlineToAlarm(String numberToCalculteDeadlineToAlarm) {
		this.numberToCalculateDeadlineToAlarm = numberToCalculteDeadlineToAlarm ;
	}

	public Integer getDaysToFixError() {
		return daysToFixError;
	}

	public void setDaysToFixError(Integer daysToFixError) {
		this.daysToFixError = daysToFixError;
	}

	public TypeCalculationDeadline getTypeCalculationDeadline() {
		return typeCalculationDeadline;
	}

	public void setTypeCalculationDeadline(TypeCalculationDeadline typeCalculationDeadline) {
		this.typeCalculationDeadline = typeCalculationDeadline;
	}

	public Date getDeadline() {
		return deadline;
	}

	public void setDeadline(Date deadline) {
		this.deadline = deadline;
	}

	public Date getCreateAt() {
		return createAt;
	}

	public void setCreateAt(Date createAt) {
		this.createAt = createAt;
	}

	public Company getCompany() {
		return company;
	}

	public void setCompany(Company company) {
		this.company = company;
	}

	public Employee getEmployee() {
		return employee;
	}

	public void setEmployee(Employee employee) {
		this.employee = employee;
	}

	public List<TaskInformation> getTaskInformations() {
		return taskInformations;
	}

	public void setTaskInformations(List<TaskInformation> taskInformations) {
		this.taskInformations = taskInformations;
	}

	public void addTaskInformation(TaskInformation taskInformation) {
		taskInformations.add(taskInformation);
		//taskInformation.setTask(this);
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

	public Task getMainTask() {
		return mainTask;
	}

	public void setMainTask(Task mainTask) {
		this.mainTask = mainTask;
	}

	public void setOptionalSubtask(boolean optionalSubtask) {
		this.optionalSubtask = optionalSubtask;
	}

	public void setToSend(boolean toSend) {
		this.toSend = toSend;
	}

	public void setTemplate(boolean template) {
		this.template = template;
	}

	public List<Task> getSubtasks() {
		return subtasks;
	}

	public void setSubtasks(List<Task> subtasks) {
		this.subtasks = subtasks;
	}

	public void addSubtask(Task subtask) {
		subtasks.add(subtask);
	}

	public boolean isDone() {
		return done;
	}

	public void setDone(boolean done) {
		this.done = done;
	}

	private static final long serialVersionUID = 1L;

}
