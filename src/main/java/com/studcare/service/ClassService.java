package com.studcare.service;

import com.studcare.adapter.AddStudentRequestAdapter;
import com.studcare.adapter.AddSubjectsToClassRequestAdapter;
import com.studcare.adapter.ClassRequestAdapter;
import com.studcare.adapter.ResponseAdapter;
import com.studcare.constants.Status;
import com.studcare.data.jpa.adaptor.SchoolClassAdapter;
import com.studcare.data.jpa.adaptor.SubjectAdapter;
import com.studcare.data.jpa.adaptor.UserAdapter;
import com.studcare.data.jpa.dto.SchoolClassDTO;
import com.studcare.data.jpa.dto.StudentDTO;
import com.studcare.data.jpa.dto.SubjectDTO;
import com.studcare.data.jpa.dto.SubjectTeacherDTO;
import com.studcare.data.jpa.entity.ClassSubjectAssignment;
import com.studcare.data.jpa.entity.SchoolClass;
import com.studcare.data.jpa.entity.Student;
import com.studcare.data.jpa.entity.StudentSubjectEnrollment;
import com.studcare.data.jpa.entity.Subject;
import com.studcare.data.jpa.entity.SubjectResult;
import com.studcare.data.jpa.entity.TermResult;
import com.studcare.data.jpa.entity.User;
import com.studcare.data.jpa.repository.ClassSubjectAssignmentRepository;
import com.studcare.data.jpa.repository.SchoolClassRepository;
import com.studcare.data.jpa.repository.StudentRepository;
import com.studcare.data.jpa.repository.StudentSubjectEnrollmentRepository;
import com.studcare.data.jpa.repository.SubjectRepository;
import com.studcare.data.jpa.repository.SubjectResultRepository;
import com.studcare.data.jpa.repository.TermResultRepository;
import com.studcare.data.jpa.repository.UserRepository;
import com.studcare.exception.StudCareDataException;
import com.studcare.exception.StudCareRuntimeException;
import com.studcare.exception.StudCareValidationException;
import com.studcare.model.AddStudentsRequestDTO;
import com.studcare.model.AddSubjectsToClassRequestDTO;
import com.studcare.model.ClassDetailsDTO;
import com.studcare.model.ClassRequestDTO;
import com.studcare.model.ClassResultsDTO;
import com.studcare.model.ClassTeacherDetailsDTO;
import com.studcare.model.HttpRequestData;
import com.studcare.model.HttpResponseData;
import com.studcare.model.ResponseDTO;
import com.studcare.model.StudentResultsDTO;
import com.studcare.model.SubjectResultDTO;
import com.studcare.model.UserDTO;
import com.studcare.validator.ClassValidator;
import jdk.jfr.Label;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.studcare.util.CommonUtils.createResponseEntity;

@Slf4j @Service public class ClassService {

	@Autowired private SchoolClassRepository schoolClassRepository;
	@Autowired private StudentRepository studentRepository;
	@Autowired private ClassRequestAdapter classRequestAdapter;
	@Autowired private AddStudentRequestAdapter addStudentRequestAdapter;
	@Autowired private ResponseAdapter classResponseAdapter;
	@Autowired @Lazy private SchoolClassAdapter schoolClassAdapter;
	@Autowired private AddSubjectsToClassRequestAdapter addSubjectsToClassRequestAdapter;

	@Autowired private ClassValidator classValidator;

	@Autowired private SubjectAdapter subjectAdapter;
	@Autowired private UserRepository userRepository;
	@Autowired private SubjectRepository subjectRepository;
	@Autowired private ClassSubjectAssignmentRepository classSubjectAssignmentRepository;
	@Autowired private StudentSubjectEnrollmentRepository studentSubjectEnrollmentRepository;
	@Autowired private UserAdapter userAdapter;

	@Autowired private ResponseAdapter responseAdapter;

	@Autowired private TermResultRepository termResultRepository;

	@Autowired private SubjectResultRepository subjectResultRepository;

