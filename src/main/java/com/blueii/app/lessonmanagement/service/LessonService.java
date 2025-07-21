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
        // Save lesson first to ensure it has an ID
        lessonRepository.save(lesson);

        // If no file uploaded, skip file logic
        if (this.getImageData() == null && this.getPdfData() == null) return;

        Path uploadDir = Path.of("uploads");
        Files.createDirectories(uploadDir); // Ensure folder exists

        // Build file names using lesson ID
        String baseName = "lesson-" + lesson.getId();
        Path imagePath = uploadDir.resolve(baseName + "-image.webp");
        Path pdfPath = uploadDir.resolve(baseName + "-file.pdf");

        // Write files
        if (this.getImageData() != null) {
            Files.write(imagePath, this.getImageData());
            lesson.getOverview().setImageUrl("/uploads/" + imagePath.getFileName());
        }

        if (this.getPdfData() != null) {
            Files.write(pdfPath, this.getPdfData());
            lesson.getOverview().setPdfUrl("/uploads/" + pdfPath.getFileName());
        }

        // Save again with updated overview
        lessonRepository.save(lesson);
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
    public List<Lesson> getAllLessons() {
        return lessonRepository.findAll();
    }

    public Lesson getLessonById(Long id) {
        lesson = lessonRepository.findById(id).orElse(null);
        return lesson;
    }
}
