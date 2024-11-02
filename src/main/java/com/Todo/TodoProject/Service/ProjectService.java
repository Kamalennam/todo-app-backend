package com.Todo.TodoProject.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Todo.TodoProject.DTO.ProjectDTO;
import com.Todo.TodoProject.DTO.TodoDTO;
import com.Todo.TodoProject.Entity.ProjectEntity;
import com.Todo.TodoProject.Entity.TodoEntity;
import com.Todo.TodoProject.Repository.ProjectRepo;

@Service
public class ProjectService {
	 
	@Autowired
	private ProjectRepo projectRepo;
	
	private static final Logger logger = Logger.getLogger(ProjectService.class.getName());
	
	public ProjectDTO createProject(ProjectDTO projectDTO) {
		Optional<ProjectEntity> existingProject = projectRepo.findByTitle(projectDTO.getTitle());
		if(existingProject.isPresent()) {
			logger.info("Project already availlable with " + existingProject);
			throw new RuntimeException("Already Project available with this title: " + projectDTO.getTitle());
		}
		ProjectEntity projectEntity = new ProjectEntity();
		projectEntity.setTitle(projectDTO.getTitle());
		projectEntity.setCreatedDate(LocalDateTime.now());
		if(projectDTO.getTodos()!=null) {
			for(TodoDTO todoDTO : projectDTO.getTodos()) {
				TodoEntity todoEntity = new TodoEntity();
				todoEntity.setDescription(todoDTO.getDescription());
				todoEntity.setStatus(todoDTO.getStatus());
				todoEntity.setCreatedDate(LocalDateTime.now());
				todoEntity.setUpdatedDate(LocalDateTime.now());
				todoEntity.setProject(projectEntity);
				if (projectEntity.getTodos() == null) {
				    projectEntity.setTodos(new ArrayList<>());
				}
				projectEntity.getTodos().add(todoEntity);

				
			}
		}
		
		ProjectEntity savedProject = projectRepo.save(projectEntity);
		logger.info("Creating project with title: " + projectEntity.getTitle());
		return convertToDTO(projectEntity);
	}
	
	private ProjectDTO convertToDTO(ProjectEntity projectEntity) {
	    ProjectDTO projectDTO = new ProjectDTO();
	    projectDTO.setId(projectEntity.getId());
	    projectDTO.setTitle(projectEntity.getTitle());
	    projectDTO.setCreateDate(projectEntity.getCreatedDate());

	    if (projectEntity.getTodos() != null) {
	        List<TodoDTO> todoDTOList = new ArrayList<>();
	        for (TodoEntity todoEntity : projectEntity.getTodos()) {
	            TodoDTO todoDTO = new TodoDTO();
	            todoDTO.setId(todoEntity.getId());
	            todoDTO.setDescription(todoEntity.getDescription());
	            todoDTO.setStatus(todoEntity.getStatus());
	            todoDTO.setCreateDate(todoEntity.getCreatedDate());
	            todoDTO.setUpdateDate(todoEntity.getUpdatedDate());
	            todoDTOList.add(todoDTO);
	        }
	        projectDTO.setTodos(todoDTOList);
	    }

	    return projectDTO;
	}


	public List<ProjectEntity> findAllProjects(){
		return projectRepo.findAll();
	}
	
//	public ProjectEntity findProjectById(Long id) {
//		return projectRepo.findById(id).orElseThrow(()->new RuntimeException("Project is not available with this id: " + id));
//	}
	
	public void deleteProject(Long id) {
		if(!projectRepo.existsById(id)) {
			logger.info("Project not available with this id: " + id);
			throw new RuntimeException("Project is not available with this id: " + id);
		}
		logger.info("Deleting Project with id: " + id);
		projectRepo.deleteById(id);
		logger.info("Successfully deleted with id: " + id);
	}
	
	
	public Optional<ProjectEntity> findProjectById(Long projectId) {
	    return projectRepo.findById(projectId);
	}
	
//	public ProjectEntity updateProject(Long id, ProjectDTO project) {
//	    return projectRepo.findById(id).map(existingProject -> {
//	        existingProject.setTitle(project.getTitle());
////	        existingProject.setDescription(updatedProject.getDescription());
//	        existingProject.setTodos(project.getTodos());
//	        // Set other fields as necessary
//	        logger.info("Updating project with id: " + id);
//	        return projectRepo.save(existingProject);
//	    }).orElseThrow(() -> new RuntimeException("Project not found with id: " + id));
//	}

	public ProjectDTO partialUpdateProject(Long id, ProjectDTO projectDTO) {
	    // Fetch the existing project from the database
	    ProjectEntity existingProject = projectRepo.findById(id)
	            .orElseThrow(() -> new RuntimeException("Project not found with id: " + id));
	    
	    // Update only non-null fields
	    if (projectDTO.getTitle() != null) {
	        existingProject.setTitle(projectDTO.getTitle());
	    }
//	    if (projectDTO.getDescription() != null) {
//	        existingProject.setDescription(projectDTO.getDescription());
//	    }
//	    if (projectDTO.getStatus() != null) {
//	        existingProject.setStatus(projectDTO.getStatus());
//	    }
	    // Add more fields as needed...

	    // Save the updated project entity
	    ProjectEntity updatedProject = projectRepo.save(existingProject);
	    
	    // Convert the updated entity to DTO and return
	    return convertToPatchDTO(updatedProject);
	}

	public ProjectDTO convertToPatchDTO(ProjectEntity projectEntity) {
	    ProjectDTO projectDTO = new ProjectDTO();
//	    projectDTO.setId(projectEntity.getId());
	    projectDTO.setTitle(projectEntity.getTitle());
//	    projectDTO.setCreateDate(projectEntity.getCreatedDate());
	    
	    return projectDTO;
	}


}
