package com.app.service;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.dao.BacklogRepository;
import com.app.dao.ProjectRepo;
import com.app.dao.UserRepository;
import com.app.exceptions.NotAuthorisedException;
import com.app.exceptions.ProjectIdException;
import com.app.exceptions.ProjectNotFoundException;
import com.app.pojos.Backlog;
import com.app.pojos.Project;
import com.app.pojos.Role;
import com.app.pojos.User;

@Service
public class ProjectServiceImpl implements IProjectService{

	@Autowired
	private ProjectRepo projectRepository;

	@Autowired
	private BacklogRepository backlogRepository;

	@Autowired
	private UserRepository userRepository;

	public Project saveOrUpdateProject(Project project, String username) {
		if (project.getId() != null) {
			Project existingProject = projectRepository.findByProjectIdentifier(project.getProjectIdentifier());
			if (existingProject != null && (!existingProject.getProjectLeader().equals(username))) {
				throw new ProjectNotFoundException("Project not found in your account");
			} else if (existingProject == null) {
				throw new ProjectNotFoundException("Project with ID: '" + project.getProjectIdentifier()
						+ "' cannot be updated because it doesn't exist");
			}
		}
		User user = userRepository.findByUsername(username);
		if (!user.getRole().equals(Role.MANAGER)) {
			throw new NotAuthorisedException("Only manager can create project");
		}
		Set<User> userlist = project.getMembers();
		project.setMembers(userlist);
		try {
			project.setProjectLeader(user.getUsername());
			project.setProjectIdentifier(project.getProjectIdentifier().toUpperCase());

			if (project.getId() == null) {
				Backlog backlog = new Backlog();
				project.setBacklog(backlog);
				backlog.setProject(project);
				backlog.setProjectIdentifier(project.getProjectIdentifier().toUpperCase());
			}

			if (project.getId() != null) {
				project.setBacklog(
						backlogRepository.findByProjectIdentifier(project.getProjectIdentifier().toUpperCase()));
			}
			return projectRepository.save(project);

		} catch (Exception e) {
			throw new ProjectIdException(
					"Project ID '" + project.getProjectIdentifier().toUpperCase() + "' already exists");
		}

	}

	public Project getProjectByIdentifier(String projectId, String username) {

		Project project = projectRepository.findByProjectIdentifier(projectId.toUpperCase());

		if (project == null) {
			throw new ProjectIdException("Project ID '" + projectId + "' does not exist");

		}
		User user = userRepository.findByUsername(username);
		Set<User> userlist = project.getMembers();
		if (!project.getProjectLeader().equals(username) && !userlist.contains(user)) {
			throw new ProjectNotFoundException("Project not found in your account");
		}

		return project;
	}

	public Iterable<Project> findAllProjects(String username) {
		User user = userRepository.findByUsername(username);
		if (user.getRole().equals(Role.DEVELOPER)) {
			return projectRepository.findAllByMembers(user);
		}

		return projectRepository.findAllByProjectLeader(username);
	}

	public void deleteProjectByIdentifier(String projectid, String username) {
		User user = userRepository.findByUsername(username);
		if (!user.getRole().equals(Role.MANAGER)) {
			throw new NotAuthorisedException("Only manager can delete project");
		}

		projectRepository.delete(getProjectByIdentifier(projectid, username));
	}

}