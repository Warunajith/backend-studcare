package com.studcare.data.jpa.adaptor;

import com.studcare.data.jpa.dto.SchoolClassDTO;
import com.studcare.data.jpa.dto.StudentDTO;
import com.studcare.data.jpa.entity.SchoolClass;
import com.studcare.data.jpa.entity.Student;
import com.studcare.data.jpa.entity.User;
import com.studcare.model.UserDTO;
import jdk.jfr.Label;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class SchoolClassAdapter {
	@Autowired
	private UserAdapter userAdapter;
	@Autowired
	@Lazy
	private StudentAdapter studentAdapter;

	public SchoolClass adapt(SchoolClassDTO schoolClassDTO) {
		SchoolClass schoolClass = new SchoolClass();
		schoolClass.setClassName(schoolClassDTO.getClassName());
		User classTeacher = userAdapter.adapt(schoolClassDTO.getClassTeacher());
		schoolClass.setClassTeacher(classTeacher);
		List<Student> students = new ArrayList<>();
		for(StudentDTO studentDTO : schoolClassDTO.getStudents()){
			Student student = studentAdapter.adapt(studentDTO);
			students.add(student);
		}
		schoolClass.setStudents(students);
		return schoolClass;
	}

	public SchoolClassDTO adapt(SchoolClass schoolClass) {
		SchoolClassDTO schoolClassDTO = new SchoolClassDTO();
		schoolClassDTO.setClassName(schoolClass.getClassName());
		UserDTO classTeacherDTO = userAdapter.adapt(schoolClass.getClassTeacher());
		schoolClassDTO.setClassTeacher(classTeacherDTO);
		List<StudentDTO> studentsList = new ArrayList<>();
		for(Student student : schoolClass.getStudents()){
			StudentDTO studentDTO = studentAdapter.adapt(student);
			studentsList.add(studentDTO);
		}
		schoolClassDTO.setStudents(studentsList);
		return schoolClassDTO;
	}
}
