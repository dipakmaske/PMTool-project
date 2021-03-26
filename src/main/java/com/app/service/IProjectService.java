package com.app.service;

import com.app.pojos.Project;

public interface IProjectService {
	public Project saveOrUpdateProject(Project project, String username);
	public Project getProjectByIdentifier(String projectId, String username);
	public Iterable<Project> findAllProjects(String username);
	public void deleteProjectByIdentifier(String projectid, String username);
	

}
