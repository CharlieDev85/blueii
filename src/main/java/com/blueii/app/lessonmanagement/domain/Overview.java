package com.blueii.app.lessonmanagement.domain;

import jakarta.persistence.*;

import java.util.List;

@Embeddable
public class Overview {

    private String title;
    private String subtitle;

    @ElementCollection
    private List<String> tags;

    @Lob
    private String description;

    private String imageUrl;
    private String pdfUrl;



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

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> recommendedLevels) {
        this.tags = recommendedLevels;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getPdfUrl() {
        return pdfUrl;
    }

    public void setPdfUrl(String pdfUrl) {
        this.pdfUrl = pdfUrl;
    }
}
