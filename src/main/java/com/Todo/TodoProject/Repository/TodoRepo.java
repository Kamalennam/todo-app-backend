package com.Todo.TodoProject.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.Todo.TodoProject.Entity.ProjectEntity;
import com.Todo.TodoProject.Entity.TodoEntity;

@Repository
public interface TodoRepo extends JpaRepository<TodoEntity, Long>{
	Optional<TodoEntity> findByDescription(String description);
	Optional<TodoEntity> findById(Long id);

}
