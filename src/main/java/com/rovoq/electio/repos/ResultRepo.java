package com.rovoq.electio.repos;

import com.rovoq.electio.domain.Result;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ResultRepo extends JpaRepository<Result, Long> {
    List<Result> findByTest(Long testId);
}
