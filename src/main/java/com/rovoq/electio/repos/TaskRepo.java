package com.rovoq.electio.repos;

import com.rovoq.electio.domain.Task;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskRepo extends JpaRepository<Task, Long> {
}
