package com.app.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.app.pojos.Project;
import com.app.pojos.User;

@Repository
public interface ProjectRepo extends CrudRepository<Project, Integer> {

	Project findByProjectIdentifier(String projectId);
	//Project findByMembers(User user);

	@Override
	Iterable<Project> findAll();

	Iterable<Project> findAllByProjectLeader(String username);

	Iterable<Project> findAllByMembers(User user);
}
