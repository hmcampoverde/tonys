package org.hmcampoverde.service;

import java.util.List;

public interface Service<T> {
	public List<T> findAll();

	public T findById(Long id);

	public T create(T t);

	public T update(Long id, T t);

	public void delete(Long id);
}
