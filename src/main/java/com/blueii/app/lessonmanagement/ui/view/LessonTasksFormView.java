package com.blueii.app.lessonmanagement.ui.view;

import com.blueii.app.lessonmanagement.domain.Overview;
import com.blueii.app.lessonmanagement.domain.Task;
import com.blueii.app.lessonmanagement.service.LessonService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.menubar.MenuBar;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;

import java.util.List;

@Route("lessons/new/tasks")
public class LessonTasksFormView extends VerticalLayout {

    private TaskForm currentTaskForm;


    public LessonTasksFormView(LessonService lessonService) {

        setSizeFull();
        setAlignItems(Alignment.CENTER);
        setJustifyContentMode(JustifyContentMode.START);

        H2 title = new H2("Tasks");
        H3 overviewTitle = new H3("Overview");

        Grid<Overview> overviewGrid = new Grid<>(Overview.class,false);
        overviewGrid.setAllRowsVisible(true);
        Overview overview = lessonService.getLesson().getOverview();
        overviewGrid.setItems(overview);
        overviewGrid.setColumns("title", "subtitle", "tags", "description");


        H3 tasksTitle = new H3("Tasks");

        Grid<Task> tasksGrid = new Grid<>(Task.class,false);
        tasksGrid.setAllRowsVisible(true);
        List<Task> tasks = lessonService.getLesson().getLessonTasks();
        tasksGrid.setItems(tasks);
        tasksGrid.addColumn(Task::getId).setHeader("Id");
        tasksGrid.addColumn(Task::getDiscriminatorType).setHeader("Task Type");
        tasksGrid.addColumn(Task::getTaskName).setHeader("Task Name").setAutoWidth(true);
        tasksGrid.addComponentColumn(item -> new Button("Edit")).setHeader("Edit").setAutoWidth(true);
        tasksGrid.addComponentColumn(item -> new Button("Delete")).setHeader("Delete").setAutoWidth(true);

        H3 formTitle = new H3("Add Tasks");
        MenuBar taskMenu = new MenuBar();
        Div formDiv = new Div();
        formDiv.setWidthFull();


        Button backButton = new Button("Back", event -> getUI().ifPresent(ui -> ui.navigate("lessons/new")));
        Button saveLessonButton = new Button("Save Lesson", event -> {
            lessonService.saveLesson();
            getUI().ifPresent(ui -> ui.navigate("lessons/new"));
        });
        HorizontalLayout horizontalLayout = new HorizontalLayout(backButton, saveLessonButton);
        horizontalLayout.setWidthFull();
        horizontalLayout.setJustifyContentMode(JustifyContentMode.CENTER);


       taskMenu.addItem("Reference", e -> {
           formDiv.removeAll();
           currentTaskForm = new ReferenceTaskForm(lessonService, tasksGrid);
           formDiv.add(currentTaskForm.getFormComponent());
        });

        taskMenu.addItem("Multiple Choice", e -> {
            formDiv.removeAll();
            currentTaskForm = new MultipleChoiceTaskForm(lessonService, tasksGrid);
            formDiv.add(currentTaskForm.getFormComponent());
        });


        add(title, overviewTitle, overviewGrid, tasksTitle, tasksGrid, formTitle,taskMenu, formDiv, horizontalLayout);


    }



}


