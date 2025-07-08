package com.blueii.app.lessonmanagement.domain;


import jakarta.persistence.*;

@Entity
@DiscriminatorValue("Reference")
public class Reference extends Task {



    @Lob
    private String contentHtml;



    public String getContentHtml() {
        return contentHtml;
    }

    public void setContentHtml(String contentHtml) {
        this.contentHtml = contentHtml;
    }

    @Override
    public String getDiscriminatorType() {
        return "Reference";
    }


}
