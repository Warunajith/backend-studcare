package com.studcare.data.jpa.adaptor;

import com.studcare.data.jpa.dto.SchoolClassDTO;
import com.studcare.data.jpa.dto.StudentDTO;
import com.studcare.data.jpa.dto.WardDTO;
import com.studcare.data.jpa.entity.SchoolClass;
import com.studcare.data.jpa.entity.Student;
import com.studcare.data.jpa.entity.User;
import com.studcare.data.jpa.entity.Ward;
import com.studcare.model.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

@Component
public class StudentAdapter {
	@Autowired
	private UserAdapter userAdapter;
	@Autowired
	private SchoolClassAdapter schoolClassAdapter;
	@Autowired
	@Lazy
	private WardAdapter wardAdapter;

	public Student adapt(StudentDTO studentDTO) {
		Student student = new Student();
		User user = userAdapter.adapt(studentDTO.getUserDTO());
		student.setUser(user);
		SchoolClass schoolClass = schoolClassAdapter.adapt(studentDTO.getSchoolClassDTO());
		student.setSchoolClass(schoolClass);
		Ward ward = wardAdapter.adapt(studentDTO.getWardDTO());
		student.setWard(ward);
		return student;
	}

	public StudentDTO adapt(Student student) {
		StudentDTO studentDTO = new StudentDTO();
		UserDTO userDTO = userAdapter.adapt(student.getUser());
		studentDTO.setUserDTO(userDTO);
		SchoolClassDTO schoolClassDTO = schoolClassAdapter.adapt(student.getSchoolClass());
		studentDTO.setSchoolClassDTO(schoolClassDTO);
		WardDTO wardDTO = wardAdapter.adapt(student.getWard());
		studentDTO.setWardDTO(wardDTO);
		return studentDTO;
	}
}
