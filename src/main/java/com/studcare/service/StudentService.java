package com.studcare.service;

import com.studcare.adapter.ResponseAdapter;
import com.studcare.adapter.YearTermResultRequestAdapter;
import com.studcare.constants.Status;
import com.studcare.data.jpa.entity.SchoolClass;
import com.studcare.data.jpa.entity.Student;
import com.studcare.data.jpa.entity.SubjectResult;
import com.studcare.data.jpa.entity.TermResult;
import com.studcare.data.jpa.repository.SchoolClassRepository;
import com.studcare.data.jpa.repository.StudentRepository;
import com.studcare.data.jpa.repository.SubjectResultRepository;
import com.studcare.data.jpa.repository.TermResultRepository;
import com.studcare.exception.StudCareDataException;
import com.studcare.exception.StudCareValidationException;
import com.studcare.model.ClassResultsDTO;
import com.studcare.model.HttpResponseData;
import com.studcare.model.ResponseDTO;
import com.studcare.model.StudentResultsDTO;
import com.studcare.model.SubjectResultDTO;
import com.studcare.model.TermResultsDTO;
import com.studcare.model.YearResultsDTO;
import com.studcare.model.YearTermDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static com.studcare.util.CommonUtils.createResponseEntity;

@Slf4j @Service public class StudentService {

	@Autowired private SchoolClassRepository schoolClassRepository;
	@Autowired private StudentRepository studentRepository;

	@Autowired private ResponseAdapter responseAdapter;
	@Autowired private YearTermResultRequestAdapter yearTermResultRequestAdapter;
	

	@Autowired private TermResultRepository termResultRepository;

	@Autowired private SubjectResultRepository subjectResultRepository;

	public ResponseEntity<Object> getStudentResults(String studentEmail, String requestBody) {
		YearTermDTO yearTermDTO = yearTermResultRequestAdapter.adapt(requestBody);
		log.info("StudentService.getStudentResults() initiated for student ID: {}, academic year: {}, term: {}", studentEmail, yearTermDTO.getAcademicYear(), yearTermDTO.getTerm());
		ResponseEntity<Object> responseEntity;
		HttpResponseData httpResponseData = new HttpResponseData();
		try {
			Student student = studentRepository.findByUser_Email(studentEmail).orElseThrow(() -> new StudCareDataException("Student not found"));
			StudentResultsDTO studentResultsDTO = new StudentResultsDTO();
			studentResultsDTO.setStudentId(student.getStudentId());
			studentResultsDTO.setStudentName(student.getUser().getUsername());

			YearResultsDTO yearResultsDTO = new YearResultsDTO();
			yearResultsDTO.setAcademicYear(yearTermDTO.getAcademicYear());

			TermResultsDTO termResultsDTO = new TermResultsDTO();
			termResultsDTO.setTermNumber(yearTermDTO.getTerm());

			List<SubjectResultDTO> subjectResultDTOs = new ArrayList<>();
			TermResult termResult = termResultRepository.findByStudentAndAcademicYearAndTermNumber(student, yearTermDTO.getAcademicYear(), yearTermDTO.getTerm()).orElse(null);
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

			termResultsDTO.setSubjectResults(subjectResultDTOs);
			yearResultsDTO.setTermResults(Collections.singletonList(termResultsDTO));
			studentResultsDTO.setYearResults(Collections.singletonList(yearResultsDTO));

			ResponseDTO responseDTO = new ResponseDTO();
			responseDTO.setResponseCode(Status.SUCCESS);
			responseDTO.setMessage("Student results retrieved successfully");
			responseDTO.setData(Collections.singletonList(studentResultsDTO));
			httpResponseData = responseAdapter.adapt(responseDTO);
			responseEntity = createResponseEntity(httpResponseData);
		} catch (StudCareValidationException exception) {
			log.error("StudentService.getStudentResults() validation error", exception);
			httpResponseData.setHttpStatus(HttpStatus.BAD_REQUEST);
			httpResponseData.setResponseBody(exception.getMessage());
			responseEntity = createResponseEntity(httpResponseData);
		} catch (Exception exception) {
			log.error("StudentService.getStudentResults() an error occurred", exception);
			httpResponseData.setHttpStatus(HttpStatus.INTERNAL_SERVER_ERROR);
			httpResponseData.setResponseBody(exception.getMessage());
			responseEntity = createResponseEntity(httpResponseData);
		}

		return responseEntity;
	}

