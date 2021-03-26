package com.app.pojos;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class ProjectTask {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column(updatable = false, unique = true)
	private String projectSequence;
	@NotBlank(message = "Please include a project summary")
	private String summary;
	private String acceptanceCriteria;
	@Column(length = 20)
	private String status;
	private Integer priority;
	@FutureOrPresent(message = "Past due date is not acceptable")
	private LocalDate dueDate;
	// ManyToOne with Backlog
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "backlog_id", updatable = false, nullable = false)
	@JsonIgnore
	private Backlog backlog;
	private String updatedBy;

	@Column(updatable = false)
	private String projectIdentifier;
	private LocalDate create_At;
	private String update_At;

	public ProjectTask() {
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Backlog getBacklog() {
		return backlog;
	}

	public void setBacklog(Backlog backlog) {
		this.backlog = backlog;
	}

	public String getProjectSequence() {
		return projectSequence;
	}

	public void setProjectSequence(String projectSequence) {
		this.projectSequence = projectSequence;
	}

	public String getSummary() {
		return summary;
	}

	public String getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}

	public String getAcceptanceCriteria() {
		return acceptanceCriteria;
	}

	public void setAcceptanceCriteria(String acceptanceCriteria) {
		this.acceptanceCriteria = acceptanceCriteria;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Integer getPriority() {
		return priority;
	}

	public void setPriority(Integer priority) {
		this.priority = priority;
	}

	public LocalDate getCreate_At() {
		return create_At;
	}

	public void setCreate_At(LocalDate create_At) {
		this.create_At = create_At;
	}



	public String getUpdate_At() {
		return update_At;
	}

	public void setUpdate_At(String update_At) {
		this.update_At = update_At;
	}

	public String getProjectIdentifier() {
		return projectIdentifier;
	}

	public void setProjectIdentifier(String projectIdentifier) {
		this.projectIdentifier = projectIdentifier;
	}

	public LocalDate getDueDate() {
		return dueDate;
	}

	public void setDueDate(LocalDate dueDate) {
		this.dueDate = dueDate;
	}

	@PrePersist
	protected void onCreate() {
		this.create_At = LocalDate.now();
		DateTimeFormatter datetimeFomatter=DateTimeFormatter.ofPattern("MMMM dd,YYYY h:mm a");
		String formatedDate=LocalDateTime.now().format(datetimeFomatter);
		this.update_At = formatedDate;
	}

	@PreUpdate
	protected void onUpdate() {
	DateTimeFormatter datetimeFomatter=DateTimeFormatter.ofPattern("MMMM dd,YYYY h:mm a");
	String formatedDate=LocalDateTime.now().format(datetimeFomatter);
	this.update_At = formatedDate;
	}

	@Override
	public String toString() {
		return "ProjectTask{" + "id=" + id + ", projectSequence='" + projectSequence + '\'' + ", summary='" + summary
				+ '\'' + ", acceptanceCriteria='" + acceptanceCriteria + '\'' + ", status='" + status + '\''
				+ ", priority=" + priority + ", dueDate=" + dueDate + ", projectIdentifier='" + projectIdentifier + '\''
				+ ", create_At=" + create_At + ", update_At=" + update_At + '}';
	}
}
