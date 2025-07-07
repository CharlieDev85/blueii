package com.blueii.app.lessonmanagement.domain;


import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.Lob;

@Entity
@DiscriminatorValue("REFERENCE")
public class Reference extends Task {

    @Lob
    private String contentHtml;

    public Reference(){
        super("REFERENCE");
    }

    public String getContentHtml() {
        return contentHtml;
    }

    public void setContentHtml(String contentHtml) {
        this.contentHtml = contentHtml;
    }


}