	public ResponseEntity<Object> createClass(HttpRequestData httpRequestData) {
		log.info("AccountService.class() initiated");
		ResponseEntity<Object> responseEntity;
		ResponseDTO classResponseDTO;
		HttpResponseData httpResponseData = new HttpResponseData();
		try {
			ClassRequestDTO classRequestDTO = classRequestAdapter.adapt(httpRequestData);
			classValidator.validate(classRequestDTO);
			User classTeacher = userRepository.findByEmail(classRequestDTO.getClassTeacherEmail())
					.orElseThrow(() -> new StudCareValidationException("Class teacher not found"));

			SchoolClassDTO schoolClassDTO = classRequestDTO.getSchoolClassDTO();
			UserDTO classTeacherDTO = userAdapter.adapt(classTeacher);
			schoolClassDTO.setClassTeacher(classTeacherDTO);
			classResponseDTO = addClass(classRequestDTO.getSchoolClassDTO());
			httpResponseData = classResponseAdapter.adapt(classResponseDTO);
			responseEntity = createResponseEntity(httpResponseData);
			log.info("AccountService.class()  finished for {}", classRequestDTO.getSchoolClassDTO().getClassName());
		} catch (StudCareValidationException exception) {
			httpResponseData.setHttpStatus(HttpStatus.BAD_REQUEST);
			responseEntity = createResponseEntity(httpResponseData);
			log.error("AccountService.class()  an validation error occurred while processing the request", exception);
		} catch (StudCareRuntimeException exception) {
			httpResponseData.setHttpStatus(HttpStatus.INTERNAL_SERVER_ERROR);
			responseEntity = createResponseEntity(httpResponseData);
			log.error("AccountService.class()  an runtime error occurred while processing the request", exception);
		} catch (Exception exception) {
			httpResponseData.setHttpStatus(HttpStatus.INTERNAL_SERVER_ERROR);
			responseEntity = createResponseEntity(httpResponseData);
			log.error("AccountService.class()  an error occurred while processing the request", exception);
		}
		return responseEntity;
	}

	public ResponseEntity<Object> addStudents(HttpRequestData httpRequestData) {
		log.info("ClassService.addStudents() initiated");
		ResponseEntity<Object> responseEntity;
		ResponseDTO responseDTO;
		HttpResponseData httpResponseData = new HttpResponseData();

		try {
			AddStudentsRequestDTO addStudentsRequestDTO = addStudentRequestAdapter.adapt(httpRequestData);
			responseDTO = addStudentsToClass(addStudentsRequestDTO.getAddStudentsDTO().getClassId(), addStudentsRequestDTO.getAddStudentsDTO().getStudents());
			httpResponseData = classResponseAdapter.adapt(responseDTO);
			responseEntity = createResponseEntity(httpResponseData);
			log.info("ClassService.addStudents() finished successfully");
		} catch (StudCareValidationException exception) {
			httpResponseData.setHttpStatus(HttpStatus.BAD_REQUEST);
			responseEntity = createResponseEntity(httpResponseData);
			log.error("ClassService.addStudents() a validation error occurred while processing the request", exception);
		} catch (StudCareRuntimeException exception) {
			httpResponseData.setHttpStatus(HttpStatus.INTERNAL_SERVER_ERROR);
			responseEntity = createResponseEntity(httpResponseData);
			log.error("ClassService.addStudents() a runtime error occurred while processing the request", exception);
		} catch (Exception exception) {
			httpResponseData.setHttpStatus(HttpStatus.INTERNAL_SERVER_ERROR);
			responseEntity = createResponseEntity(httpResponseData);
			log.error("ClassService.addStudents() an error occurred while processing the request", exception);
		}

		return responseEntity;
	}

	private ResponseDTO addClass(SchoolClassDTO schoolClassDTO) {
		SchoolClass schoolClass = schoolClassAdapter.adapt(schoolClassDTO);
		ResponseDTO responseDTO = new ResponseDTO();
		if (schoolClassRepository.findByClassName(schoolClass.getClassName()).isPresent()) {
			responseDTO.setResponseCode(Status.FAILURE);
			responseDTO.setMessage("Class already exists with name: " + schoolClass.getClassName());
		} else {
			try {
				schoolClassRepository.save(schoolClass);
				responseDTO.setResponseCode(Status.SUCCESS);
				responseDTO.setMessage("Class created successfully for " + schoolClass.getClassName());
			} catch (Exception exception) {
				throw new StudCareDataException("Failed saving class to the database");
			}
		}
		return responseDTO;
	}

