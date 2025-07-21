package com.blueii.app.lessonmanagement.ui.view;

import com.blueii.app.lessonmanagement.domain.Lesson;
import com.blueii.app.lessonmanagement.domain.Overview;
import com.blueii.app.lessonmanagement.service.LessonService;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.BeforeEvent;
import com.vaadin.flow.router.HasUrlParameter;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import jakarta.annotation.security.PermitAll;
import com.vaadin.flow.component.html.Anchor;

@Route("lessons")
@PageTitle("Lesson")
@PermitAll
public class LessonDetailView extends VerticalLayout implements HasUrlParameter<Long> {

    private final LessonService lessonService;

    public LessonDetailView(LessonService lessonService) {
        this.lessonService = lessonService;
        setSizeFull();
        setPadding(true);
        setSpacing(true);
    }

    @Override
    public void setParameter(BeforeEvent beforeEvent, Long id) {
        if (id == null) {
            add(new Paragraph("Lesson ID is missing."));
            return;
        }
        Lesson lesson = lessonService.getLessonById(id);
        if (lesson == null) {
            add(new Paragraph("Lesson not found."));
            return;
        }
        Overview overview = lesson.getOverview();

        add(
                new H2(overview.getTitle()),
                new Paragraph("Subtitle: " + overview.getSubtitle()),
                new Paragraph("Description: " + overview.getDescription()),
                new Paragraph("Tags: " + String.join(", ", overview.getTags()))
        );

        if (overview.getImageUrl() != null) {
            Image image = new Image(overview.getImageUrl(), "Lesson image");
            image.setMaxWidth("400px");
            add(image);
        }

        if (overview.getPdfUrl() != null) {
            Anchor pdfLink = new Anchor(overview.getPdfUrl(), "View PDF");
            pdfLink.setTarget("_blank");
            add(pdfLink);
        }

    }
}
