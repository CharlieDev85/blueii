package com.blueii.app.lessonmanagement.service;

import org.springframework.stereotype.Service;

@Service

public class LessonDraftService {
    private String title;
    private String subtitle;

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getSubtitle() { return subtitle; }
    public void setSubtitle(String subtitle) { this.subtitle = subtitle; }
}
