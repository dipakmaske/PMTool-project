package com.app.service;

import com.app.pojos.ProjectTask;

public interface IProjectTaskService {
	public ProjectTask addProjectTask(String projectIdentifier, ProjectTask projectTask, String username);

	public Iterable<ProjectTask> findBacklogById(String id, String username);
	

	public ProjectTask getPTByProjectSequence(String backlog_id, String pt_id, String username);
	
	public ProjectTask updateByProjectSequence(ProjectTask updatedTask, String backlog_id, String pt_id,
			String username);
	

	public void deletePTByProjectSequence(String backlog_id, String pt_id, String username);
}
