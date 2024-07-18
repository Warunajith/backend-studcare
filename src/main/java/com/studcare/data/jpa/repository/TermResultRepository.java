package com.studcare.data.jpa.repository;

import com.studcare.data.jpa.entity.Student;
import com.studcare.data.jpa.entity.SubjectTeacher;
import com.studcare.data.jpa.entity.TermResult;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TermResultRepository extends JpaRepository<TermResult, Long> {
	Optional<TermResult> findByStudentAndAcademicYearAndTermNumber(Student student, Integer academicYear, Integer termNumber);
}