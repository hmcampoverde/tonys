package org.hmcampoverde.service;

import java.util.List;

public interface Service<E, D> {
	public List<D> findAll();

	public D findById(Long id);

	public D create(D dto);

	public D update(Long id, D dto);

	public void delete(Long id);
}
