package com.boot.api;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Service
public class ToDoListService {
    private final TodoRepository todoRepository;

    public ToDoListService(TodoRepository todoRepository) {
        this.todoRepository = todoRepository;
    }

    @PostMapping("/todo")
    public ResponseEntity<Todo> saveToDoItem(@RequestBody Todo todo) {
        Todo newTodoTask = todoRepository.save(todo);
        return ResponseEntity.ok(newTodoTask);
    }

    @GetMapping("todos")
    public ResponseEntity<List<Todo>> FetchAllToDoItems() {
        return ResponseEntity.ok(todoRepository.findAll());
    }

    @GetMapping("/todos/{id}")
    public ResponseEntity<Optional<Todo>> fetchTodoItemById(@PathVariable Long id) {
        Optional<Todo> todo
                = todoRepository.findById(id);
        if (todo.isPresent()) {
            return ResponseEntity.ok(todo);
        }
        else {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping(path = "/todos/{todoId}")
    public ResponseEntity<Todo> updateTodoItem( @PathVariable(value = "todoId") Long id, Todo updatedTodo) {
        if(id == null) {
            throw new IllegalArgumentException("ID Error: ID must be not Null");
        }

        Todo existingTodo = todoRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(String.valueOf(id)));

        existingTodo.setName(updatedTodo.getName());

        existingTodo.setDescription(updatedTodo.getDescription());

        Todo savedTodoItemEntity = todoRepository.save(existingTodo);

        return ResponseEntity.ok(savedTodoItemEntity);
    }

    @DeleteMapping(value = "/todos/{id}")
    public ResponseEntity<String> deleteTodoItem(@PathVariable Long id) {
        todoRepository.deleteById(id);
        return ResponseEntity.ok(
                "Todo Item ID " + id + " has been successfully deleted ");
    }
}
