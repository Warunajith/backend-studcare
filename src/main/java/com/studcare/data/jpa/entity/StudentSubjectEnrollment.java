package com.studcare.data.jpa.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;
import lombok.Data;

import java.io.Serializable;


@Entity
@Data
@Table(name = "STUDENT_SUBJECT_ENROLLMENT")
public class StudentSubjectEnrollment {
	@EmbeddedId
	private StudentSubjectEnrollmentKey id;

	@ManyToOne
	@MapsId("studentID")
	@JoinColumn(name = "StudentID")
	private Student student;

	@ManyToOne
	@MapsId("subjectID")
	@JoinColumn(name = "SubjectID")
	private Subject subject;

	@ManyToOne
	@JoinColumn(name = "ClassID")
	private SchoolClass schoolClass;

	@Embeddable
	@Data
	public static class StudentSubjectEnrollmentKey implements Serializable {
		@Column(name = "StudentID")
		private Long studentID;

		@Column(name = "SubjectID")
		private Long subjectID;
	}
}