package com.github.apetrelli.gwtintegration.datajpa;

import java.util.List;

public interface SearchRepository<T, F> {

	List<T> search(F filter);
}
