package org.hmcampoverde.dto.response;

import java.util.List;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PaginatedResponse<T> {

	private List<T> data;
	private int currentPage;
	private int totalPages;
	private long totalItems;
	private int pageSize;
	private boolean hasNext;
	private boolean hasPrevious;
}
