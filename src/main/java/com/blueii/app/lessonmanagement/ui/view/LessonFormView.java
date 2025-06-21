package com.blueii.app.lessonmanagement.ui.view;

import com.blueii.app.lessonmanagement.service.LessonDraftService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.component.upload.Upload;
import com.vaadin.flow.component.upload.receivers.MemoryBuffer;
import com.vaadin.flow.router.Menu;
import com.vaadin.flow.router.Route;

@Route("lessons/new")
@Menu(order = 1, icon = "vaadin:clipboard-check", title = "New Lesson")
public class LessonFormView extends VerticalLayout {
    public LessonFormView(LessonDraftService lessonDraftService) {
        setSizeFull();
        setAlignItems(Alignment.CENTER);
        setJustifyContentMode(JustifyContentMode.START);

        H2 title = new H2("Create New Lesson - Overview");

        // Overview Fields
        TextField titleField = new TextField("Title");
        TextField subtitleField = new TextField("Subtitle");
        TextArea levelsField = new TextArea("Recommended Levels (comma-separated)");
        TextArea descriptionField = new TextArea("Description (Plain Text)");

        //Preload fields from LessonDraftService
        titleField.setValue(lessonDraftService.getTitle() != null ? lessonDraftService.getTitle() : "");
        subtitleField.setValue(lessonDraftService.getSubtitle() != null ? lessonDraftService.getSubtitle() : "");


        // Upload fields for image and PDF
        MemoryBuffer imageBuffer = new MemoryBuffer();
        Upload imageUpload = new Upload(imageBuffer);
        imageUpload.setAcceptedFileTypes("image/png", "image/jpeg", "image/jpg", "image/webp");
        imageUpload.setMaxFiles(1);
        imageUpload.setDropLabel(new Span("Upload Lesson Image (700x466 required)"));
        imageUpload.setWidthFull();
        //imageUpload.setWidth("700px");
        //imageUpload.setHeight("78px");
        imageUpload.getStyle().set("aspect-ratio", "700 / 466");

        MemoryBuffer pdfBuffer = new MemoryBuffer();
        Upload pdfUpload = new Upload(pdfBuffer);
        pdfUpload.setAcceptedFileTypes("application/pdf");
        pdfUpload.setMaxFiles(1);
        pdfUpload.setDropLabel(new Span("Upload Lesson PDF"));
        pdfUpload.setWidthFull();

        // Adjust widths
        titleField.setWidthFull();
        subtitleField.setWidthFull();
        levelsField.setWidthFull();
        descriptionField.setWidthFull();

        // Wrapper to center the FormLayout
        VerticalLayout formWrapper = new VerticalLayout();
        formWrapper.setWidth("100%");
        formWrapper.setMaxWidth("600px");
        formWrapper.setAlignItems(Alignment.CENTER);

        // Form layout configuration
        FormLayout form = new FormLayout();
        form.setWidthFull();
        form.setResponsiveSteps(new FormLayout.ResponsiveStep("0", 1));

        form.add(titleField);
        form.add(subtitleField);
        form.add(levelsField);
        form.add(descriptionField);
        form.add(imageUpload);
        form.add(pdfUpload);

        formWrapper.add(form);

        // Continue button (navigation with validation)
        Button continueButton = new Button("Continue", event -> {
            if (titleField.isEmpty() || descriptionField.isEmpty()) {
                Notification.show("Please fill out required fields: Title and Description");
            } else {
                lessonDraftService.setTitle(titleField.getValue());
                lessonDraftService.setSubtitle(subtitleField.getValue());
                getUI().ifPresent(ui -> ui.navigate("lessons/new/tasks"));

            }
        });

        HorizontalLayout buttonLayout = new HorizontalLayout(continueButton);
        buttonLayout.setWidthFull();
        buttonLayout.setJustifyContentMode(JustifyContentMode.CENTER);

        formWrapper.add(buttonLayout);

        add(title, formWrapper);
    }

}
