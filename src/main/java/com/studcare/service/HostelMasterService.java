package com.studcare.service;

import com.studcare.data.jpa.adaptor.MonthlyEvaluationAdapter;
import com.studcare.adapter.ResponseAdapter;
import com.studcare.constants.Status;
import com.studcare.data.jpa.adaptor.HostelMasterAdapter;
import com.studcare.data.jpa.adaptor.StudentAdapter;
import com.studcare.data.jpa.adaptor.WardAdapter;
import com.studcare.data.jpa.dto.HostelMasterDTO;
import com.studcare.data.jpa.entity.HostelMaster;
import com.studcare.data.jpa.entity.MonthlyEvaluation;
import com.studcare.data.jpa.entity.Student;
import com.studcare.data.jpa.entity.Ward;
import com.studcare.data.jpa.repository.HostelMasterRepository;
import com.studcare.data.jpa.repository.MonthlyEvaluationRepository;
import com.studcare.data.jpa.repository.StudentRepository;
import com.studcare.data.jpa.repository.UserRepository;
import com.studcare.data.jpa.repository.WardRepository;
import com.studcare.exception.StudCareValidationException;
import com.studcare.model.HttpResponseData;
import com.studcare.model.MonthlyEvaluationRequestDTO;
import com.studcare.model.ResponseDTO;
import com.studcare.model.WardDetailsDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static com.studcare.util.CommonUtils.createResponseEntity;

@Slf4j
@Service
public class HostelMasterService {

	@Autowired
	private HostelMasterRepository hostelMasterRepository;
	@Autowired
	private WardRepository wardRepository;
	@Autowired
	private StudentRepository studentRepository;
	@Autowired
	private MonthlyEvaluationRepository monthlyEvaluationRepository;
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private ResponseAdapter responseAdapter;
	@Autowired
	private WardAdapter wardAdapter;
	@Autowired
	private StudentAdapter studentAdapter;
	@Autowired
	private HostelMasterAdapter hostelMasterAdapter;
	@Autowired
	private MonthlyEvaluationAdapter monthlyEvaluationAdapter;

	public ResponseEntity<Object> getWardDetails(Long hostelMasterId) {
		log.info("HostelMasterService.getWardDetails() initiated for hostel master ID: {}", hostelMasterId);
		ResponseEntity<Object> responseEntity;
		HttpResponseData httpResponseData = new HttpResponseData();

		try {
			HostelMaster hostelMaster = hostelMasterRepository.findById(hostelMasterId)
					.orElseThrow(() -> new StudCareValidationException("Hostel Master not found"));

			Ward ward = wardRepository.findByHostelMaster(hostelMaster)
					.orElseThrow(() -> new StudCareValidationException("Ward not found for this Hostel Master"));

			List<Student> students = studentRepository.findByWard(ward);

			WardDetailsDTO wardDetailsDTO = new WardDetailsDTO();
			wardDetailsDTO.setWardDTO(wardAdapter.adapt(ward));
			wardDetailsDTO.setStudents(students.stream().map(studentAdapter::adapt).collect(Collectors.toList()));

			ResponseDTO responseDTO = new ResponseDTO();
			responseDTO.setResponseCode(Status.SUCCESS);
			responseDTO.setMessage("Ward details retrieved successfully");
			responseDTO.setData(Collections.singletonList(wardDetailsDTO));

			httpResponseData = responseAdapter.adapt(responseDTO);
			responseEntity = createResponseEntity(httpResponseData);
		} catch (StudCareValidationException exception) {
			log.error("HostelMasterService.getWardDetails() validation error", exception);
			httpResponseData.setHttpStatus(HttpStatus.BAD_REQUEST);
			responseEntity = createResponseEntity(httpResponseData);
		} catch (Exception exception) {
			log.error("HostelMasterService.getWardDetails() an error occurred", exception);
			httpResponseData.setHttpStatus(HttpStatus.INTERNAL_SERVER_ERROR);
			responseEntity = createResponseEntity(httpResponseData);
		}

		return responseEntity;
	}

	public ResponseEntity<Object> getAllHostelMasters() {
		log.info("HostelMasterService.getAllHostelMasters() initiated");
		ResponseEntity<Object> responseEntity;
		HttpResponseData httpResponseData = new HttpResponseData();

		try {
			List<HostelMaster> hostelMasters = hostelMasterRepository.findAll();
			List<HostelMasterDTO> hostelMasterDTOs = hostelMasters.stream()
					.map(hostelMasterAdapter::adapt)
					.collect(Collectors.toList());

			ResponseDTO responseDTO = new ResponseDTO();
			responseDTO.setResponseCode(Status.SUCCESS);
			responseDTO.setMessage("All Hostel Masters retrieved successfully");
			responseDTO.setData(hostelMasterDTOs);

			httpResponseData = responseAdapter.adapt(responseDTO);
			responseEntity = createResponseEntity(httpResponseData);
		} catch (Exception exception) {
			log.error("HostelMasterService.getAllHostelMasters() an error occurred", exception);
			httpResponseData.setHttpStatus(HttpStatus.INTERNAL_SERVER_ERROR);
			responseEntity = createResponseEntity(httpResponseData);
		}

		return responseEntity;
	}

	public ResponseEntity<Object> addMonthlyEvaluation(Long hostelMasterId, Long studentId, MonthlyEvaluationRequestDTO evaluationRequestDTO) {
		log.info("HostelMasterService.addMonthlyEvaluation() initiated for hostel master ID: {} and student ID: {}", hostelMasterId, studentId);
		ResponseEntity<Object> responseEntity;
		HttpResponseData httpResponseData = new HttpResponseData();

		try {
			HostelMaster hostelMaster = hostelMasterRepository.findById(hostelMasterId)
					.orElseThrow(() -> new StudCareValidationException("Hostel Master not found"));

			Student student = studentRepository.findById(studentId)
					.orElseThrow(() -> new StudCareValidationException("Student not found"));

			MonthlyEvaluation monthlyEvaluation = monthlyEvaluationAdapter.adapt(evaluationRequestDTO);
			monthlyEvaluation.setStudent(student);
			monthlyEvaluation.setHostelMasterId(hostelMaster);

			monthlyEvaluationRepository.save(monthlyEvaluation);

			ResponseDTO responseDTO = new ResponseDTO();
			responseDTO.setResponseCode(Status.SUCCESS);
			responseDTO.setMessage("Monthly evaluation added successfully");

			httpResponseData = responseAdapter.adapt(responseDTO);
			responseEntity = createResponseEntity(httpResponseData);
		} catch (StudCareValidationException exception) {
			log.error("HostelMasterService.addMonthlyEvaluation() validation error", exception);
			httpResponseData.setHttpStatus(HttpStatus.BAD_REQUEST);
			responseEntity = createResponseEntity(httpResponseData);
		} catch (Exception exception) {
			log.error("HostelMasterService.addMonthlyEvaluation() an error occurred", exception);
			httpResponseData.setHttpStatus(HttpStatus.INTERNAL_SERVER_ERROR);
			responseEntity = createResponseEntity(httpResponseData);
		}

		return responseEntity;
	}

}