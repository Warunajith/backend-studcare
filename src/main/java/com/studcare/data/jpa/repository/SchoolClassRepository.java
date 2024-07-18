package com.studcare.data.jpa.repository;

import com.studcare.data.jpa.entity.SchoolClass;
import com.studcare.data.jpa.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SchoolClassRepository extends JpaRepository<SchoolClass, Long> {
	Optional<SchoolClass> findByClassName(String className);
	Optional<SchoolClass> findByClassTeacher(User classTeacher);

}
