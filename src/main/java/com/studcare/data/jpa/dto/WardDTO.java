package com.studcare.data.jpa.dto;

import lombok.Data;

import java.util.List;

@Data
public class WardDTO {
	private String wardName;
	private HostelMasterDTO hostelMasterDTO;
	private List<StudentDTO> studentsDTO;
}
