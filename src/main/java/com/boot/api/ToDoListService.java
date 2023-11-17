package com.boot.api;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Optional;

@Service
public class ToDoListService {
    private final TodoRepository todoRepository;

    public ToDoListService(TodoRepository todoRepository) {
        this.todoRepository = todoRepository;
    }

    public ResponseEntity<Todo> saveToDoItem(@RequestBody Todo todo) {
        Todo newTodoTask = todoRepository.save(todo);
        return ResponseEntity.ok(newTodoTask);
    }

    public ResponseEntity<List<Todo>> FetchAllToDoItems() {
        return ResponseEntity.ok(todoRepository.findAll());
    }

    // Get a product by ID
    public ResponseEntity<Optional<Todo>> fetchTodoItemById(Long id) {
        Optional<Todo> todo
                = todoRepository.findById(id);
        if (todo.isPresent()) {
            return ResponseEntity.ok(todo);
        }
        else {
            return ResponseEntity.notFound().build();
        }
    }

    public ResponseEntity<Todo> updateTodoItem(Long id, Todo updatedTodo) {
        if(id == null) {
            throw new IllegalArgumentException("ID Error: ID must be not Null");
        }

        Todo existingTodo = todoRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(String.valueOf(id)));

        existingTodo.setName(updatedTodo.getName());

        existingTodo.setDescription(updatedTodo.getDescription());

        Todo savedTodoItemEntity = todoRepository.save(existingTodo);

        return ResponseEntity.ok(savedTodoItemEntity);
    }

    public ResponseEntity<String> deleteTodoItem(Long id)
    {
        todoRepository.deleteById(id);
        return ResponseEntity.ok(
                "Todo Item has been successfully deleted");
    }
}
