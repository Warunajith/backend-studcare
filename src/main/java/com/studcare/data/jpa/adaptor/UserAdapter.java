package com.studcare.data.jpa.adaptor;

import com.studcare.data.jpa.entity.User;
import com.studcare.model.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;


@Component
public class UserAdapter{
	@Autowired
	private PasswordEncoder passwordEncoder;

	public User adapt(UserDTO userDTO) {
		User user = new User();
		user.setEmail(userDTO.getEmail());
		user.setUsername(userDTO.getUsername());
		user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
		user.setRole(userDTO.getRole());
		user.setIsClassTeacher(userDTO.isClassTeacher());
		return user;
	}

	public UserDTO adapt(User user) {
		UserDTO userDTO = new UserDTO();
		userDTO.setEmail(user.getEmail());
		userDTO.setUsername(user.getUsername());
		userDTO.setRole(user.getRole());
		userDTO.setClassTeacher(user.getIsClassTeacher());
		return userDTO;
	}
}
