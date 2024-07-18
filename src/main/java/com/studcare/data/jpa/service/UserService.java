package com.studcare.data.jpa.service;

import com.studcare.constants.Status;
import com.studcare.data.jpa.adaptor.UserAdapter;
import com.studcare.data.jpa.entity.User;
import com.studcare.data.jpa.repository.UserRepository;
import com.studcare.data.security.JwtService;
import com.studcare.exception.StudCareDataException;
import com.studcare.exception.StudCareValidationException;
import com.studcare.model.LogoutRequestDTO;
import com.studcare.model.LogoutResponseDTO;
import com.studcare.model.UserDTO;
import com.studcare.model.UserDeletionResponseDTO;
import com.studcare.model.UserProfileResponseDTO;
import com.studcare.model.UserRegisterResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;

import static com.studcare.constants.Security.AUTHORIZATION;
import static com.studcare.constants.Security.BEARER;

@Service
public class UserService {
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private UserAdapter userAdapter;
	@Autowired
	private JwtService jwtService;

	public UserRegisterResponseDTO register(UserDTO userDTO) throws StudCareValidationException {
		User user = userAdapter.adapt(userDTO);
		UserRegisterResponseDTO registerResponseDTO = new UserRegisterResponseDTO();
		if (userRepository.findByEmail(user.getEmail()).isPresent()) {
			registerResponseDTO.setResponseCode(Status.FAILURE);
			registerResponseDTO.setMessage("User already exists with email: " + user.getEmail());
		}else {
			try {
				userRepository.save(user);
				registerResponseDTO.setResponseCode(Status.SUCCESS);
				registerResponseDTO.setMessage("User account created successfully for " + user.getEmail());
			} catch (Exception exception) {
				registerResponseDTO.setResponseCode(Status.FAILURE);
				registerResponseDTO.setMessage("Failed saving user to the database");
				throw new StudCareDataException("Failed saving user to the database");
			}
		}
		return registerResponseDTO;
	}

	public UserDeletionResponseDTO delete(String email) throws StudCareValidationException {
		UserDeletionResponseDTO deleteResponseDTO = new UserDeletionResponseDTO();

		Optional<User> userOptional = userRepository.findByEmail(email);
		if (userOptional.isEmpty()) {
			deleteResponseDTO.setResponseCode(Status.FAILURE);
			deleteResponseDTO.setMessage("User not found with email: " + email);
		} else {
			User user = userOptional.get();
			try {
				userRepository.delete(user);
				deleteResponseDTO.setResponseCode(Status.SUCCESS);
				deleteResponseDTO.setMessage("User account deleted successfully for " + email);
			} catch (Exception exception) {
				deleteResponseDTO.setResponseCode(Status.FAILURE);
				deleteResponseDTO.setMessage("Failed deleting user from the database");
				throw new StudCareDataException("Failed deleting user from the database");
			}
		}
		return deleteResponseDTO;
	}

	public UserProfileResponseDTO getUserProfile(String userEmail) throws StudCareValidationException {
		UserProfileResponseDTO profileResponseDTO = new UserProfileResponseDTO();
		Optional<User> userOptional = userRepository.findByEmail(userEmail);
		if (userOptional.isEmpty()) {
			profileResponseDTO.setResponseCode(Status.FAILURE);
			profileResponseDTO.setMessage("No user with email: " + userEmail);
		}else {
			User user = userOptional.get();
			profileResponseDTO.setResponseCode(Status.SUCCESS);
			profileResponseDTO.setMessage("User profile retrieved successfully");
			profileResponseDTO.setUserDTO(userAdapter.adapt(user));
		}
		return profileResponseDTO;
	}

	public LogoutResponseDTO logout(LogoutRequestDTO logoutRequestDTO) throws StudCareValidationException {
		LogoutResponseDTO logoutResponseDTO = new LogoutResponseDTO();
		Optional<User> userOptional = userRepository.findByEmail(logoutRequestDTO.getEmail());

		if (userOptional.isEmpty()) {
			logoutResponseDTO.setResponseCode(Status.FAILURE);
			logoutResponseDTO.setMessage("No user found with email: " + logoutRequestDTO.getEmail());
			throw new StudCareValidationException("No user found with email: " + logoutRequestDTO.getEmail());
		} else {
			User user = userOptional.get();

			try {
				String tokenToInvalidate = getTokenFromRequest(logoutRequestDTO.getHeaders());
				boolean isTokenInvalid = jwtService.invalidateToken(user, tokenToInvalidate);

				if (isTokenInvalid) {
					logoutResponseDTO.setResponseCode(Status.SUCCESS);
					logoutResponseDTO.setMessage("User logged out successfully");
				} else {
					logoutResponseDTO.setResponseCode(Status.FAILURE);
					logoutResponseDTO.setMessage("Failed to invalidate token");
				}
			} catch (UsernameNotFoundException e) {
				logoutResponseDTO.setResponseCode(Status.FAILURE);
				logoutResponseDTO.setMessage("User not found");
			}
		}
		return logoutResponseDTO;
	}

	private String getTokenFromRequest(Map<String, String> headers) {
		String tokenToInvalidate = headers.get(AUTHORIZATION);
		if (tokenToInvalidate.startsWith(BEARER)) {
			return tokenToInvalidate.substring(7);
		}
		return null;
	}
}

