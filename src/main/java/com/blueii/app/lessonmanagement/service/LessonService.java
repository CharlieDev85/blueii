package com.blueii.app.lessonmanagement.service;

import com.blueii.app.lessonmanagement.domain.Lesson;
import com.blueii.app.lessonmanagement.domain.Overview;
import com.blueii.app.lessonmanagement.domain.Task;
import com.blueii.app.lessonmanagement.repository.LessonRepository;
import org.springframework.stereotype.Service;

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

    public void saveLesson() {
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
}
