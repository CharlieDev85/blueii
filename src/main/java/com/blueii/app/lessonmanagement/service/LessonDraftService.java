package com.blueii.app.lessonmanagement.service;

import com.blueii.app.lessonmanagement.domain.Reference;
import com.blueii.app.lessonmanagement.domain.Task;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service

public class LessonDraftService {
    private String title;
    private String subtitle;
    private String levels;
    private String description;
    private byte[] imageData;
    private byte[] pdfData;

    private final List<Task> tasks = new ArrayList<>();

    public void reset() {
        this.title = null;
        this.subtitle = null;
        this.levels = null;
        this.description = null;
        this.imageData = null;
        this.pdfData = null;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSubtitle() {
        return subtitle;
    }

    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle;
    }

    public String getLevels() {
        return levels;
    }

    public void setLevels(String levels) {
        this.levels = levels;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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
    public List<Task> getTasks() {
        return tasks;
    }
    public void setTasks(List<Task> tasks) {
        this.tasks.clear();
        this.tasks.addAll(tasks);
    }

    public void addTask(Reference ref) {
        tasks.add(ref);
    }
}
