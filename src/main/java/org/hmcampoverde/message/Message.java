package org.hmcampoverde.message;

import java.util.Map;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Message {

	private String severity;
	private String summary;
	private String detail;
	private Map<String, ?> data;
}
