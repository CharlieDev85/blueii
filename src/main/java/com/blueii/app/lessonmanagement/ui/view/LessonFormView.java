package com.blueii.app.lessonmanagement.ui.view;

import com.blueii.app.lessonmanagement.domain.Overview;
import com.blueii.app.lessonmanagement.service.LessonService;
import com.blueii.app.lessonmanagement.util.StringUtil;
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
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import jakarta.annotation.security.PermitAll;

import java.io.IOException;
import java.io.InputStream;

@Route("lessons/new")
@PageTitle("Create Lesson")
@Menu(order = 1, icon = "vaadin:clipboard-check", title = "New Lesson")
@PermitAll // When security is enabled, allow all authenticated users
public class LessonFormView extends VerticalLayout {

    public LessonFormView(LessonService lessonService) {
        Overview overview = lessonService.getLesson().getOverview() != null ? lessonService.getLesson().getOverview() : new Overview();

        // View and layout setup
        setSizeFull();
        setAlignItems(Alignment.CENTER);
        setJustifyContentMode(JustifyContentMode.START);

        // Title of the form
        H2 title = new H2("Create New Lesson - Overview");

        // Form fields for lesson metadata
        // Load values from draft service if available
        TextField titleField = new TextField("Title");
        titleField.setValue(overview.getTitle() != null ? overview.getTitle() : "");

        TextField subtitleField = new TextField("Subtitle");
        subtitleField.setValue(overview.getSubtitle() != null ? overview.getSubtitle() : "");
        TextArea tags = new TextArea("tags(comma-separated)");
        tags.setValue(overview.getTags() != null ? overview.getTags().toString().replace("[", "").replace("]", "") : "");
        TextArea descriptionField = new TextArea("Description (Plain Text)");
        descriptionField.setValue(overview.getDescription() != null ? overview.getDescription() : "");
        MemoryBuffer imageBuffer = new MemoryBuffer();
        Upload imageUpload = new Upload();
        imageUpload.setReceiver(imageBuffer);
        imageUpload.setDropLabel(new Span("Drag & drop lesson image here"));
        imageUpload.setAcceptedFileTypes("image/png", "image/jpeg", "image/jpg", "image/webp");

        MemoryBuffer pdfBuffer = new MemoryBuffer();
        Upload pdfUpload = new Upload();
        pdfUpload.setReceiver(pdfBuffer);
        pdfUpload.setDropLabel(new Span("Drag & drop lesson PDF here"));
        pdfUpload.setAcceptedFileTypes("application/pdf");

        titleField.setWidthFull();
        subtitleField.setWidthFull();
        tags.setWidthFull();
        descriptionField.setWidthFull();

        // Upload listeners to store files in the service
        imageUpload.addSucceededListener(event -> {
            try (InputStream is = imageBuffer.getInputStream()) {
                lessonService.setImageData(is.readAllBytes());
            } catch (IOException e) {
                Notification.show("Image upload failed");
            }
        });

        pdfUpload.addSucceededListener(event -> {
            try (InputStream is = pdfBuffer.getInputStream()) {
                lessonService.setPdfData(is.readAllBytes());
            } catch (IOException e) {
                Notification.show("PDF upload failed");
            }
        });

        // Form layout and styling
        VerticalLayout formWrapper = new VerticalLayout();
        formWrapper.setWidth("100%");
        formWrapper.setMaxWidth("600px");
        formWrapper.setAlignItems(Alignment.CENTER);

        FormLayout form = new FormLayout();
        form.setWidthFull();
        form.setResponsiveSteps(new FormLayout.ResponsiveStep("0", 1));

        form.add(titleField);
        form.add(subtitleField);
        form.add(tags);
        form.add(descriptionField);
        form.add(imageUpload);
        form.add(imageUpload);
        form.add(pdfUpload);

        formWrapper.add(form);

        if (lessonService.getImageData() != null) {
            Notification.show("Previously uploaded image is already stored.");
        }
        if (lessonService.getPdfData() != null) {
            Notification.show("Previously uploaded PDF is already stored.");
        }

        // Continue button to store values and navigate to task form
        Button continueButton = new Button("Continue", event -> {
            if (titleField.isEmpty() || descriptionField.isEmpty()) {
                Notification.show("Please fill out required fields: Title and Description");
            } else {
                overview.setTitle(titleField.getValue());
                overview.setSubtitle(subtitleField.getValue());
                overview.setTags(StringUtil.csvToList(tags.getValue()));
                overview.setDescription(descriptionField.getValue());
                lessonService.getLesson().setOverview(overview);
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
