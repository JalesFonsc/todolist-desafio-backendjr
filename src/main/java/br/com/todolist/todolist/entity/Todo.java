package br.com.todolist.todolist.entity;

import org.apache.commons.lang3.builder.EqualsBuilder;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;

@Entity
@Table(name ="todos")
public class Todo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank
    private String name;
    @NotBlank
    private String description;
    private boolean isDone;
    private int priority;

    public Todo () {

    }
    
    public Todo(Long id, @NotBlank String name, @NotBlank String description, boolean isDone, int priority) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.isDone = isDone;
        this.priority = priority;
    }

    public Todo(String name, String description, boolean isDone, int priority) {
        this.name = name;
        this.description = description;
        this.isDone = isDone;
        this.priority = priority;
    }

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public boolean isDone() {
        return isDone;
    }
    public void setDone(boolean isDone) {
        this.isDone = isDone;
    }
    public int getPriority() {
        return priority;
    }
    public void setPriority(int priority) {
        this.priority = priority;
    }

    @Override
    public boolean equals(Object object) {
        return EqualsBuilder.reflectionEquals(object, this);
    }
}