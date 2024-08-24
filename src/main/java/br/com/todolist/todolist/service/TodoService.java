package br.com.todolist.todolist.service;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import br.com.todolist.todolist.entity.Todo;
import br.com.todolist.todolist.exception.BadRequestException;
import br.com.todolist.todolist.repository.TodoRepository;

@Service
public class TodoService {
    private final TodoRepository todoRepository;
    
    public TodoService(TodoRepository todoRepository) {
        this.todoRepository = todoRepository;
    }

    public List<Todo> create(Todo todo) {
        todoRepository.save(todo);
        return list();
    }

    public List<Todo> list() {
        Sort sort = Sort.by("priority").descending().and(
            Sort.by("name").ascending()
        );

        return todoRepository.findAll(sort);
    }

    public List<Todo> update(Long id, Todo todo) {
        todoRepository.findById(id).ifPresentOrElse((existingToDo) -> {
            todo.setId(id);
            todoRepository.save(todo);
        }, () -> {
            throw new BadRequestException("Todo %d não existe! ".formatted(id));
        });

        return list();
    }

    public List<Todo> delete(Long id) {
        todoRepository.findById(id).ifPresentOrElse((existingTodo) -> {
            todoRepository.delete(existingTodo);
        }, () -> {
            throw new BadRequestException("Todo %d não existe! ".formatted(id));
        });
        
        return list();
    }
}
