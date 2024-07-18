package com.studcare.data.jpa.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
@Table(name = "TERM_RESULT")
public class TermResult {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "TermResultID")
	private Long termResultId;

	@ManyToOne
	@JoinColumn(name = "StudentID")
	private Student student;

	@Column(name = "TermNumber")
	private Integer termNumber;

	@Column(name = "AcademicYear")
	private Integer academicYear;

	@Column(name = "ClassRank")
	private Integer classRank;

	@Column(name = "ClassTeacherNote")
	private String classTeacherNote;

	@OneToMany(mappedBy = "termResult", cascade = CascadeType.ALL)
	private List<SubjectResult> subjectResults;

	@CreationTimestamp
	private LocalDateTime createdTimestamp;

	@UpdateTimestamp
	private LocalDateTime modifiedTimestamp;

}