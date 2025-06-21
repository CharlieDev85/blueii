// File: src/main/java/com/blueii/app/lessonmanagement/domain/Lesson.java
package com.blueii.app.lessonmanagement.domain;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Lesson {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Embedded
    private Overview overview;

    @OneToMany(mappedBy = "lesson", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Task> tasks = new ArrayList<>();

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Overview getOverview() { return overview; }
    public void setOverview(Overview overview) { this.overview = overview; }

    public List<Task> getLessonTasks() { return tasks; }
    public void setLessonTasks(List<Task> tasks) { this.tasks = tasks; }
}
