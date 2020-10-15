package br.com.brainweb.interview.core.exception;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class GlobalFieldError {

	private final String message;
	private final String field;
	private final Object parameter;
	
}
