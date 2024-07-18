package com.studcare.controller;

import com.studcare.model.SubjectResultsRequestDTO;
import com.studcare.service.TeacherService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/teacher")
public class TeacherController {

	@Autowired
	private TeacherService teacherService;
	@GetMapping("/{teacherId}/subjects")
	public ResponseEntity<Object> getTeacherSubjectsAndClasses(@PathVariable Long teacherId) {
		try {
			log.info("ClassController.getTeacherSubjectsAndClasses()[GET] process initiated for teacher ID: {}", teacherId);
			return teacherService.getTeacherSubjectsAndClasses(teacherId);
		} catch (Exception exception) {
			log.error("ClassController.getTeacherSubjectsAndClasses()[GET] unexpected error occurred", exception);
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/{userId}/class")
	public ResponseEntity<Object> getClassTeacherDetails(@PathVariable Long userId) {
		try {
			log.info("ClassController.getClassTeacherDetails()[GET] process initiated for user ID: {}", userId);
			return teacherService.getClassTeacherDetails(userId);
		} catch (Exception exception) {
			log.error("ClassController.getClassTeacherDetails()[GET] unexpected error occurred", exception);
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	@PostMapping("/{teacherId}/subject/results")
	public ResponseEntity<Object> addSubjectResults(
			@PathVariable Long teacherId,
			@RequestBody SubjectResultsRequestDTO resultsRequestDTO) {
		try {
			log.info("ClassController.addSubjectResults()[POST] process initiated for teacher ID: {}", teacherId);
			return teacherService.addSubjectResults(resultsRequestDTO, teacherId);
		} catch (Exception exception) {
			log.error("ClassController.addSubjectResults()[POST] unexpected error occurred", exception);
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
