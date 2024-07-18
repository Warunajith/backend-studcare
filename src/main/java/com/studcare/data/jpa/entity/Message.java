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

import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "MESSAGE")
public class Message {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "MessageID")
	private Long messageId;

	@ManyToOne
	@JoinColumn(name = "SenderID")
	private User sender;

	@ManyToOne
	@JoinColumn(name = "ReceiverID")
	private User receiver;

	@Column(name = "MessageBody")
	private String messageBody;

	@Column(name = "SentAt")
	private LocalDateTime sentAt;

}