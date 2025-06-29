package com.blueii.app.lessonmanagement.repository;

import com.blueii.app.lessonmanagement.domain.Lesson;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LessonRepository extends JpaRepository<Lesson, Long> {}
