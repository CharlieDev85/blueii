package com.blueii.app.lessonmanagement.ui.view;

import com.blueii.app.lessonmanagement.domain.Task;
import com.vaadin.flow.component.Component;

import java.util.Optional;

public interface TaskForm {

    Component getFormComponent(); // returns the UI block (layout, etc.)
    String getType();             // returns the task type (e.g., "Reference")
    Optional<Task> createTask();  // builds the Task object
}
