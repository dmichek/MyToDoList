package com.example.mytodolist.repositories;

import com.example.mytodolist.models.Person;
import com.example.mytodolist.models.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TasksRepository extends JpaRepository<Task, Integer> {
    List<Task> findByPerson_id(Integer id);
}
