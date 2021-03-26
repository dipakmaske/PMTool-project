package com.app;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.app.controller.BacklogController;
import com.app.controller.ProjectController;
import com.app.controller.UserController;

@SpringBootTest
class PmToolApplicationTests {
	
	@Autowired
	private UserController userController;
	
	@Autowired
	private ProjectController projectController;
	
	@Autowired
	private BacklogController backlogController;

	@Test
	void contextLoads() {
		System.out.println("in test");
		assertNotNull(userController);
		assertNotNull(projectController);
		assertNotNull(backlogController);
	}

}
