package com.blueii.app.lessonmanagement.ui.view;

import com.blueii.app.lessonmanagement.service.LessonDraftService;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.router.Route;

@Route("lessons/new/tasks")

public class LessonTasksFormView extends VerticalLayout {

    public LessonTasksFormView(LessonDraftService lessonDraftService) {
        setSizeFull();
        setAlignItems(Alignment.CENTER);
        setJustifyContentMode(JustifyContentMode.CENTER);

        H2 taskTitle = new H2("Add Tasks to Lesson");

        Paragraph overviewSummary = new Paragraph(
                "Lesson: " + lessonDraftService.getTitle() +
                        " | Subtitle: " + lessonDraftService.getSubtitle()
        );

        Select<String> taskTypeSelect = new Select<>();
        taskTypeSelect.setItems("Reference", "MultipleChoice");
        taskTypeSelect.setLabel("Add Task Type");
        taskTypeSelect.setWidthFull();

        VerticalLayout taskFormContainer = new VerticalLayout();
        taskFormContainer.setWidthFull();

        taskTypeSelect.addValueChangeListener(event -> {
            taskFormContainer.removeAll();
            String selected = event.getValue();
            if ("Reference".equals(selected)) {

                //reference selected
                Div editorContainer = new Div();
                editorContainer.setId("quill-editor");
                editorContainer.setWidthFull();
                editorContainer.setHeight("200px");
                taskFormContainer.add(editorContainer);
                UI.getCurrent().getPage().addStyleSheet("https://cdn.jsdelivr.net/npm/quill@1.3.7/dist/quill.snow.css");
                UI.getCurrent().getPage().addJavaScript("https://cdn.jsdelivr.net/npm/quill@1.3.7/dist/quill.min.js");
                UI.getCurrent().getPage().executeJs("""
                    setTimeout(function () {
                        let container = document.querySelector('#quill-editor');
                        if (container) {
                            container.innerHTML = '';
                            new Quill(container, { theme: 'snow' });
                        }
                    }, 300);
                """);

            } else if ("MultipleChoice".equals(selected)) {
                com.vaadin.flow.component.textfield.TextArea instructionsField = new com.vaadin.flow.component.textfield.TextArea("Instructions");
                instructionsField.setHeight("100px");
                instructionsField.setWidthFull();
                taskFormContainer.add(instructionsField);
            }
        });

        Button saveButton = new Button("Save Lesson");
        Button backButton = new Button("Back", event -> getUI().ifPresent(ui -> ui.navigate("lessons/new")));

        HorizontalLayout buttonLayout = new HorizontalLayout(backButton, saveButton);
        buttonLayout.setWidthFull();
        buttonLayout.setJustifyContentMode(JustifyContentMode.CENTER);

        VerticalLayout wrapper = new VerticalLayout(taskTitle, overviewSummary, taskTypeSelect, taskFormContainer, buttonLayout);
        wrapper.setWidth("100%");
        wrapper.setMaxWidth("600px");
        wrapper.setAlignItems(Alignment.CENTER);

        add(wrapper);
    }
}