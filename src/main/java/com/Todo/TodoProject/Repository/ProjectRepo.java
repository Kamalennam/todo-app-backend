package com.Todo.TodoProject.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.Todo.TodoProject.Entity.ProjectEntity;

@Repository
public interface ProjectRepo extends JpaRepository<ProjectEntity, Long> {

	Optional<ProjectEntity> findByTitle(String title);

}
