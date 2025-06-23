package com.blueii.app.lessonmanagement.domain;


import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.Lob;

@Entity
@DiscriminatorValue("REFERENCE")
public class Reference extends Task {

    private String referenceName;

    @Lob
    private String contentHtml;

    public String getContentHtml() {
        return contentHtml;
    }

    public void setContentHtml(String contentHtml) {
        this.contentHtml = contentHtml;
    }

    public String getReferenceName() {
        return referenceName;
    }

    public void setReferenceName(String referenceName) {
        this.referenceName = referenceName;
    }
}
