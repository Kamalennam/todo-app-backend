package com.Todo.TodoProject.Controller;

import java.time.LocalDateTime;
import java.util.List;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.Todo.TodoProject.DTO.ProjectDTO;
import com.Todo.TodoProject.DTO.TodoDTO;
import com.Todo.TodoProject.Entity.ProjectEntity;
import com.Todo.TodoProject.Entity.TodoEntity;
import com.Todo.TodoProject.Repository.TodoRepo;
import com.Todo.TodoProject.Service.ProjectService;
import com.Todo.TodoProject.Service.TodoService;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
public class TodoController {

	@Autowired
	private TodoService todoService;
	
	@Autowired
	private ProjectService projectService;
	
	@Autowired
	private TodoRepo todoRepo;
	
	 private static final Logger logger = Logger.getLogger(TodoController.class.getName());
	 
	 
	 
	 @PostMapping("/createTask/{projectId}")
	public ResponseEntity<TodoEntity> createTask(@PathVariable Long projectId, @RequestBody TodoEntity todoEntity) {
		logger.info("Created Task details: " + todoEntity);
		ProjectEntity project = projectService.findProjectById(projectId)
				.orElseThrow(()-> new RuntimeException("Project not found with projectId: " + projectId));
		todoEntity.setProject(project);
		todoEntity.setCreatedDate(LocalDateTime.now());
		todoEntity.setUpdatedDate(LocalDateTime.now());
		TodoEntity todo = todoService.createToDo(todoEntity);
		
		return new ResponseEntity<>(todo, HttpStatus.CREATED);
	}
	
	 
	 @GetMapping("/allTasks")
	 public List<TodoEntity> findAllProjects(){
		 return todoService.getAllTasks();
	 }
	 
	 @DeleteMapping("/deleteTask/{id}") 
	 public ResponseEntity<String> deleteProject(@PathVariable Long id){
		 todoService.deleteTask(id);
		 logger.info("Successully Task deleted with id: "+ id);
		 return new ResponseEntity<>("Task deleted successfully..!! with id: " + id,HttpStatus.OK);
	 }
	 
	 @PatchMapping("/updateTask/{id}")
	 public ResponseEntity<TodoDTO> updateTask(@PathVariable Long id, @RequestBody TodoDTO todo) {
	     TodoDTO updatedTask = todoService.partialUpdateTask(id, todo);
	     logger.info("Task details updated successfully with id: " + id);
	     logger.info("Task description: " + updatedTask.getDescription());
	     logger.info("Task status: " + updatedTask.getStatus());
	     return new ResponseEntity<>(updatedTask, HttpStatus.OK);
	 }

}
