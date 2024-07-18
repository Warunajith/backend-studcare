package com.studcare.data.jpa.adaptor;

import com.studcare.data.jpa.dto.HostelMasterDTO;
import com.studcare.data.jpa.dto.WardDTO;
import com.studcare.data.jpa.dto.StudentDTO;
import com.studcare.data.jpa.entity.HostelMaster;
import com.studcare.data.jpa.entity.Ward;
import com.studcare.data.jpa.entity.Student;
import com.studcare.data.jpa.entity.User;
import com.studcare.model.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class WardAdapter {
	@Autowired
	private HostelMasterAdapter hostelMasterAdapter;
	@Autowired
	@Lazy
	private StudentAdapter studentAdapter;

	public Ward adapt(WardDTO wardDTO) {
		Ward ward = new Ward();
		ward.setWardName(wardDTO.getWardName());
		HostelMaster wardMaster = hostelMasterAdapter.adapt(wardDTO.getHostelMasterDTO());
		ward.setHostelMaster(wardMaster);
		List<Student> students = new ArrayList<>();
		for(StudentDTO studentDTO : wardDTO.getStudentsDTO()){
			Student student = studentAdapter.adapt(studentDTO);
			students.add(student);
		}
		ward.setStudents(students);
		return ward;
	}

	public WardDTO adapt(Ward ward) {
		WardDTO wardDTO = new WardDTO();
		wardDTO.setWardName(ward.getWardName());
		HostelMasterDTO hostelMasterDTO = hostelMasterAdapter.adapt(ward.getHostelMaster());
		wardDTO.setHostelMasterDTO(hostelMasterDTO);
		List<StudentDTO> studentsList = new ArrayList<>();
		for(Student student : ward.getStudents()){
			StudentDTO studentDTO = studentAdapter.adapt(student);
			studentsList.add(studentDTO);
		}
		wardDTO.setStudentsDTO(studentsList);
		return wardDTO;
	}
}