	private ResponseDTO addStudentsToClass(Long classId, List<String> studentEmails) {
		ResponseDTO responseDTO = new ResponseDTO();
		Optional<SchoolClass> optionalSchoolClass = schoolClassRepository.findById(classId);
		if (optionalSchoolClass.isPresent()) {
			SchoolClass schoolClass = optionalSchoolClass.get();
			List<Student> students = studentRepository.findByUser_EmailIn(studentEmails);
			try {
				for (Student student : students) {
					student.setSchoolClass(schoolClass);
					studentRepository.save(student);

					// Map students to subjects taught in the class
					List<ClassSubjectAssignment> classSubjects = classSubjectAssignmentRepository.findBySchoolClass(schoolClass);
					for (ClassSubjectAssignment classSubject : classSubjects) {
						StudentSubjectEnrollment enrollment = new StudentSubjectEnrollment();
						enrollment.setStudent(student);
						enrollment.setSubject(classSubject.getSubject());
						enrollment.setSchoolClass(schoolClass);
						studentSubjectEnrollmentRepository.save(enrollment);
					}
				}

				responseDTO.setResponseCode(Status.SUCCESS);
				responseDTO.setMessage("Students added to the class and mapped to subjects successfully");
			} catch (Exception exception) {
				throw new StudCareDataException("Failed to add students to the class and map to subjects");
			}
		} else {
			responseDTO.setResponseCode(Status.FAILURE);
		}

		return responseDTO;
	}

	public ResponseEntity<Object> addSubjectsToClass(HttpRequestData httpRequestData) {
		log.info("ClassService.addSubjectsToClass() initiated");
		ResponseEntity<Object> responseEntity;
		HttpResponseData httpResponseData = new HttpResponseData();
		try {
			AddSubjectsToClassRequestDTO addSubjectsToClassRequestDTO = addSubjectsToClassRequestAdapter.adapt(httpRequestData);
			ResponseDTO responseDTO = addSubjectsAndTeachersToClass(addSubjectsToClassRequestDTO);
			httpResponseData = classResponseAdapter.adapt(responseDTO);
			responseEntity = createResponseEntity(httpResponseData);
			log.info("ClassService.addSubjectsToClass() finished successfully");
		} catch (StudCareValidationException exception) {
			httpResponseData.setHttpStatus(HttpStatus.BAD_REQUEST);
			responseEntity = createResponseEntity(httpResponseData);
			log.error("ClassService.addSubjectsToClass() a validation error occurred", exception);
		} catch (Exception exception) {
			httpResponseData.setHttpStatus(HttpStatus.INTERNAL_SERVER_ERROR);
			responseEntity = createResponseEntity(httpResponseData);
			log.error("ClassService.addSubjectsToClass() an error occurred", exception);
		}

		return responseEntity;
	}

	private ResponseDTO addSubjectsAndTeachersToClass(AddSubjectsToClassRequestDTO subjectTeachersRequest) {
		// This is correct, but you might want to add a check to ensure no duplicate assignments
		SchoolClass schoolClass = schoolClassRepository.findByClassName(subjectTeachersRequest.getClassName())
				.orElseThrow(() -> new StudCareValidationException("Class not found"));

		for (SubjectTeacherDTO dto : subjectTeachersRequest.getSubjectTeachers()) {
			Subject subject = subjectRepository.findById(dto.getSubjectId()).orElseThrow(() -> new StudCareValidationException("Subject not found"));
			User teacher = userRepository.findById(dto.getTeacherId()).orElseThrow(() -> new StudCareValidationException("Teacher not found"));

			// Check if assignment already exists
			if (classSubjectAssignmentRepository.existsBySchoolClassAndSubject(schoolClass, subject)) {
				throw new StudCareValidationException("Subject already assigned to this class");
			}

			ClassSubjectAssignment assignment = new ClassSubjectAssignment();
			ClassSubjectAssignment.ClassSubjectAssignmentKey key = new ClassSubjectAssignment.ClassSubjectAssignmentKey();
			key.setClassID(schoolClass.getClassID());
			key.setSubjectID(subject.getSubjectID());
			assignment.setId(key);
			assignment.setSchoolClass(schoolClass);
			assignment.setSubject(subject);
			assignment.setTeacher(teacher);
			classSubjectAssignmentRepository.save(assignment);
		}

		ResponseDTO responseDTO = new ResponseDTO();
		responseDTO.setResponseCode(Status.SUCCESS);
		responseDTO.setMessage("Subjects and teachers added to the class successfully");
		return responseDTO;
	}

