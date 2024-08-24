package br.com.todolist.todolist.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.todolist.todolist.entity.Todo;

public interface TodoRepository extends JpaRepository<Todo, Long>{
    
}
