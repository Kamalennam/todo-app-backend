package com.Todo.TodoProject.Controller;

import java.time.LocalDateTime;
import java.util.List;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.Todo.TodoProject.DTO.ProjectDTO;
import com.Todo.TodoProject.Entity.ProjectEntity;
import com.Todo.TodoProject.Entity.TodoEntity;
import com.Todo.TodoProject.Service.ProjectService;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
public class ProjectController {

	@Autowired
	private ProjectService projectService;
	
	 private static final Logger logger = Logger.getLogger(ProjectController.class.getName());
	 
	 @Configuration
	    public class CorsConfig implements WebMvcConfigurer {
		    @Override
	            public void addCorsMappings(CorsRegistry registry) {
	                registry.addMapping("/**")
	                        .allowedOrigins("http://localhost:3000")
	                        .allowedMethods("GET", "POST", "PUT", "DELETE", "PATCH")
	                        .allowedHeaders("*")
	                        .allowCredentials(true);
	            }
	        };
	    
	

	 @PostMapping("/createProject")
	 public ResponseEntity<ProjectDTO> createProject(@RequestBody ProjectDTO projectDTO){
		 logger.info("Project details are: " + projectDTO);
//		 projectDTO.setCreatedDate(LocalDateTime.now());
//		 
//		 if (projectEntity.getTodos() != null) {
//		        for (TodoEntity todo : projectEntity.getTodos()) {
//		            todo.setCreatedDate(LocalDateTime.now());
//		            todo.setUpdatedDate(LocalDateTime.now());
//		            todo.setProject(projectEntity); 
//		        }
//		    }
		 ProjectDTO project = projectService.createProject(projectDTO);
		 
		 logger.info("The project details are: " + project);
		 return new ResponseEntity<>(project,HttpStatus.CREATED);
	 }
	 
	 @GetMapping("/allProjects")
	 public List<ProjectEntity> findAllProjects(){
		 List<ProjectEntity> projects = projectService.findAllProjects();
		   logger.info("Projects retrieved: " + projects.size());
		    return projects;
	 }
	 
	 @DeleteMapping("/deleteProject/{id}") 
	 public ResponseEntity<String> deleteProject(@PathVariable Long id){
		 projectService.deleteProject(id);
		 logger.info("Successully project deleted with id: "+ id);
		 return new ResponseEntity<>("Project deleted successfully..!! with id: " + id,HttpStatus.OK);
	 }
	 
	 @PatchMapping("/updateProject/{id}")
	 public ResponseEntity<ProjectDTO> updateProject(@PathVariable Long id, @RequestBody ProjectDTO project) {
		 ProjectDTO updatedProject = projectService.partialUpdateProject(id, project);
		 updatedProject.setTitle(project.getTitle());
		 logger.info("Project details are updateed successfully with id: " + id);
		 logger.info("project title: " + updatedProject.getTitle());
	     return new ResponseEntity<>(updatedProject, HttpStatus.OK);
	 }

}
