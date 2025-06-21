package com.blueii.app.taskmanagement.service;

import com.blueii.app.taskmanagement.domain.TaskInit;
import com.blueii.app.taskmanagement.domain.TaskInitRepository;
import org.jspecify.annotations.Nullable;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.Clock;
import java.time.LocalDate;
import java.util.List;

@Service
@Transactional(propagation = Propagation.REQUIRES_NEW)
public class TaskService {

    private final TaskInitRepository taskInitRepository;

    private final Clock clock;

    TaskService(TaskInitRepository taskInitRepository, Clock clock) {
        this.taskInitRepository = taskInitRepository;
        this.clock = clock;
    }

    public void createTask(String description, @Nullable LocalDate dueDate) {
        if ("fail".equals(description)) {
            throw new RuntimeException("This is for testing the error handler");
        }
        var task = new TaskInit();
        task.setDescription(description);
        task.setCreationDate(clock.instant());
        task.setDueDate(dueDate);
        taskInitRepository.saveAndFlush(task);
    }

    public List<TaskInit> list(Pageable pageable) {
        return taskInitRepository.findAllBy(pageable).toList();
    }

}
