package org.hmcampoverde.specification;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false, onlyExplicitlyIncluded = true)
public class FilterCriteria {

	@EqualsAndHashCode.Include
	private String field;

	private String matchMode;
	private String operator;
	private Object value;

	@EqualsAndHashCode.Include
	private boolean global;
}
