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
	@GetMapping("/{teacher}/subjects")
	public ResponseEntity<Object> getTeacherSubjectsAndClasses(@PathVariable String teacher) {
		try {
			log.info("ClassController.getTeacherSubjectsAndClasses()[GET] process initiated for teacher ID: {}", teacher);
			return teacherService.getTeacherSubjectsAndClasses(teacher);
		} catch (Exception exception) {
			log.error("ClassController.getTeacherSubjectsAndClasses()[GET] unexpected error occurred", exception);
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/{teacher}/class")
	public ResponseEntity<Object> getClassTeacherDetails(@PathVariable String teacher) {
		try {
			log.info("ClassController.getClassTeacherDetails()[GET] process initiated for user ID: {}", teacher);
			return teacherService.getClassTeacherDetails(teacher);
		} catch (Exception exception) {
			log.error("ClassController.getClassTeacherDetails()[GET] unexpected error occurred", exception);
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	@PostMapping("/{teacher}/subject/result")
	public ResponseEntity<Object> addSubjectResults(
			@PathVariable String teacher,
			@RequestBody SubjectResultsRequestDTO resultsRequestDTO) {
		try {
			log.info("ClassController.addSubjectResults()[POST] process initiated for teacher ID: {}", teacher);
			return teacherService.addSubjectResults(resultsRequestDTO, teacher);
		} catch (Exception exception) {
			log.error("ClassController.addSubjectResults()[POST] unexpected error occurred", exception);
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
