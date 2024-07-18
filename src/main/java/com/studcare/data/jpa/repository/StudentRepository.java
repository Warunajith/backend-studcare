package com.studcare.data.jpa.repository;

import com.studcare.data.jpa.entity.SchoolClass;
import com.studcare.data.jpa.entity.Student;
import com.studcare.data.jpa.entity.Ward;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {
	List<Student> findByUser_EmailIn(List<String> emails);
	List<Student> findBySchoolClass(SchoolClass schoolClass);
	List<Student> findByWard(Ward ward);
}