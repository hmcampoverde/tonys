package org.hmcampoverde.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MessageResponse<T> {

	private Severity severity;
	private String summary;
	private String detail;
	private T data;

	public MessageResponse(Severity severity, String summary, String detail) {
		this.severity = severity;
		this.summary = summary;
		this.detail = detail;
	}
}
