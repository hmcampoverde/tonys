package org.hmcampoverde.dto.request;

import java.io.Serializable;
import java.util.List;
import lombok.Data;
import org.hmcampoverde.specification.FilterCriteria;
import org.hmcampoverde.specification.SortCriteria;

@Data
public class PaginatedRequest implements Serializable {

	private int page;
	private int size;
	private SortCriteria sort;
	private boolean global;
	private List<FilterCriteria> filters;
}
