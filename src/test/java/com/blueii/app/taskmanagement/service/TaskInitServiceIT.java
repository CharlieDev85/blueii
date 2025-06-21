package com.blueii.app.taskmanagement.service;

import com.blueii.app.TestcontainersConfiguration;
import com.blueii.app.taskmanagement.domain.TaskInit;
import com.blueii.app.taskmanagement.domain.TaskInitRepository;
import jakarta.validation.ValidationException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.PageRequest;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.Clock;
import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@Import(TestcontainersConfiguration.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@Transactional(propagation = Propagation.NOT_SUPPORTED)
class TaskInitServiceIT {

    @Autowired
    TaskService taskService;

    @Autowired
    TaskInitRepository taskInitRepository;

    @Autowired
    Clock clock;

    @AfterEach
    void cleanUp() {
        taskInitRepository.deleteAll();
    }

    @Test
    public void tasks_are_stored_in_the_database_with_the_current_timestamp() {
        var now = clock.instant();
        var due = LocalDate.of(2025, 2, 7);
        taskService.createTask("Do this", due);
        assertThat(taskService.list(PageRequest.ofSize(1))).singleElement()
                .matches(taskInit -> taskInit.getDescription().equals("Do this") && due.equals(taskInit.getDueDate())
                        && taskInit.getCreationDate().isAfter(now));
    }

    @Test
    public void tasks_are_validated_before_they_are_stored() {
        assertThatThrownBy(() -> taskService.createTask("X".repeat(TaskInit.DESCRIPTION_MAX_LENGTH + 1), null))
                .isInstanceOf(ValidationException.class);
        assertThat(taskInitRepository.count()).isEqualTo(0);
    }
}
