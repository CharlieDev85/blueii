package com.blueii.app.taskmanagement.domain;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface TaskInitRepository extends JpaRepository<TaskInit, Long>, JpaSpecificationExecutor<TaskInit> {

    // If you don't need a total row count, Slice is better than Page.
    Slice<TaskInit> findAllBy(Pageable pageable);
}
