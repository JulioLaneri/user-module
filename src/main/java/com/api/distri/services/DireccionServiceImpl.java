package com.api.distri.services;

import com.api.distri.daos.DireccionDao;
import com.api.distri.dtos.DireccionDto;
import com.api.distri.interfaces.IDireccionService;
import com.api.distri.mappers.DireccionMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class DireccionServiceImpl implements IDireccionService {

    private DireccionDao direccionDao;
    private DireccionMapper mapper;

    @Autowired
    public DireccionServiceImpl(DireccionDao direccionDao, DireccionMapper mapper) {
        this.direccionDao = direccionDao;
        this.mapper = mapper;
    }

    @Override
    public DireccionDto create(DireccionDto direccionDto) {
        return null;
    }

    @Override
    public DireccionDto getById(Long id) {
        return null;
    }

    @Override
    public Page<DireccionDto> getAll(int page) {
        return null;
    }

    @Override
    public DireccionDto update(Long id, DireccionDto direccionDto) {
        return null;
    }

    @Override
    public boolean delete(Long id) {
        return false;
    }
}
