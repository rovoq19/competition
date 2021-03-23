package com.rovoq.electio.repos;

import com.rovoq.electio.domain.Exam;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExamRepo extends JpaRepository<Exam, Long> {
}
