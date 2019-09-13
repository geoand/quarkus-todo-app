package io.quarkus.sample;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface TodoRepository extends JpaRepository<Todo, Long> {

    @Modifying
    @Query("update Todo set completed = ?2 where id = ?1")
    void setUpdated(Long id, boolean completed);

    long deleteByCompletedTrue();
}
