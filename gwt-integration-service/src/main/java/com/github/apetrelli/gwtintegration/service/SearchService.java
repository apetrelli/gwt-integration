package com.github.apetrelli.gwtintegration.service;

import java.util.List;

public interface SearchService<T, F> {

    List<T> search(F filter);
}
