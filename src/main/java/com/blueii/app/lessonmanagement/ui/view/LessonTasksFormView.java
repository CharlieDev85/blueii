package com.blueii.app.lessonmanagement.ui.view;

import com.blueii.app.lessonmanagement.domain.Reference;
import com.blueii.app.lessonmanagement.domain.Task;
import com.blueii.app.lessonmanagement.service.LessonDraftService;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.menubar.MenuBar;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;

import java.util.ArrayList;
import java.util.List;

@Route("lessons/new/tasks")
public class LessonTasksFormView extends VerticalLayout {

    public LessonTasksFormView(LessonDraftService lessonDraftService) {setSizeFull();
        setAlignItems(Alignment.CENTER);
        setJustifyContentMode(JustifyContentMode.START);

        H2 title = new H2("Tasks");
        H3 overviewTitle = new H3("Overview");

        Grid<String[]> overviewGrid = new Grid<>();
        overviewGrid.addColumn(data -> data[0]).setHeader("Title");
        overviewGrid.addColumn(data -> data[1]).setHeader("Subtitle");
        overviewGrid.addColumn(data -> data[2]).setHeader("Levels");

        String[] overviewData = new String[]{
                lessonDraftService.getTitle(),
                lessonDraftService.getSubtitle(),
                lessonDraftService.getLevels()
        };

        overviewGrid.setItems(overviewData);

        Button backButton = new Button("Back");

        H3 tasksTitle = new H3("Tasks");

        Grid<String[]> tasksGrid = new Grid<>();
        tasksGrid.addColumn(data -> data[0]).setHeader("Num").setAutoWidth(true);
        tasksGrid.addColumn(data -> data[1]).setHeader("Type").setAutoWidth(true);
        tasksGrid.addColumn(data -> data[2]).setHeader("Task Name").setAutoWidth(true);
        tasksGrid.addComponentColumn(item -> new Button("Edit")).setHeader("Edit").setAutoWidth(true);
        tasksGrid.addComponentColumn(item -> new Button("Delete")).setHeader("Delete").setAutoWidth(true);



        H3 formTitle = new H3("Add Tasks");

        MenuBar taskMenu = new MenuBar();

        TextField referenceNameField = new TextField("Reference Name");
        referenceNameField.clear();
        referenceNameField.setWidthFull();

        Div quillEditor = new Div();
        quillEditor.setId("quill-editor");
        quillEditor.setWidthFull();
        quillEditor.setHeight("300px");
        quillEditor.getStyle().set("border", "1px solid #ccc").set("border-radius", "6px").set("padding", "8px");

        Button addReferenceButton = new Button("Add Reference", event -> {
            UI.getCurrent().getPage().executeJs("return document.querySelector('#quill-editor .ql-editor').innerHTML;")
                    .then(String.class, contentHtml -> {
                        Reference reference = new Reference();
                        reference.setReferenceName(referenceNameField.getValue());
                        reference.setContentHtml(contentHtml);
                        lessonDraftService.addTask(reference);
                        List<Task> allTasks = lessonDraftService.getTasks();
                        List<String[]> taskRows = new ArrayList<>();
                        for (int i = 0; i < allTasks.size(); i++) {
                            Task task = allTasks.get(i);
                            String type = task.getClass().getSimpleName();
                            String name = (task instanceof Reference ref) ? ref.getReferenceName() : "";
                            taskRows.add(new String[]{String.valueOf(i + 1), type, name});
                        }
                        tasksGrid.setItems(taskRows); // Empty at first
                        referenceNameField.clear();
                        UI.getCurrent().getPage().executeJs("document.querySelector('#quill-editor .ql-editor').innerHTML = ''; ");
                    });
        });

        FormLayout referenceForm = new FormLayout();
        referenceForm.setWidthFull();
        referenceForm.setResponsiveSteps(new FormLayout.ResponsiveStep("0", 1));
        referenceForm.add(referenceNameField);
        referenceForm.add(quillEditor);
        referenceForm.add(addReferenceButton);

        TextField multipleChoiceNameField = new TextField("Name");
        multipleChoiceNameField.setWidthFull();

        FormLayout multipleChoiceForm = new FormLayout();
        multipleChoiceForm.setWidthFull();
        multipleChoiceForm.setResponsiveSteps(new FormLayout.ResponsiveStep("0", 1));
        multipleChoiceForm.add(multipleChoiceNameField);
        multipleChoiceForm.setVisible(false);

        Div tasksForm = new Div();
        tasksForm.getStyle().set("border", "1px solid #ddd").set("border-radius", "8px").set("padding", "12px").set("margin-top", "16px");
        tasksForm.setId("tasks-form");
        tasksForm.add(formTitle, taskMenu, referenceForm, multipleChoiceForm);

        add(title, overviewTitle, overviewGrid, tasksTitle, tasksGrid, tasksForm, backButton);

        taskMenu.addItem("Reference", e -> {
            referenceForm.setVisible(true);
            multipleChoiceForm.setVisible(false);
        });

        taskMenu.addItem("Multiple Choice", e -> {
            referenceForm.setVisible(false);
            multipleChoiceForm.setVisible(true);
        });

        // Initialize the Quill editor
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
    }
}


