//package com.studcare.data.jpa.adaptor;
//
//import com.studcare.data.jpa.dto.SchoolClassDTO;
//import com.studcare.data.jpa.dto.HostelMasterDTO;
//import com.studcare.data.jpa.dto.WardDTO;
//import com.studcare.data.jpa.entity.HostelMaster;
//import com.studcare.data.jpa.entity.SchoolClass;
//import com.studcare.data.jpa.entity.HostelMaster;
//import com.studcare.data.jpa.entity.User;
//import com.studcare.data.jpa.entity.Ward;
//import com.studcare.model.UserDTO;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;
//
//@Component
//public class HostelMasterAdapter {
//	@Autowired
//private UserAdapter userAdapter;
//
//	public HostelMaster adapt(HostelMasterDTO hostelMasterDTO) {
//		HostelMaster hostelMaster = new HostelMaster();
//		User user = userAdapter.adapt(hostelMasterDTO.getUserDTO());
//		hostelMaster.setUser(user);
//		return hostelMaster;
//	}
//
//	public HostelMasterDTO adapt(HostelMaster hostelMaster) {
//		HostelMasterDTO hostelMasterDTO = new HostelMasterDTO();
//		UserDTO userDTO = userAdapter.adapt(hostelMaster.getUser());
//		hostelMasterDTO.setUserDTO(userDTO);
//		return hostelMasterDTO;
//	}
//}
