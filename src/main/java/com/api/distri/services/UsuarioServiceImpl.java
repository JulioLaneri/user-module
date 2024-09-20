package com.api.distri.services;

import com.api.distri.beans.DireccionBean;
import com.api.distri.beans.UsuarioBean;
import com.api.distri.daos.DireccionDao;
import com.api.distri.daos.UsuarioDao;
import com.api.distri.dtos.DireccionDto;
import com.api.distri.dtos.UsuarioDto;
import com.api.distri.exceptions.NotFoundException;
import com.api.distri.interfaces.IDireccionService;
import com.api.distri.interfaces.IUsuarioService;
import com.api.distri.mappers.DireccionMapper;
import com.api.distri.mappers.UsuarioMapper;
import com.api.distri.utils.Settings;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.aop.framework.AopContext;
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
public class UsuarioServiceImpl implements IUsuarioService {
    private final UsuarioDao usuarioDao;
    private final UsuarioMapper mapper;
    private final DireccionMapper direccionMapper;
    private final DireccionDao direccionDao;
    private final IDireccionService direccionService;

    @Autowired
    private CacheManager cacheManager;


    @Autowired
    public UsuarioServiceImpl(UsuarioDao usuarioDao, UsuarioMapper mapper, DireccionMapper direccionMapper, DireccionDao direccionDao, IDireccionService direccionService) {
        this.usuarioDao = usuarioDao;
        this.mapper = mapper;
        this.direccionMapper = direccionMapper;
        this.direccionDao = direccionDao;
        this.direccionService = direccionService;
    }

    @Override
    public UsuarioDto create(UsuarioDto usuarioDto) {
        UsuarioBean newUsuario = mapper.toBean(usuarioDto);
        newUsuario.setActivo(true);
        List<DireccionBean> direcciones = usuarioDto.getDirecciones().stream()
                .map(direccionDto -> {
                    DireccionBean direccionBean = direccionMapper.toBean(direccionDto); // Convertimos DirecciónDto a DirecciónBean
                    direccionBean.setUsuario(newUsuario); // Seteamos el UsuarioBean directamente
                    direccionBean.setActivo(true);
                    return direccionBean;
                })
                .toList();

        newUsuario.setDirecciones(direcciones);

        usuarioDao.save(newUsuario);

        return mapper.toDto(newUsuario);
    }



    @Override
    @Cacheable(cacheNames = "distri", key = "'usuario_' + #id")
    public UsuarioDto getById(Long id) {
        UsuarioBean usuarioBean = usuarioDao.findByIdAndActivoIsTrue(id);
        if (usuarioBean == null){
            throw new NotFoundException("Usuario no encontrado");
        }
        UsuarioDto usuarioDto = mapper.toDto(usuarioBean);
        List<DireccionBean> direccionBeanList = direccionDao.findByUsuario_idAndActivoIsTrue(usuarioBean.getId());
        List<DireccionDto> direccionDtos = direccionBeanList.stream()
                .map(direccionMapper::toDto)
                .collect(Collectors.toList());
        usuarioDto.setDirecciones(direccionDtos);

        return usuarioDto;
    }


    @Override
    public Page<UsuarioDto> getAll(int page) {
        Pageable pageable = PageRequest.of(page, Settings.PAGE_SIZE);
        Page<UsuarioBean> usuarios = usuarioDao.findAllByActivoIsTrue(pageable);

        // Accede al caché
        Cache cache = cacheManager.getCache("distri");

        Page<UsuarioDto> usuarioDtos = usuarios.map(usuarioBean -> {
            Long usuarioId = usuarioBean.getId();
            String cacheKey = "usuario_" + usuarioId;
            assert cache != null;
            Cache.ValueWrapper valueWrapper = cache.get(cacheKey);

            UsuarioDto usuarioDto;
            if (valueWrapper != null) {
                usuarioDto = (UsuarioDto) valueWrapper.get();
            } else {
                // Si no está en el caché, obtiene el usuario desde la base de datos y lo cachea
                usuarioDto = getById(usuarioId);
                cache.put(cacheKey, usuarioDto);
            }

            return usuarioDto;
        });

        return usuarioDtos;
    }






    @Override
    @CachePut(cacheNames = "distri", key = "'usuario_' + #id")
    public UsuarioDto update(Long id, UsuarioDto usuarioDto) {
        UsuarioBean usuarioExistente = usuarioDao.findByIdAndActivoIsTrue(id);
        if (!usuarioExistente.isActivo()){
            throw new NotFoundException("Usuario no encontrado");
        }

        if (usuarioDto.getNombre() != null) {
            usuarioExistente.setNombre(usuarioDto.getNombre());
        }
        if (usuarioDto.getCorreo() != null) {
            usuarioExistente.setCorreo(usuarioDto.getCorreo());
        }
        if (usuarioDto.getContrasena() != null) {
            usuarioExistente.setContrasena(usuarioDto.getContrasena());
        }
        if (usuarioDto.getTelefono() != null) {
            usuarioExistente.setTelefono(usuarioDto.getTelefono());
        }
        // Si hay direcciones en el DTO, actualiza las direcciones usando el método update de Direccion
        if (usuarioDto.getDirecciones() != null && !usuarioDto.getDirecciones().isEmpty()) {
            List<DireccionBean> direccionesActualizadas = usuarioDto.getDirecciones().stream()
                    .map(direccionDto -> {
                        if (direccionDto.getId() != null) {
                            return direccionMapper.toBean(direccionService.update(direccionDto.getId(), direccionDto));
                        } else {
                            DireccionBean nuevaDireccion = direccionMapper.toBean(direccionDto);
                            nuevaDireccion.setUsuario(usuarioExistente);
                            nuevaDireccion.setActivo(true);
                            return nuevaDireccion;
                        }
                    })
                    .toList();

            usuarioExistente.setDirecciones(direccionesActualizadas); // Actualiza las direcciones del usuario
        }
        usuarioDao.save(usuarioExistente);
        return mapper.toDto(usuarioExistente);
    }


    @Override
    @CacheEvict(cacheNames = "distri", key = "'usuario_' + #id")
    public boolean delete(Long id) {
        UsuarioBean usuarioBean = usuarioDao.findByIdAndActivoIsTrue(id);
        if (usuarioBean == null){
            throw new NotFoundException("Usuario no encontrado");
        }
        usuarioBean.setActivo(false);
        List<DireccionBean> direcciones = direccionDao.findByUsuario_idAndActivoIsTrue(usuarioBean.getId());
        direcciones.forEach(direccion -> {
            direccion.setActivo(false);
            direccionDao.save(direccion);
        });
        usuarioDao.save(usuarioBean);
        return true;
    }

}
