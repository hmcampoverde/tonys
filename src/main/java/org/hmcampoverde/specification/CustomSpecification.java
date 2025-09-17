package org.hmcampoverde.specification;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Path;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

@AllArgsConstructor
public class CustomSpecification<T> implements Specification<T> {

	private final List<FilterCriteria> filters;

	@Override
	@Nullable
	public Predicate toPredicate(
		@NonNull Root<T> root,
		@Nullable CriteriaQuery<?> query,
		@NonNull CriteriaBuilder builder
	) {
		Predicate predicate = builder.conjunction();

		List<Predicate> predicatesGlobal = new ArrayList<>();

		List<FilterCriteria> criterias = filters
			.stream()
			.distinct()
			.filter(filter -> filter.getValue() != null)
			.toList();

		for (FilterCriteria criteria : criterias) {
			String field = criteria.getField();
			String operator = criteria.getOperator();
			Path<?> path = root.get(field);

			List<FilterCriteria> group = filters
				.stream()
				.filter(filter -> filter.getField().equals(field) && !filter.isGlobal() && !criteria.isGlobal())
				.toList();

			if (group.size() > 1) {
				if (operator.equals("or")) {
					predicate = builder.and(predicate, builder.or(predicate(path, builder, group)));
				} else {
					predicate = builder.and(predicate, builder.and(predicate(path, builder, group)));
				}
			} else {
				if (criteria.isGlobal()) {
					predicatesGlobal.add(predicate(path, builder, criteria));
				} else {
					predicate = builder.and(predicate, predicate(path, builder, criteria));
				}
			}
		}

		if (!predicatesGlobal.isEmpty()) {
			predicate = builder.and(predicate, builder.or(predicatesGlobal.toArray(new Predicate[0])));
		}

		return predicate;
	}

	private Predicate[] predicate(Path<?> path, CriteriaBuilder builder, List<FilterCriteria> criterias) {
		Predicate[] predicates = new Predicate[criterias.size()];
		int index = 0;
		for (FilterCriteria criteria : criterias) {
			predicates[index] = predicate(path, builder, criteria);
			index++;
		}

		return predicates;
	}

	private Predicate predicate(Path<?> path, CriteriaBuilder builder, FilterCriteria criteria) {
		Predicate predicate = null;
		String matchMode = criteria.getMatchMode();
		Object value = criteria.getValue();

		switch (matchMode) {
			case "equal" -> predicate = builder.equal(path, value);
			case "contains" -> {
				if (value instanceof String str) {
					predicate = builder.like(builder.lower(path.as(String.class)), "%" + str.toLowerCase() + "%");
				} else if (value instanceof Boolean bln) {
					predicate = builder.equal(path, bln);
				}
			}
			case "startsWith" -> {
				if (value instanceof String str) {
					predicate = builder.like(builder.lower(path.as(String.class)), str.toLowerCase() + "%");
				}
			}
			case "endsWith" -> {
				if (value instanceof String str) {
					predicate = builder.like(builder.lower(path.as(String.class)), "%" + str.toLowerCase());
				}
			}
			case "gt" -> {
				if (value instanceof Number num) predicate = builder.gt(path.as(Number.class), num);
				else if (value instanceof String dateStr) {
					LocalDate date = LocalDate.parse(dateStr, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
					predicate = builder.greaterThan(path.as(LocalDate.class), date);
				}
			}
			case "lt" -> {
				if (value instanceof Number num) {
					predicate = builder.lt(path.as(Number.class), num);
				} else if (value instanceof String dateStr) {
					LocalDate date = LocalDate.parse(dateStr, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
					predicate = builder.lessThan(path.as(LocalDate.class), date);
				}
			}
		}

		return predicate;
	}
}
