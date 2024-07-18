package com.studcare.data.jpa.repository;

import com.studcare.data.jpa.entity.HostelMaster;
import com.studcare.data.jpa.entity.Subject;
import com.studcare.data.jpa.entity.SubjectResult;
import com.studcare.data.jpa.entity.TermResult;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SubjectResultRepository extends JpaRepository<SubjectResult, Long> {
	Optional<SubjectResult> findByTermResultAndSubject(TermResult termResult, Subject subject);
	List<SubjectResult> findByTermResult(TermResult termResult);
}