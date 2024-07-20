package com.studcare.data.jpa.adaptor;

import com.studcare.data.jpa.dto.WardDTO;
import com.studcare.data.jpa.entity.User;
import com.studcare.data.jpa.entity.Ward;
import com.studcare.model.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class WardAdapter {
	@Autowired
	private UserAdapter userAdapter;

	public Ward adapt(WardDTO wardDTO) {
		Ward ward = new Ward();
		ward.setWardName(wardDTO.getWardName());
		User wardMaster = userAdapter.adapt(wardDTO.getHostelMaster());
		ward.setHostelMaster(wardMaster);
		ward.setCreatedTimestamp(LocalDateTime.now());
		ward.setModifiedTimestamp(LocalDateTime.now());
		return ward;
	}

	public WardDTO adapt(Ward ward) {
		WardDTO wardDTO = new WardDTO();
		wardDTO.setWardName(ward.getWardName());
		UserDTO hostelMasterDTO = userAdapter.adapt(ward.getHostelMaster());
		wardDTO.setHostelMaster(hostelMasterDTO);
		return wardDTO;
	}
}
