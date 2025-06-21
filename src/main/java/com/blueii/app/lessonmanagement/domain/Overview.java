package com.blueii.app.lessonmanagement.domain;

import jakarta.persistence.*;

import java.util.List;

@Embeddable
public class Overview {

    private String title;
    private String subtitle;

    @ElementCollection
    private List<String> recommendedLevels;

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

    public List<String> getRecommendedLevels() {
        return recommendedLevels;
    }

    public void setRecommendedLevels(List<String> recommendedLevels) {
        this.recommendedLevels = recommendedLevels;
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
