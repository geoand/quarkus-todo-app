package io.quarkus.sample;

import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/api")
public class TodoController {

    final TodoRepository todoRepository;

    public TodoController(TodoRepository todoRepository) {
        this.todoRepository = todoRepository;
    }

    @RequestMapping(method = RequestMethod.OPTIONS)
    public ResponseEntity<Void> opt() {
        return ResponseEntity.ok().build();
    }

    @GetMapping
    public List<Todo> getAll() {
        return todoRepository.findAll(Sort.by("order"));
    }

    @GetMapping("/{id}")
    public Todo getOne(@PathVariable("id") Long id) {
        return findById(id);
    }

    @PostMapping
    @Transactional
    public ResponseEntity<Todo> create(@RequestBody @Valid Todo item) {
        todoRepository.save(item);
        return ResponseEntity.status(HttpStatus.CREATED).body(item);
    }

    @PatchMapping("/{id}")
//    @Transactional
    public ResponseEntity<Todo>  update(@RequestBody @Valid Todo todo, @PathVariable("id") Long id) {
        if (todoRepository.existsById(id)) {
            todoRepository.setUpdated(id, todo.completed);
            return ResponseEntity.ok(todo);
        }
        throw new ApplicationException("Todo with id of " + id + " does not exist.");
    }

    @DeleteMapping
    @Transactional
    public ResponseEntity<Void> deleteCompleted() {
        todoRepository.deleteByCompletedTrue();
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<Void> deleteOne(@PathVariable("id") Long id) {
        Todo entity = findById(id);
        todoRepository.delete(entity);
        return ResponseEntity.noContent().build();
    }

    private Todo findById(Long id) {
        Optional<Todo> optional = todoRepository.findById(id);
        if (!optional.isPresent()) {
            throw new ApplicationException("Todo with id of " + id + " does not exist.");
        }
        return optional.get();
    }

}
