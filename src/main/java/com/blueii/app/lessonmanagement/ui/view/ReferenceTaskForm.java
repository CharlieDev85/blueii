package com.blueii.app.lessonmanagement.ui.view;

import com.blueii.app.lessonmanagement.domain.Reference;
import com.blueii.app.lessonmanagement.domain.Task;
import com.blueii.app.lessonmanagement.service.LessonService;
import com.blueii.app.lessonmanagement.ui.util.QuillUtil;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.textfield.TextField;

import java.util.List;
import java.util.Optional;

public class ReferenceTaskForm implements TaskForm{
    //private final VerticalLayout layout = new VerticalLayout();
    private final FormLayout referenceForm = new FormLayout();
    private final TextField referenceNameField = new TextField("Reference Name");
    private final Div quillEditor = new Div();


    ReferenceTaskForm(LessonService lessonService, Grid<Task> tasksGrid) {
        loadQuill();

        // Empty at first
        Button addReferenceButton = new Button("Add Reference", event -> {
            UI.getCurrent().getPage().executeJs("return document.querySelector('#quill-editor .ql-editor').innerHTML;")
                    .then(String.class, contentHtml -> {
                        Reference reference = new Reference();
                        reference.setLesson(lessonService.getLesson());
                        reference.setTaskName(referenceNameField.getValue());
                        reference.setContentHtml(contentHtml);
                        lessonService.addTask(reference);
                        List<Task> allTasks = lessonService.getLesson().getLessonTasks();

                        //todo: fix this for the program to use a Grid<Task> review...
                        tasksGrid.setItems(allTasks); // Empty at first
                        referenceNameField.clear();
                        UI.getCurrent().getPage().executeJs("document.querySelector('#quill-editor .ql-editor').innerHTML = ''; ");
                    });
        });

        //referenceNameField.clear();
        referenceNameField.setWidthFull();

        referenceForm.setMaxWidth("800px");
        referenceForm.setWidthFull();
        referenceForm.setResponsiveSteps(new FormLayout.ResponsiveStep("0", 1));
        referenceForm.add(referenceNameField);

        referenceForm.add(quillEditor);
        referenceForm.add(addReferenceButton);



    }

    private void loadQuill() {
        quillEditor.setId("quill-editor");
        quillEditor.setWidthFull();
        quillEditor.setHeight("500px");
        quillEditor.getStyle()
                .set("border", "1px solid #ccc")
                .set("border-radius", "6px")
                .set("padding", "8px");

        QuillUtil.initializeQuill("quill-editor");
    }

    @Override
    public Component getFormComponent() {
        return referenceForm;
    }

    @Override
    public String getType() {
        return "Reference";
    }

    @Override
    public Optional<Task> createTask() {
        //todo
        return Optional.empty();
    }
}
