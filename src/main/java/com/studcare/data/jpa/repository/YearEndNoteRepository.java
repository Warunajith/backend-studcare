package com.studcare.data.jpa.repository;

import com.studcare.data.jpa.entity.SubjectTeacher;
import com.studcare.data.jpa.entity.YearEndNote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface YearEndNoteRepository extends JpaRepository<YearEndNote, Long> {
}