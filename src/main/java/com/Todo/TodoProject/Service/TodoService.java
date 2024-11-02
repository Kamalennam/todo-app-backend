package com.Todo.TodoProject.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.logging.Logger;

import com.Todo.TodoProject.DTO.ProjectDTO;
import com.Todo.TodoProject.DTO.TodoDTO;
import com.Todo.TodoProject.Entity.ProjectEntity;
import com.Todo.TodoProject.Entity.TodoEntity;
import com.Todo.TodoProject.Repository.TodoRepo;

@Service
public class TodoService {

	@Autowired
	private TodoRepo todoRepo;
	
	private static final Logger logger = Logger.getLogger(TodoService.class.getName());
	
	public TodoEntity createToDo(TodoEntity todo){
		logger.info("creting todo: " + todo);
		return todoRepo.save(todo);
		}
	
	public List<TodoEntity> getAllTasks(){
		return todoRepo.findAll();
	}
	
	public TodoEntity findById(Long id) {
		return todoRepo.findById(id).orElseThrow(()-> new RuntimeException("No task found with this id..!!"));
	}
	
	public void deleteTask(Long id) {
		if(!todoRepo.existsById(id)) {
			logger.info("No todo found with this id: "+ id);
			throw new RuntimeException("No todo found with id: " + id);
		}
			todoRepo.deleteById(id);
			logger.info("Todo is deleted successfully with id: " + id);
	}
	
	public TodoDTO partialUpdateTask(Long id, TodoDTO todoDTO) {
	    // Fetch the existing task from the database
	    TodoEntity existingTask = todoRepo.findById(id)
	            .orElseThrow(() -> new RuntimeException("Task not found with id: " + id));
	    
	   
	    if (todoDTO.getDescription() != null) {
	        existingTask.setDescription(todoDTO.getDescription());
	    }
	    if (todoDTO.getStatus() != null) {
	        existingTask.setStatus(todoDTO.getStatus());
	    }
	    existingTask.setUpdatedDate(LocalDateTime.now());

	    TodoEntity updatedTask = todoRepo.save(existingTask);
	    
	    return convertToPatchDTO(updatedTask);
	}

	public TodoDTO convertToPatchDTO(TodoEntity todoEntity) {
	    TodoDTO todoDTO = new TodoDTO();
//	    todoDTO.setId(todoEntity.getId());
	    todoDTO.setDescription(todoEntity.getDescription());
	    todoDTO.setStatus(todoEntity.getStatus());
	    
	    return todoDTO;
	}


	
}
   
	
	
