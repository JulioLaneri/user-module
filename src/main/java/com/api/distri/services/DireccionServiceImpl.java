package com.api.distri.services;

import com.api.distri.beans.DireccionBean;
import com.api.distri.daos.DireccionDao;
import com.api.distri.dtos.DireccionDto;
import com.api.distri.exceptions.NotFoundException;
import com.api.distri.interfaces.IDireccionService;
import com.api.distri.mappers.DireccionMapper;
import com.api.distri.utils.Settings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

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
        DireccionBean newDireccion = mapper.toBean(direccionDto);
        newDireccion.setActivo(true);
        direccionDao.save(newDireccion);
        return mapper.toDto(newDireccion);
    }

    @Override
    public DireccionDto getById(Long id) {
        DireccionBean direccionBean = direccionDao.findByIdAndActivoIsTrue(id);
        if (direccionBean == null){
            throw new NotFoundException("Direccion no encontrada");
        }
        return mapper.toDto(direccionDao.findByIdAndActivoIsTrue(id));
    }

    @Override
    public Page<DireccionDto> getAll(int page) {
        Pageable pageable = PageRequest.of(page, Settings.PAGE_SIZE);
        Page<DireccionBean> direcciones = direccionDao.findAllByActivoIsTrue(pageable);
        return direcciones.map(mapper::toDto);
    }

    @Override
    public DireccionDto update(Long id, DireccionDto direccionDto) {
        // Busca la dirección existente por ID
        DireccionBean direccionExistente = direccionDao.findByIdAndActivoIsTrue(id);
        if (direccionExistente == null) {
            throw new NotFoundException("Direccion no encontrada");
        }
        if (direccionDto.getCiudad() != null) {
            direccionExistente.setCiudad(direccionDto.getCiudad());
        }
        if (direccionDto.getCodigoPostal() != null) {
            direccionExistente.setCodigoPostal(direccionDto.getCodigoPostal());
        }
        if (direccionDto.getCalle() != null) {
            direccionExistente.setCalle(direccionDto.getCalle());
        }
        if (direccionDto.getPais() != null) {
            direccionExistente.setPais(direccionDto.getPais());
        }
        direccionDao.save(direccionExistente);
        return mapper.toDto(direccionExistente);
    }


    @Override
    public boolean delete(Long id) {
        DireccionBean direccionBean = direccionDao.findByIdAndActivoIsTrue(id);
        if (direccionBean == null){
            throw new NotFoundException("Direccion no encontrada");
        }
        direccionBean.setActivo(false);
        direccionDao.save(direccionBean);
        return true;
    }

    @Override
    public List<DireccionDto> get(Long id) {
        return direccionDao.findByUsuario_idAndActivoIsTrue(id).stream().map(mapper::toDto).collect(Collectors.toList());
    }



}
