package com.github.apetrelli.gwtintegration.datajpa;

import java.io.Serializable;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import com.github.apetrelli.gwtintegration.service.CrudService;

@Transactional(readOnly=true)
public abstract class AbstractCrudService<T, I extends Serializable, R extends JpaRepository<T, I>> implements CrudService<T, I> {

	protected R repository;

	/**
	 * @param repository
	 */
	public AbstractCrudService(R repository) {
		this.repository = repository;
	}

	public T findOne(I id) {
		return repository.findOne(id);
	}
	
	@Override
	@Transactional(readOnly=false)
	public T save(T entity) {
		return repository.save(entity);
	}
	
	@Override
	@Transactional(readOnly=false)
	public void delete(I id) {
		repository.delete(id);
	}
	
	@Override
	@Transactional(readOnly=false)
	public void deleteAll(Set<T> items) {
		repository.delete(items);
	}
}
