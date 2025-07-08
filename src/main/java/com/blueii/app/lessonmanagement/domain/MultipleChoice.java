package com.blueii.app.lessonmanagement.domain;

import jakarta.persistence.*;
import java.util.List;

@Entity
@DiscriminatorValue("Multiple Choice")
public class MultipleChoice extends Task {

    private String instructions;


    @OneToMany(mappedBy = "multipleChoice", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<MultipleChoiceQuestion> questions;

    public String getInstructions() {
        return instructions;
    }

    public void setInstructions(String instructions) {
        this.instructions = instructions;
    }

    public List<MultipleChoiceQuestion> getQuestions() {
        return questions;
    }

    public void setQuestions(List<MultipleChoiceQuestion> questions) {
        this.questions = questions;
    }

    @Override
    public String getDiscriminatorType() {
        return "Multiple Choice";
    }

    public void addQuestion(MultipleChoiceQuestion question) {
        question.setMultipleChoice(this);
        this.questions.add(question);
    }
}
