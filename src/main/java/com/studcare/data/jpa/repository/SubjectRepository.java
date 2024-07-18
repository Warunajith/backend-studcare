package com.studcare.data.jpa.repository;

import com.studcare.data.jpa.entity.Subject;
import com.studcare.data.jpa.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SubjectRepository extends JpaRepository<Subject, Long> {
	boolean existsBySubjectName(String subjects);
}