	public ResponseEntity<Object> getStudentYearResults(String studentEmail, String requestBody) {
		YearTermDTO yearTermDTO = yearTermResultRequestAdapter.adapt(requestBody);
		log.info("StudentService.getStudentYearResults() initiated for student ID: {}, academic year: {}", studentEmail, yearTermDTO.getAcademicYear());
		ResponseEntity<Object> responseEntity;
		HttpResponseData httpResponseData = new HttpResponseData();
		try {
			Student student = studentRepository.findByUser_Email(studentEmail).orElseThrow(() -> new StudCareDataException("Student not found"));
			StudentResultsDTO studentResultsDTO = new StudentResultsDTO();
			studentResultsDTO.setStudentId(student.getStudentId());
			studentResultsDTO.setStudentName(student.getUser().getUsername());

			YearResultsDTO yearResultsDTO = new YearResultsDTO();
			yearResultsDTO.setAcademicYear(yearTermDTO.getAcademicYear());

			List<TermResultsDTO> termResultsDTOs = new ArrayList<>();
			List<TermResult> termResults = termResultRepository.findByStudentAndAcademicYear(student, yearTermDTO.getAcademicYear()).orElseThrow(() -> new StudCareDataException("results not found"));
			for (TermResult termResult : termResults) {
				TermResultsDTO termResultsDTO = new TermResultsDTO();
				termResultsDTO.setTermNumber(termResult.getTermNumber());

				List<SubjectResultDTO> subjectResultDTOs = new ArrayList<>();
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

				termResultsDTO.setSubjectResults(subjectResultDTOs);
				termResultsDTOs.add(termResultsDTO);
			}

			yearResultsDTO.setTermResults(termResultsDTOs);
			studentResultsDTO.setYearResults(Collections.singletonList(yearResultsDTO));

			ResponseDTO responseDTO = new ResponseDTO();
			responseDTO.setResponseCode(Status.SUCCESS);
			responseDTO.setMessage("Student year results retrieved successfully");
			responseDTO.setData(Collections.singletonList(studentResultsDTO));
			httpResponseData = responseAdapter.adapt(responseDTO);
			responseEntity = createResponseEntity(httpResponseData);
		} catch (StudCareValidationException exception) {
			log.error("StudentService.getStudentYearResults() validation error", exception);
			httpResponseData.setHttpStatus(HttpStatus.BAD_REQUEST);
			httpResponseData.setResponseBody(exception.getMessage());
			responseEntity = createResponseEntity(httpResponseData);
		} catch (Exception exception) {
			log.error("StudentService.getStudentYearResults() an error occurred", exception);
			httpResponseData.setHttpStatus(HttpStatus.INTERNAL_SERVER_ERROR);
			httpResponseData.setResponseBody(exception.getMessage());
			responseEntity = createResponseEntity(httpResponseData);
		}

		return responseEntity;
	}

	public ResponseEntity<Object> getStudentAllResults(String studentEmail) {
		log.info("StudentService.getStudentAllResults() initiated for student email: {}", studentEmail);
		ResponseEntity<Object> responseEntity;
		HttpResponseData httpResponseData = new HttpResponseData();
		try {
			Student student = studentRepository.findByUser_Email(studentEmail).orElseThrow(() -> new StudCareDataException("Student not found"));
			StudentResultsDTO studentResultsDTO = new StudentResultsDTO();
			studentResultsDTO.setStudentId(student.getStudentId());
			studentResultsDTO.setStudentName(student.getUser().getUsername());

			List<YearResultsDTO> yearResultsDTOs = new ArrayList<>();
			List<TermResult> termResults = termResultRepository.findByStudent(student).orElseThrow(() -> new StudCareDataException("results not found"));
			for (TermResult termResult : termResults) {
				YearResultsDTO yearResultsDTO = yearResultsDTOs.stream()
						.filter(yr -> yr.getAcademicYear().equals(termResult.getAcademicYear()))
						.findFirst()
						.orElseGet(() -> {
							YearResultsDTO newYearResultsDTO = new YearResultsDTO();
							newYearResultsDTO.setAcademicYear(termResult.getAcademicYear());
							newYearResultsDTO.setTermResults(new ArrayList<>());
							yearResultsDTOs.add(newYearResultsDTO);
							return newYearResultsDTO;
						});

				TermResultsDTO termResultsDTO = new TermResultsDTO();
				termResultsDTO.setTermNumber(termResult.getTermNumber());

				List<SubjectResultDTO> subjectResultDTOs = new ArrayList<>();
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

				termResultsDTO.setSubjectResults(subjectResultDTOs);
				yearResultsDTO.getTermResults().add(termResultsDTO);
			}

			studentResultsDTO.setYearResults(yearResultsDTOs);
			ResponseDTO responseDTO = new ResponseDTO();
			responseDTO.setResponseCode(Status.SUCCESS);
			responseDTO.setMessage("Student results retrieved successfully");
			responseDTO.setData(Collections.singletonList(studentResultsDTO));
			httpResponseData = responseAdapter.adapt(responseDTO);
			responseEntity = createResponseEntity(httpResponseData);
		} catch (StudCareValidationException exception) {
			log.error("StudentService.getStudentAllResults() validation error", exception);
			httpResponseData.setHttpStatus(HttpStatus.BAD_REQUEST);
			httpResponseData.setResponseBody(exception.getMessage());
			responseEntity = createResponseEntity(httpResponseData);
		} catch (Exception exception) {
			log.error("StudentService.getStudentAllResults() an error occurred", exception);
			httpResponseData.setHttpStatus(HttpStatus.INTERNAL_SERVER_ERROR);
			httpResponseData.setResponseBody(exception.getMessage());
			responseEntity = createResponseEntity(httpResponseData);
		}

		return responseEntity;
	}

}
