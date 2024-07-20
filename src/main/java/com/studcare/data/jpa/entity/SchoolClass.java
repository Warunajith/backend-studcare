package com.studcare.data.jpa.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.JoinColumn;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Entity
@Data
@Table(name = "SCHOOL_CLASS")
public class SchoolClass {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ClassID")
	private Long classID;

	@Column(name = "ClassName")
	private String className;

	@OneToOne
	@JoinColumn(name = "ClassTeacherID", unique = true)
	private User classTeacher;

	@ManyToMany
	@JoinTable(
			name = "CLASS_SUBJECT",
			joinColumns = @JoinColumn(name = "ClassID"),
			inverseJoinColumns = @JoinColumn(name = "SubjectID")
	)
	private List<Subject> subjects;

	@CreationTimestamp
	private LocalDateTime createdTimestamp;

	@UpdateTimestamp
	private LocalDateTime modifiedTimestamp;
}