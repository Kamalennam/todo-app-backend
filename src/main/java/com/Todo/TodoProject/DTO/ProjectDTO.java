package com.Todo.TodoProject.DTO;

import java.time.LocalDateTime;
import java.util.List;

import com.Todo.TodoProject.Entity.TodoEntity;

public class ProjectDTO {

	private Long id;
	private String title;
	private LocalDateTime createDate;
	private List<TodoDTO> todos;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public LocalDateTime getCreateDate() {
		return createDate;
	}
	public void setCreateDate(LocalDateTime createDate) {
		this.createDate = createDate;
	}
	public List<TodoDTO> getTodos() {
		return todos;
	}
	public void setTodos(List<TodoDTO> todos) {
		this.todos = todos;
	}
}
