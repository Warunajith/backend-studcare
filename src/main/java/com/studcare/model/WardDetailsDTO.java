package com.studcare.model;

import com.studcare.data.jpa.dto.StudentDTO;
import com.studcare.data.jpa.dto.WardDTO;
import com.studcare.data.jpa.entity.HostelMaster;
import lombok.Data;

import java.util.List;

@Data
public class WardDetailsDTO {
	private Long wardId;
	private String wardName;
	private HostelMaster hostelMaster;
	private List<StudentDTO> students;
	private WardDTO wardDTO;
}
