package com.blueii.app.lessonmanagement.domain;


import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.Lob;

@Entity
@DiscriminatorValue("REFERENCE")
public class Reference extends Task {
    @Lob
    private String contentHtml;
}
