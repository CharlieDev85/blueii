package com.blueii.app.lessonmanagement.ui.view;


import com.blueii.app.lessonmanagement.domain.Lesson;
import com.blueii.app.lessonmanagement.service.LessonService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Menu;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import jakarta.annotation.security.PermitAll;

import java.util.List;

@Route("lessons")
@PageTitle("All Lesson")
@Menu(order = 3, icon = "vaadin:clipboard-check", title = "All Lessons")
@PermitAll // When security is enabled, allow all authenticated users
public class Lessons extends VerticalLayout {


    public Lessons(LessonService lessonService) {
        setSizeFull();
        setAlignItems(Alignment.CENTER);
        setJustifyContentMode(JustifyContentMode.START);
        H2 title = new H2("All Lessons");
        Grid<Lesson> lessonsGrid = new Grid<>(Lesson.class,false);
        lessonsGrid.setAllRowsVisible(true);
        List<Lesson> lessons = lessonService.getAllLessons();
        lessonsGrid.setItems(lessons);
        lessonsGrid.addColumn(Lesson::getId).setHeader("Id");
        lessonsGrid.addColumn(lesson -> {
            return lesson.getOverview().getTitle();
        }).setHeader("title");
        lessonsGrid.addComponentColumn(lesson -> {
            Button viewButton = new Button("View");
            viewButton.addClickListener(event -> {
                getUI().ifPresent(ui -> ui.navigate("lessons/" + lesson.getId()));
            });
            return viewButton;
                }

                ).setHeader("Action").setAutoWidth(true);

        add(title,lessonsGrid);
        if(lessons.isEmpty()){
            Notification.show("No lessons found");
        }else{
            Notification.show("Lessons found");
        }

    }

}
