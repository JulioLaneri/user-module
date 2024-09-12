package com.api.distri.interfaces;

import com.api.distri.abstracts.AbstractDto;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;

public interface IService<T extends AbstractDto> {
    public T create(T t);
    public T getById(Long id);
    public Page<T> getAll(int page);
    public T update(Long id, T t);
    public boolean delete(Long id);
}
