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
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "CLASS_SUBJECT_ASSIGNMENT")
public class ClassSubjectAssignment {
	@EmbeddedId
	private ClassSubjectAssignmentKey id;

	@ManyToOne
	@MapsId("classID")
	@JoinColumn(name = "ClassID")
	private SchoolClass schoolClass;

	@ManyToOne
	@MapsId("subjectID")
	@JoinColumn(name = "SubjectID")
	private Subject subject;

	@ManyToOne
	@JoinColumn(name = "TeacherID")
	private User teacher;

	@CreationTimestamp
	private LocalDateTime createdTimestamp;

	@UpdateTimestamp
	private LocalDateTime modifiedTimestamp;

	@Embeddable
	@Data
	public static class ClassSubjectAssignmentKey implements Serializable {
		@Column(name = "ClassID")
		private Long classID;

		@Column(name = "SubjectID")
		private Long subjectID;
	}
}
