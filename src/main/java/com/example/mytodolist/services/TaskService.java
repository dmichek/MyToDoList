package com.example.mytodolist.services;

import com.example.mytodolist.models.Person;
import com.example.mytodolist.models.Task;
import com.example.mytodolist.repositories.TasksRepository;
import com.example.mytodolist.security.PersonDetails;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class TaskService {

    private TasksRepository tasksRepository;

    private Person person;

    @Autowired
    public TaskService(TasksRepository tasksRepository) {
        this.tasksRepository = tasksRepository;
    }

    @Transactional
    public void create(Task task){
        task.setPerson(getPerson());
        task.setCreateAt(LocalDate.now());
        tasksRepository.save(task);
    }

    public List<Task> getAllTasks(){
        return tasksRepository.findByPerson_id(getPerson().getId());
    }

    public Task getTaskById(int id) {
        Optional<Task> foundPerson = tasksRepository.findById(id);
        return foundPerson.orElse(null);
    }

    @Transactional
    public void update(int id, Task task){
        task.setPerson(getPerson());
        task.setId(id);
        tasksRepository.save(task);
    }

    @Transactional
    public void delete(int id){
        tasksRepository.deleteById(id);
    }

    private Person getPerson(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        PersonDetails personDetails = (PersonDetails) authentication.getPrincipal();
        return personDetails.getPerson();
    }

}
