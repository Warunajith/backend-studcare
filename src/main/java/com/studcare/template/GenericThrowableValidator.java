package com.studcare.template;

import com.studcare.exception.StudCareValidationException;

public interface GenericThrowableValidator<T> {
	void validate(T requestDTO) throws StudCareValidationException;
}
