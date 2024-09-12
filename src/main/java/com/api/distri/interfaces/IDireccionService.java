package com.api.distri.interfaces;

import com.api.distri.beans.UsuarioBean;
import com.api.distri.dtos.DireccionDto;

import java.util.List;

public interface IDireccionService extends IService<DireccionDto> {
    public List<DireccionDto> get(Long id);
}
