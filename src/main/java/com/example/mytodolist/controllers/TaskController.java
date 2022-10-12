package com.example.mytodolist.controllers;

import com.example.mytodolist.models.Person;
import com.example.mytodolist.models.Task;
import com.example.mytodolist.services.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import com.example.mytodolist.security.PersonDetails;
import com.example.mytodolist.services.AdminService;

import javax.validation.Valid;

@Controller
@RequestMapping("/tasks")
public class TaskController {
    private final AdminService adminService;
    private final TaskService taskService;

    @Autowired
    public TaskController(AdminService adminService, TaskService taskService) {
        this.adminService = adminService;
        this.taskService = taskService;
    }

    @GetMapping(path = "/{id}")
    public String getTaskById(@PathVariable("id") int id, Model model) {
        model.addAttribute("task", taskService.getTaskById(id));
        return "show";
    }

    @GetMapping
    public String getAlltask(Model model) {
        model.addAttribute("tasks", taskService.getAllTasks());
        return "tasks";
    }

    @GetMapping("/new")
    public String newTaskPage(@ModelAttribute("task") Task task) {
        return "new";
    }

    @PostMapping
    public String create(@ModelAttribute("task") @Valid Task task,
                       BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            return "/new";

        taskService.create(task);
        return "redirect:/tasks";
    }

    @PatchMapping("/{id}")
    public String update(@ModelAttribute("task") @Valid Task task, BindingResult bindingResult,
                         @PathVariable("id") int id) {
        if (bindingResult.hasErrors())
            return "show";

        taskService.update(id, task);
        return "redirect:/tasks";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int id) {
        taskService.delete(id);
        return "redirect:/tasks";
    }

    @GetMapping("/admin")
    public String adminPage() {
        adminService.doAdminStuff();
        return "admin";
    }
}