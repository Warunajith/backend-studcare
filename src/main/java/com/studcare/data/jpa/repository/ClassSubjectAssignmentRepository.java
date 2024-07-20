package com.studcare.data.jpa.repository;

import com.studcare.data.jpa.entity.ClassSubjectAssignment;
import com.studcare.data.jpa.entity.SchoolClass;
import com.studcare.data.jpa.entity.Subject;
import com.studcare.data.jpa.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ClassSubjectAssignmentRepository extends JpaRepository<ClassSubjectAssignment, ClassSubjectAssignment.ClassSubjectAssignmentKey> {
	List<ClassSubjectAssignment> findBySchoolClass(SchoolClass schoolClass);

	List<ClassSubjectAssignment> findBySubject(Subject subject);

	List<ClassSubjectAssignment> findByTeacher(User teacher);

	Optional<ClassSubjectAssignment> findBySchoolClassAndSubject(SchoolClass schoolClass, Subject subject);

	boolean existsBySchoolClassAndSubject(SchoolClass schoolClass, Subject subject);

	void deleteBySchoolClassAndSubject(SchoolClass schoolClass, Subject subject);

	List<ClassSubjectAssignment> findBySchoolClassAndTeacher(SchoolClass schoolClass, User teacher);

	Optional<List<ClassSubjectAssignment>> findByTeacherAndSubject(User teacher, Subject subject);
	boolean existsByTeacherAndSubject(User teacher, Subject subject);

}
