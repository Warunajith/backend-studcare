package com.studcare.data.jpa.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "SUBJECT_RESULT")
public class SubjectResult {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "SubjectResultID")
	private Long subjectResultId;

	@ManyToOne
	@JoinColumn(name = "TermResultID")
	private TermResult termResult;

	@ManyToOne
	@JoinColumn(name = "SubjectID")
	private Subject subject;

	@Column(name = "Marks")
	private Integer marks;

	@Column(name = "Grade")
	private String grade;

	@Column(name = "TeacherNote")
	private String teacherNote;

	@CreationTimestamp
	private LocalDateTime createdTimestamp;

	@UpdateTimestamp
	private LocalDateTime modifiedTimestamp;

}