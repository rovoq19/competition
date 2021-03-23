package com.rovoq.electio.repos;

import com.rovoq.electio.domain.Test;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TestRepo extends JpaRepository<Test, Long> {
}
