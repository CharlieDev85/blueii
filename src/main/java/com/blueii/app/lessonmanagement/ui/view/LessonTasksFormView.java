package com.blueii.app.lessonmanagement.ui.view;

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

@Route("lessons/new/tasks")
public class LessonTasksFormView extends VerticalLayout {

    private TaskForm currentTaskForm;


    public LessonTasksFormView(LessonService lessonService) {

        setSizeFull();
        setAlignItems(Alignment.CENTER);
        setJustifyContentMode(JustifyContentMode.START);

        H2 title = new H2("Tasks");
        H3 overviewTitle = new H3("Overview");

        Grid<String[]> overviewGrid = new Grid<>();
        overviewGrid.addColumn(data -> data[0]).setHeader("Title");
        overviewGrid.addColumn(data -> data[1]).setHeader("Subtitle");
        overviewGrid.addColumn(data -> data[2]).setHeader("tags");
        String[] overviewData = new String[]{
                lessonService.getLesson().getOverview().getTitle(),
                lessonService.getLesson().getOverview().getSubtitle(),
                lessonService.getLesson().getOverview().getTags().toString(),
        };
        overviewGrid.setItems(overviewData);

        H3 tasksTitle = new H3("Tasks");

        Grid<String[]> tasksGrid = new Grid<>();
        tasksGrid.setAllRowsVisible(true);
        tasksGrid.addColumn(data -> data[0]).setHeader("Num").setAutoWidth(true);
        tasksGrid.addColumn(data -> data[1]).setHeader("Type").setAutoWidth(true);
        tasksGrid.addColumn(data -> data[2]).setHeader("Task Name").setAutoWidth(true);
        tasksGrid.addComponentColumn(item -> new Button("Edit")).setHeader("Edit").setAutoWidth(true);
        tasksGrid.addComponentColumn(item -> new Button("Delete")).setHeader("Delete").setAutoWidth(true);

        H3 formTitle = new H3("Add Tasks");
        MenuBar taskMenu = new MenuBar();
        Div formDiv = new Div();
        formDiv.setWidthFull();


        Button backButton = new Button("Back", event -> getUI().ifPresent(ui -> ui.navigate("lessons/new")));
        Button saveLessonButton = new Button("Save Lesson", event -> {  });
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


