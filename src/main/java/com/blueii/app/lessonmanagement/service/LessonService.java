package com.blueii.app.lessonmanagement.service;

import com.blueii.app.lessonmanagement.domain.Lesson;
import com.blueii.app.lessonmanagement.domain.Overview;
import com.blueii.app.lessonmanagement.domain.Task;
import com.blueii.app.lessonmanagement.repository.LessonRepository;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

@Service

public class LessonService {
    Lesson lesson = new Lesson();
    private final LessonRepository lessonRepository;

    public LessonService(LessonRepository lessonRepository) {
        this.lessonRepository = lessonRepository;
        Overview overview = new Overview();
        List<Task> tasks = new ArrayList<>();
        lesson.setOverview(overview);
        lesson.setLessonTasks(tasks);
    }

    public void addTask(Task task) {
        lesson.getLessonTasks().add(task);
    }

    public void saveLesson() throws IOException {
        lessonRepository.save(lesson);
        if(this.getImageData() != null && this.getPdfData() != null){
            Path imagePath = Path.of("uploads", "lesson-" + lesson.getId() + "-image.png");
            Path pdfPath = Path.of("uploads", "lesson-" + lesson.getId() + "-file.pdf");
            // Create 'uploads/' folder if it doesn't exist
            Files.createDirectories(imagePath.getParent());
            // Write files
            Files.write(imagePath, this.getImageData());
            Files.write(pdfPath, this.getPdfData());
            // Set accessible URLs
            lesson.getOverview().setImageUrl("/uploads/lesson-" + lesson.getId() + "-image.png");
            lesson.getOverview().setPdfUrl("/uploads/lesson-" + lesson.getId() + "-file.pdf");
            lessonRepository.save(lesson);
        }
    }


    private byte[] imageData;
    private byte[] pdfData;



    public Lesson getLesson() {
        return lesson;
    }

    public void setLesson(Lesson lesson) {
        this.lesson = lesson;
    }

    public byte[] getImageData() {
        return imageData;
    }

    public void setImageData(byte[] imageData) {
        this.imageData = imageData;
    }

    public byte[] getPdfData() {
        return pdfData;
    }

    public void setPdfData(byte[] pdfData) {
        this.pdfData = pdfData;
    }

    public void clean() {
        lesson.setOverview(null);
        lesson.setLessonTasks(null);
        imageData = null;
        pdfData = null;
        lesson = new Lesson();
        List<Task> tasks = new ArrayList<>();
        lesson.setLessonTasks(tasks);
        lesson.setOverview(new Overview());
        lesson.setLessonTasks(new ArrayList<>());
        lesson.setLessonTasks(tasks);
    }
}