	public ResponseEntity<Object> getStudentsInClass(Long classId) {
		log.info("ClassService.getStudentsInClass() initiated");
		ResponseEntity<Object> responseEntity;
		HttpResponseData httpResponseData = new HttpResponseData();
		try {
			SchoolClass schoolClass = schoolClassRepository.findById(classId).orElseThrow(() -> new StudCareValidationException("Class not found"));
			List<Student> students = studentRepository.findBySchoolClass(schoolClass);

			ResponseDTO responseDTO = new ResponseDTO();
			responseDTO.setResponseCode(Status.SUCCESS);
			responseDTO.setMessage("Students in class retrieved successfully");
			responseDTO.setData(students);

			httpResponseData = classResponseAdapter.adapt(responseDTO);
			responseEntity = createResponseEntity(httpResponseData);
			log.info("ClassService.getStudentsInClass() finished successfully");
		} catch (StudCareValidationException exception) {
			httpResponseData.setHttpStatus(HttpStatus.BAD_REQUEST);
			responseEntity = createResponseEntity(httpResponseData);
			log.error("ClassService.getStudentsInClass() a validation error occurred", exception);
		} catch (Exception exception) {
			httpResponseData.setHttpStatus(HttpStatus.INTERNAL_SERVER_ERROR);
			responseEntity = createResponseEntity(httpResponseData);
			log.error("ClassService.getStudentsInClass() an error occurred", exception);
		}
		return responseEntity;
	}

	public ResponseEntity<Object> getClassesForTeacherAndSubject(Long teacherId, Long subjectId) {
		log.info("ClassService.getClassesForTeacherAndSubject() initiated for teacher ID: {} and subject ID: {}", teacherId, subjectId);
		ResponseEntity<Object> responseEntity;
		HttpResponseData httpResponseData = new HttpResponseData();

		try {
			User teacher = userRepository.findById(teacherId).orElseThrow(() -> new StudCareValidationException("Teacher not found"));
			Subject subject = subjectRepository.findById(subjectId).orElseThrow(() -> new StudCareValidationException("Subject not found"));

			List<ClassSubjectAssignment> assignments = classSubjectAssignmentRepository.findByTeacherAndSubject(teacher, subject);

			List<SchoolClassDTO> classDTOs = assignments.stream().map(assignment -> schoolClassAdapter.adapt(assignment.getSchoolClass()))
					.collect(Collectors.toList());

			ResponseDTO responseDTO = new ResponseDTO();
			responseDTO.setResponseCode(Status.SUCCESS);
			responseDTO.setMessage("Teachers for subject retrieved successfully");
			responseDTO.setData(classDTOs);
			httpResponseData = responseAdapter.adapt(responseDTO);
			responseEntity = createResponseEntity(httpResponseData);
		} catch (StudCareValidationException exception) {
			log.error("ClassService.getClassesForTeacherAndSubject() validation error", exception);
			httpResponseData.setHttpStatus(HttpStatus.BAD_REQUEST);
			responseEntity = createResponseEntity(httpResponseData);
		} catch (Exception exception) {
			log.error("ClassService.getClassesForTeacherAndSubject() an error occurred", exception);
			httpResponseData.setHttpStatus(HttpStatus.INTERNAL_SERVER_ERROR);
			responseEntity = createResponseEntity(httpResponseData);
		}

		return responseEntity;

	}

	public ResponseEntity<Object> getClassDetails(Long classId) {
		log.info("ClassService.getClassDetails() initiated for class ID: {}", classId);
		ResponseEntity<Object> responseEntity;
		HttpResponseData httpResponseData = new HttpResponseData();

		try {
			SchoolClass schoolClass = schoolClassRepository.findById(classId).orElseThrow(() -> new StudCareValidationException("Class not found"));

			// Get students
			List<Student> students = studentRepository.findBySchoolClass(schoolClass);

			// Get subjects and teachers
			List<ClassSubjectAssignment> assignments = classSubjectAssignmentRepository.findBySchoolClass(schoolClass);

			Map<Subject, User> subjectTeacherMap = assignments.stream()
					.collect(Collectors.toMap(ClassSubjectAssignment::getSubject, ClassSubjectAssignment::getTeacher));

			ClassDetailsDTO classDetails = new ClassDetailsDTO();
			classDetails.setClassId(schoolClass.getClassID());
			classDetails.setClassName(schoolClass.getClassName());
			classDetails.setClassTeacher(userAdapter.adapt(schoolClass.getClassTeacher()));
			classDetails.setStudents(students.stream().map(this::adaptStudent).collect(Collectors.toList()));
			classDetails.setSubjectTeachers(subjectTeacherMap.entrySet().stream()
					.map(entry -> new SubjectTeacherDTO(subjectAdapter.adapt(entry.getKey()), userAdapter.adapt(entry.getValue())))
					.collect(Collectors.toList()));

			ResponseDTO responseDTO = new ResponseDTO();
			responseDTO.setResponseCode(Status.SUCCESS);
			responseDTO.setMessage("Class details retrieved successfully");
			responseDTO.setData(Collections.singletonList(classDetails));

			httpResponseData = responseAdapter.adapt(responseDTO);
			responseEntity = createResponseEntity(httpResponseData);
		} catch (StudCareValidationException exception) {
			log.error("ClassService.getClassDetails() validation error", exception);
			httpResponseData.setHttpStatus(HttpStatus.BAD_REQUEST);
			responseEntity = createResponseEntity(httpResponseData);
		} catch (Exception exception) {
			log.error("ClassService.getClassDetails() an error occurred", exception);
			httpResponseData.setHttpStatus(HttpStatus.INTERNAL_SERVER_ERROR);
			responseEntity = createResponseEntity(httpResponseData);
		}

		return responseEntity;
	}

