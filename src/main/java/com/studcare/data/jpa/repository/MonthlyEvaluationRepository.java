package com.studcare.data.jpa.repository;

import com.studcare.data.jpa.entity.MonthlyEvaluation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MonthlyEvaluationRepository extends JpaRepository<MonthlyEvaluation, Long> {
}