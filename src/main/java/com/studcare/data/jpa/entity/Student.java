package com.studcare.data.jpa.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.JoinColumn;
import lombok.Data;

import java.util.Set;

@Entity
@Data
@Table(name = "STUDENT")
public class  Student {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "StudentID")
	private Long studentId;

	@ManyToOne
	@JoinColumn(name = "ClassID")
	private SchoolClass schoolClass;

	@ManyToOne
	@JoinColumn(name = "WardID")
	private Ward ward;

	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "UserID", unique = true)
	private User user;
	@ManyToMany
	@JoinTable(
			name = "STUDENT_SUBJECT_ENROLLMENT",
			joinColumns = @JoinColumn(name = "StudentID"),
			inverseJoinColumns = @JoinColumn(name = "SubjectID")
	)
	private Set<Subject> enrolledSubjects;

}
