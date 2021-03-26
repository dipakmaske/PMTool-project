package com.app.pojos;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.validation.constraints.Future;
import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Project {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	@NotBlank(message = "project name is required")
	private String projectName;
	@NotBlank(message = "project Identifier is Required")
	@Size(min = 4, max = 5, message = "please use 4 to 5 character")
	@Column(updatable = false, unique = true)
	private String projectIdentifier;
	@NotBlank(message = "Description is required")
	private String description;
	@FutureOrPresent(message = "Past date is not acceptable")
	private LocalDate start_date;
	@Future(message = "End date should be in future")
	private LocalDate end_date;
	@Column(updatable = false)
	private String created_At;
	private LocalDateTime updated_At;

	@OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL, mappedBy = "project")
	@JsonIgnore
	private Backlog backlog;

	@ManyToMany(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
	@JoinTable(name = "project_users", joinColumns = @JoinColumn(name = "project_id"), inverseJoinColumns = @JoinColumn(name = "user_id"))
	private Set<User> members = new HashSet<User>();

	private String projectLeader;

	public Project() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Project(Integer id, String projectName, String projectIdentifier, String description, LocalDate start_date,
			LocalDate end_date, String created_At, LocalDateTime updated_At) {
		super();
		this.id = id;
		this.projectName = projectName;
		this.projectIdentifier = projectIdentifier;
		this.description = description;
		this.start_date = start_date;
		this.end_date = end_date;
		this.created_At = created_At;
		this.updated_At = updated_At;
	}

	public Integer getId() {
		return id;
	}

	public Backlog getBacklog() {
		return backlog;
	}

	public Set<User> getMembers() {
		return members;
	}

	public void setMembers(Set<User> members) {
		this.members = members;
	}

	public String getProjectLeader() {
		return projectLeader;
	}

	public String getCreated_At() {
		return created_At;
	}

	public void setCreated_At(String created_At) {
		this.created_At = created_At;
	}

	public void setProjectLeader(String projectLeader) {
		this.projectLeader = projectLeader;
	}

	public void setBacklog(Backlog backlog) {
		this.backlog = backlog;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public String getProjectIdentifier() {
		return projectIdentifier;
	}

	public void setProjectIdentifier(String projectIdentifier) {
		this.projectIdentifier = projectIdentifier;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public LocalDate getStart_date() {
		return start_date;
	}

	public void setStart_date(LocalDate start_date) {
		this.start_date = start_date;
	}

	public LocalDate getEnd_date() {
		return end_date;
	}

	public void setEnd_date(LocalDate end_date) {
		this.end_date = end_date;
	}


	public LocalDateTime getUpdated_At() {
		return updated_At;
	}

	public void setUpdated_At(LocalDateTime updated_At) {
		this.updated_At = updated_At;
	}

	@PrePersist
	protected void onCreate() {
		DateTimeFormatter datetimeFomatter=DateTimeFormatter.ofPattern("MMMM dd,YYYY h:mm a");
		String formatedDate=LocalDateTime.now().format(datetimeFomatter);
		this.created_At = formatedDate;
		System.out.println(created_At);
		this.updated_At = LocalDateTime.now();
	}

	@PreUpdate
	protected void onUpdate() {
		this.updated_At = LocalDateTime.now();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((projectIdentifier == null) ? 0 : projectIdentifier.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Project other = (Project) obj;
		if (projectIdentifier == null) {
			if (other.projectIdentifier != null)
				return false;
		} else if (!projectIdentifier.equals(other.projectIdentifier))
			return false;
		return true;
	}

}
