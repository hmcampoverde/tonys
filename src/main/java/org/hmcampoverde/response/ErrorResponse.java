package org.hmcampoverde.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ErrorResponse {

	private Severity severity;
	private String summary;
	private String detail;
}
