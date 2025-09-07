package org.hmcampoverde.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MessageResponse<T> {

	private Severity severity;
	private String summary;
	private String detail;
	private T data;
}
