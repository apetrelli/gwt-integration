package com.github.apetrelli.gwtintegration.editor.client.editor;

import java.util.Set;

import javax.validation.ConstraintViolation;

public interface ConstraintViolationDisplayer {
	void reset();

	void setConstraintViolations(Set<ConstraintViolation<?>> violations);
}
