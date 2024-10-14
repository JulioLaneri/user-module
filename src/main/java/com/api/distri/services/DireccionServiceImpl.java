package com.api.distri.services;

import com.api.distri.beans.DireccionBean;
import com.api.distri.daos.DireccionDao;
import com.api.distri.dtos.DireccionDto;
import com.api.distri.exceptions.NotFoundException;
import com.api.distri.interfaces.IDireccionService;
import com.api.distri.mappers.DireccionMapper;
import com.api.distri.utils.PageResponse;
import com.api.distri.utils.Settings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
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

    private CacheManager cacheManager;

    @Autowired
    public DireccionServiceImpl(DireccionDao direccionDao, DireccionMapper mapper, CacheManager cacheManager) {
        this.direccionDao = direccionDao;
        this.mapper = mapper;
        this.cacheManager = cacheManager;
    }

    @Override
    public DireccionDto create(DireccionDto direccionDto) {
        DireccionBean newDireccion = mapper.toBean(direccionDto);
        newDireccion.setActivo(true);
        direccionDao.save(newDireccion);
        return mapper.toDto(newDireccion);
    }

    @Override
    @Cacheable(cacheManager = "cacheManagerWithSecondsTTL",cacheNames = "distri", key = "'direccion_' + #id")
    public DireccionDto getById(Long id) {
        DireccionBean direccionBean = direccionDao.findByIdAndActivoIsTrue(id);
        if (direccionBean == null) {
            throw new NotFoundException("Direccion no encontrada");
        }
        return mapper.toDto(direccionBean);
    }

    @Override
    public PageResponse<DireccionDto> getAll(int page) {
        Pageable pageable = PageRequest.of(page, Settings.PAGE_SIZE);
        Page<DireccionBean> direcciones = direccionDao.findAllByActivoIsTrue(pageable);

        // Accede al caché
        Cache cache = cacheManager.getCache("distri");

        Page<DireccionDto> direccionDtos = direcciones.map(direccionBean -> {
            Long direccionId = direccionBean.getId();
            String cacheKey = "direccion_" + direccionId;
            assert cache != null;
            Cache.ValueWrapper valueWrapper = cache.get(cacheKey);

            DireccionDto direccionDto;
            if (valueWrapper != null) {
                direccionDto = (DireccionDto) valueWrapper.get();
            } else {
                // Si no está en el caché, obtiene la dirección desde la base de datos y la cachea
                direccionDto = getById(direccionId);
                cache.put(cacheKey, direccionDto);
            }

            return direccionDto;
        });
        return new PageResponse<>(
                direccionDtos.getContent(),
                direccionDtos.getTotalPages(),
                direccionDtos.getTotalElements(),
                direccionDtos.getNumber() + 1
        );
    }

    @Override
    @CachePut(cacheNames = "distri", key = "'direccion_' + #id")
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
    @CacheEvict(cacheNames = "distri", key = "'direccion_' + #id")
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
