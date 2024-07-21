package com.studcare.data.jpa.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "YEAR_END_NOTE")
public class YearEndNote {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "YearEndNoteID")
	private Long yearEndNoteId;


	@OneToOne
	@JoinColumn(name = "StudentID", unique = true)
	private User student;

	@Column(name = "AcademicYear")
	private String academicYear;

	@Column(name = "TeacherNote")
	private String teacherNote;

	@Column(name = "HostelMasterNote")
	private String hostelMasterNote;

	@CreationTimestamp
	private LocalDateTime createdTimestamp;

	@UpdateTimestamp
	private LocalDateTime modifiedTimestamp;

}