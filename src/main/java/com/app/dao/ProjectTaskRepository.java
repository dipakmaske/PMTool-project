package com.app.dao;

import com.app.pojos.ProjectTask;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProjectTaskRepository extends CrudRepository<ProjectTask, Long> {
	
	
	  List<ProjectTask> findByProjectIdentifierOrderByPriority(String id);
	  
	  ProjectTask findByProjectSequence(String sequence);
}

