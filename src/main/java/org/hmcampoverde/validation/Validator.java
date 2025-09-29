package org.hmcampoverde.validation;

import org.hmcampoverde.exception.ValidationException;

public interface Validator<D> {
	public void validate(D dto) throws ValidationException;
}
