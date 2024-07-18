package com.studcare.constants;

public enum Status {
	SUCCESS("Success"),
	FAILURE("Failure");

	private final String description;

	Status(String description) {
		this.description = description;
	}

	public String getDescription() {
		return description;
	}
}