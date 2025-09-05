package org.hmcampoverde.service;

import java.util.List;
import org.hmcampoverde.message.Message;

public interface Service<T> {
	public List<T> findAll();

	public T findById(Long id);

	public Message create(T t);

	public Message update(Long id, T t);

	public Message delete(Long id);
}