	private StudentDTO adaptStudent(Student student) {
		StudentDTO dto = new StudentDTO();
		dto.setUserDTO(userAdapter.adapt(student.getUser()));
		return dto;
	}

	public ResponseEntity<Object> getClassResults(Long classId, Integer academicYear, Integer termNumber) {
		log.info("ClassService.getClassResults() initiated for class ID: {}, academic year: {}, term: {}", classId, academicYear, termNumber);
		ResponseEntity<Object> responseEntity;
		HttpResponseData httpResponseData = new HttpResponseData();
		try {
			SchoolClass schoolClass = schoolClassRepository.findById(classId).orElseThrow(() -> new StudCareValidationException("Class not found"));
			List<Student> students = studentRepository.findBySchoolClass(schoolClass);
			ClassResultsDTO classResultsDTO = new ClassResultsDTO();
			classResultsDTO.setClassId(schoolClass.getClassID());
			classResultsDTO.setClassName(schoolClass.getClassName());
			List<StudentResultsDTO> studentResultsDTOs = new ArrayList<>();
			for (Student student : students) {
				StudentResultsDTO studentResultsDTO = new StudentResultsDTO();
				studentResultsDTO.setStudentId(student.getStudentId());
				studentResultsDTO.setStudentName(student.getUser().getUsername());
				TermResult termResult = termResultRepository.findByStudentAndAcademicYearAndTermNumber(student, academicYear, termNumber).orElse(null);
				List<SubjectResultDTO> subjectResultDTOs = new ArrayList<>();
				if (termResult != null) {
					List<SubjectResult> subjectResults = subjectResultRepository.findByTermResult(termResult);
					for (SubjectResult subjectResult : subjectResults) {
						SubjectResultDTO subjectResultDTO = new SubjectResultDTO();
						subjectResultDTO.setSubjectId(subjectResult.getSubject().getSubjectID());
						subjectResultDTO.setSubjectName(subjectResult.getSubject().getSubjectName());
						subjectResultDTO.setMarks(subjectResult.getMarks());
						subjectResultDTO.setGrade(subjectResult.getGrade());
						subjectResultDTO.setTeacherNote(subjectResult.getTeacherNote());
						subjectResultDTOs.add(subjectResultDTO);
					}
				}
				studentResultsDTO.setSubjectResults(subjectResultDTOs);
				studentResultsDTOs.add(studentResultsDTO);
			}
			classResultsDTO.setStudentResults(studentResultsDTOs);
			ResponseDTO responseDTO = new ResponseDTO();
			responseDTO.setResponseCode(Status.SUCCESS);
			responseDTO.setMessage("Class results retrieved successfully");
			responseDTO.setData(Collections.singletonList(classResultsDTO));
			httpResponseData = responseAdapter.adapt(responseDTO);
			responseEntity = createResponseEntity(httpResponseData);
		} catch (StudCareValidationException exception) {
			log.error("ClassService.getClassResults() validation error", exception);
			httpResponseData.setHttpStatus(HttpStatus.BAD_REQUEST);
			responseEntity = createResponseEntity(httpResponseData);
		} catch (Exception exception) {
			log.error("ClassService.getClassResults() an error occurred", exception);
			httpResponseData.setHttpStatus(HttpStatus.INTERNAL_SERVER_ERROR);
			responseEntity = createResponseEntity(httpResponseData);
		}

		return responseEntity;
	}
}
