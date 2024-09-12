package com.api.distri.services;

import com.api.distri.daos.UsuarioDao;
import com.api.distri.dtos.UsuarioDto;
import com.api.distri.interfaces.IUsuarioService;
import com.api.distri.mappers.UsuarioMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class UsuarioServiceImpl implements IUsuarioService {
    private UsuarioDao usuarioDao;
    private UsuarioMapper mapper;

    @Autowired
    public UsuarioServiceImpl(UsuarioDao usuarioDao, UsuarioMapper mapper) {
        this.usuarioDao = usuarioDao;
        this.mapper = mapper;
    }

    @Override
    public UsuarioDto create(UsuarioDto usuarioDto) {
        return null;
    }

    @Override
    public UsuarioDto getById(Long id) {
        return null;
    }

    @Override
    public Page<UsuarioDto> getAll(int page) {
        return null;
    }

    @Override
    public UsuarioDto update(Long id, UsuarioDto usuarioDto) {
        return null;
    }

    @Override
    public boolean delete(Long id) {
        return false;
    }
}
