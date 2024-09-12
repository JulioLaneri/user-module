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
public class UsuarioServiceImpl implements IUsuarioService {
    private final UsuarioDao usuarioDao;
    private final UsuarioMapper mapper;
    private final DireccionMapper direccionMapper;
    private final DireccionDao direccionDao;

    private final IDireccionService direccionService;


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

        Page<UsuarioDto> usuarioDtos = usuarios.map(usuarioBean -> {
            UsuarioDto usuarioDto = mapper.toDto(usuarioBean);

            List<DireccionBean> direccionBeanList = direccionDao.findByUsuario_idAndActivoIsTrue(usuarioBean.getId());

            List<DireccionDto> usuariosList = direccionBeanList.stream()
                    .map(direccionMapper::toDto)
                    .collect(Collectors.toList());

            usuarioDto.setDirecciones(usuariosList);
            return usuarioDto;
        });
        return usuarioDtos;
    }




    @Override
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
