package com.app.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.dao.BacklogRepository;
import com.app.dao.ProjectTaskRepository;
import com.app.dao.UserRepository;
import com.app.exceptions.ProjectNotFoundException;
import com.app.pojos.Backlog;
import com.app.pojos.ProjectTask;
import com.app.pojos.User;

@Service
public class ProjectTaskServiceImpl implements IProjectTaskService {

	@Autowired
	private BacklogRepository backlogRepository;

	@Autowired
	private ProjectTaskRepository projectTaskRepository;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private IProjectService projectService;

	public ProjectTask addProjectTask(String projectIdentifier, ProjectTask projectTask, String username) {

		try {
			Backlog backlog = projectService.getProjectByIdentifier(projectIdentifier, username).getBacklog();
			
			projectTask.setBacklog(backlog);
			
			Integer BacklogSequence = backlog.getPTSequence();
			// Update the BL SEQUENCE
			BacklogSequence++;
			backlog.setPTSequence(BacklogSequence);

			User user = userRepository.findByUsername(username);
			projectTask.setUpdatedBy(user.getFullName());

			// Add Sequence to Project Task
			projectTask.setProjectSequence(backlog.getProjectIdentifier() + "-" + BacklogSequence);
			projectTask.setProjectIdentifier(projectIdentifier);

			if (projectTask.getStatus() == "" || projectTask.getStatus() == null) {
				projectTask.setStatus("TO_DO");
			}
			if (projectTask.getPriority() == null || projectTask.getPriority() == 0) {
				
				projectTask.setPriority(3);
			}
			return projectTaskRepository.save(projectTask);
		} catch (Exception e) {
			System.out.println(e.getMessage());
			throw new ProjectNotFoundException("Project not Found");
		}

	}

	public Iterable<ProjectTask> findBacklogById(String id, String username) {
		projectService.getProjectByIdentifier(id, username);

		return projectTaskRepository.findByProjectIdentifierOrderByPriority(id);
	}

	public ProjectTask getPTByProjectSequence(String backlog_id, String pt_id, String username) {

		// make sure we are searching on the right backlog
		projectService.getProjectByIdentifier(backlog_id, username);

		// make sure that our task exists
		ProjectTask projectTask = projectTaskRepository.findByProjectSequence(pt_id);

		if (projectTask == null) {
			throw new ProjectNotFoundException("Project Task '" + pt_id + "' not found");
		}

		// make sure that the backlog/project id in the path corresponds to the right
		// project
		System.out.println(projectTask.getProjectIdentifier().equals(backlog_id));
		if (!projectTask.getProjectIdentifier().equals(backlog_id)) {
			throw new ProjectNotFoundException(
					"Project Task '" + pt_id + "' does not exist in project: '" + backlog_id);
		}

		return projectTask;
	}

	public ProjectTask updateByProjectSequence(ProjectTask updatedTask, String backlog_id, String pt_id,
			String username) {
		ProjectTask projectTask = getPTByProjectSequence(backlog_id, pt_id, username);

		projectTask = updatedTask;
		User user = userRepository.findByUsername(username);
		projectTask.setUpdatedBy(user.getFullName());
		return projectTaskRepository.save(projectTask);
	}

	public void deletePTByProjectSequence(String backlog_id, String pt_id, String username) {
		ProjectTask projectTask = getPTByProjectSequence(backlog_id, pt_id, username);

		Backlog backlog = projectTask.getBacklog();
		List<ProjectTask> pts = backlog.getProjectTasks();
		pts.remove(projectTask);
		backlogRepository.save(backlog);
		projectTaskRepository.delete(projectTask);
	}
}
