package com.blueii.app.lessonmanagement.ui.view;

import com.blueii.app.lessonmanagement.domain.MultipleChoice;
import com.blueii.app.lessonmanagement.domain.MultipleChoiceQuestion;
import com.blueii.app.lessonmanagement.domain.Task;
import com.blueii.app.lessonmanagement.service.LessonService;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class MultipleChoiceTaskForm implements TaskForm{


    private final TextField taskNameField = new TextField("Task Name");
    private final TextField instructionsField = new TextField("Instructions");
    private final Grid<MultipleChoiceQuestion> questionGrid = new Grid<>();
    private final VerticalLayout questionFormLayout = new VerticalLayout();
    private final Button saveMultipleChoiceButton = new Button("Save Multiple Choice");
    private final FormLayout mainForm = new FormLayout();

    private final List<MultipleChoiceQuestion> questionList = new ArrayList<>();
    private final MultipleChoice multipleChoice = new MultipleChoice();

    public MultipleChoiceTaskForm(LessonService lessonService, Grid<Task> tasksGrid) {

        questionGrid.setWidthFull();
        questionGrid.addColumn(q -> questionList.indexOf(q) + 1).setHeader("Num");
        questionGrid.addColumn(MultipleChoiceQuestion::getQuestion).setHeader("Question");
        questionGrid.addColumn(q -> String.join(", ", q.getOptions())).setHeader("Answers");
        questionGrid.addComponentColumn(q -> new Button("Edit")).setHeader("Edit");
        questionGrid.addComponentColumn(q -> new Button("Delete", e -> {
            questionList.remove(q);
            questionGrid.setItems(questionList);
        })).setHeader("Delete");

        saveMultipleChoiceButton.addClickListener(e -> {
            multipleChoice.setTaskName(taskNameField.getValue());
            multipleChoice.setInstructions(instructionsField.getValue());
            multipleChoice.setQuestions(questionList);
            lessonService.addTask(multipleChoice);
            List<Task> allTasks = lessonService.getLesson().getLessonTasks();
            tasksGrid.setItems(allTasks);
        });

        displayQuestionForm();

        mainForm.setWidthFull();
        mainForm.setMaxWidth("800px");
        mainForm.setResponsiveSteps(new FormLayout.ResponsiveStep("0", 1));
        mainForm.add(taskNameField);
        mainForm.add(instructionsField);
        mainForm.add(new Div(questionGrid));
        mainForm.add(questionFormLayout);
        mainForm.add(saveMultipleChoiceButton);
    }

    private void displayQuestionForm() {
        questionFormLayout.removeAll();

        TextField questionField = new TextField("Question");
        TextField option1 = new TextField("Answer 1");
        Checkbox correct1 = new Checkbox("Correct");
        TextField option2 = new TextField("Answer 2");
        Checkbox correct2 = new Checkbox("Correct");
        TextField option3 = new TextField("Answer 3");
        Checkbox correct3 = new Checkbox("Correct");

        Button addQuestionButton = new Button("Add Question", e -> {
            List<String> options = List.of(option1.getValue(), option2.getValue(), option3.getValue());
            String correct = correct1.getValue() ? option1.getValue() :
                    correct2.getValue() ? option2.getValue() :
                            correct3.getValue() ? option3.getValue() : "";
            MultipleChoiceQuestion question = new MultipleChoiceQuestion();
            question.setQuestion(questionField.getValue());
            question.setOptions(options);
            question.setCorrectAnswer(correct);
            questionList.add(question);
            questionGrid.setItems(questionList);
            displayQuestionForm();
        });
        HorizontalLayout option1Layout = new HorizontalLayout(option1, correct1);
        option1Layout.setAlignItems(FlexComponent.Alignment.BASELINE);
        HorizontalLayout option2Layout = new HorizontalLayout(option2, correct2);
        option2Layout.setAlignItems(FlexComponent.Alignment.BASELINE);
        HorizontalLayout option3Layout = new HorizontalLayout(option3, correct3);
        option3Layout.setAlignItems(FlexComponent.Alignment.BASELINE);

        questionFormLayout.add(questionField,
                option1Layout,
                option2Layout,
                option3Layout,
                addQuestionButton);
    }

    @Override
    public Component getFormComponent() {
        return mainForm;
    }

    @Override
    public String getType() {
        return "MultipleChoice";
    }

    @Override
    public Optional<Task> createTask() {
        return Optional.of(multipleChoice);
    }
}
