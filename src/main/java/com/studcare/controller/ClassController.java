package com.studcare.controller;

import com.studcare.model.HttpRequestData;
import com.studcare.service.ClassService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/class")
public class ClassController {
	@Autowired
	private ClassService classService;

	@PostMapping("/create")
	public ResponseEntity<Object> createClass(
			@RequestHeader Map<String, String> headers,
			@RequestParam Map<String, String> queryParams,
			@RequestBody String requestBody) {
		try {
			log.info("ClassController.createClass()[POST] process initiated");
			HttpRequestData httpRequestData = new HttpRequestData(headers, queryParams, requestBody);
			return classService.createClass(httpRequestData);
		} catch (Exception exception) {
			log.error("ClassController.createClass()[POST] unexpected error occurred", exception);
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PostMapping("/{className}/add/students")
	public ResponseEntity<Object> addStudents(
			@RequestHeader Map<String, String> headers,
			@RequestParam Map<String, String> queryParams,
			@RequestBody String requestBody) {
		try {
			log.info("ClassController.addStudents()[POST] process initiated");
			HttpRequestData httpRequestData = new HttpRequestData(headers, queryParams, requestBody);
			return classService.addStudents(httpRequestData);
		} catch (Exception exception) {
			log.error("ClassController.addStudents()[POST] unexpected error occurred", exception);
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PostMapping("/{className}/add/subjects")
	public ResponseEntity<Object> addSubjects(
			@PathVariable String className,
			@RequestHeader Map<String, String> headers,
			@RequestParam Map<String, String> queryParams,
			@RequestBody String requestBody) {
		try {
			log.info("ClassController.addSubjects()[POST] process initiated");
			HttpRequestData httpRequestData = new HttpRequestData(className, headers, queryParams, requestBody);
			return classService.addSubjectsToClass(httpRequestData);
		} catch (Exception exception) {
			log.error("ClassController.addSubjects()[POST] unexpected error occurred", exception);
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/{classId}/students")
	public ResponseEntity<Object> getStudentsInClass(@PathVariable Long classId) {
		try {
			log.info("ClassController.getStudentsInClass()[GET] process initiated");
			return classService.getStudentsInClass(classId);
		} catch (Exception exception) {
			log.error("ClassController.getStudentsInClass()[GET] unexpected error occurred", exception);
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/teacher/{teacherId}/subject/{subjectId}")
	public ResponseEntity<Object> getClassesForTeacherAndSubject(
			@PathVariable Long teacherId,
			@PathVariable Long subjectId) {
		try {
			log.info("ClassController.getClassesForTeacherAndSubject()[GET] process initiated for teacher ID: {} and subject ID: {}", teacherId, subjectId);
			return classService.getClassesForTeacherAndSubject(teacherId, subjectId);
		} catch (Exception exception) {
			log.error("ClassController.getClassesForTeacherAndSubject()[GET] unexpected error occurred", exception);
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/{classId}/details")
	public ResponseEntity<Object> getClassDetails(@PathVariable Long classId) {
		try {
			log.info("ClassController.getClassDetails()[GET] process initiated for class ID: {}", classId);
			return classService.getClassDetails(classId);
		} catch (Exception exception) {
			log.error("ClassController.getClassDetails()[GET] unexpected error occurred", exception);
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	@GetMapping("/{classId}/results")
	public ResponseEntity<Object> getClassResults(
			@PathVariable Long classId,
			@RequestParam Integer academicYear,
			@RequestParam Integer termNumber) {
		try {
			log.info("ClassController.getClassResults()[GET] process initiated for class ID: {}", classId);
			return classService.getClassResults(classId, academicYear, termNumber);
		} catch (Exception exception) {
			log.error("ClassController.getClassResults()[GET] unexpected error occurred", exception);
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

}