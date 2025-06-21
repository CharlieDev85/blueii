package com.blueii.app.lessonmanagement.domain;

import jakarta.persistence.*;

import java.util.List;

@Entity
public class MultipleChoiceQuestion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String question;

    @ElementCollection
    private List<String> options;

    private String correctAnswer;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "multiple_choice_id")
    private MultipleChoice multipleChoice;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public List<String> getOptions() {
        return options;
    }

    public void setOptions(List<String> options) {
        this.options = options;
    }

    public String getCorrectAnswer() {
        return correctAnswer;
    }

    public void setCorrectAnswer(String correctAnswer) {
        this.correctAnswer = correctAnswer;
    }

    public MultipleChoice getMultipleChoice() {
        return multipleChoice;
    }

    public void setMultipleChoice(MultipleChoice multipleChoice) {
        this.multipleChoice = multipleChoice;
    }
}
