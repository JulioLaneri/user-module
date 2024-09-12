package com.api.distri.mappers;

import com.api.distri.abstracts.AbstractMapper;
import com.api.distri.beans.UsuarioBean;
import com.api.distri.dtos.UsuarioDto;
import org.springframework.stereotype.Component;

@Component
public class UsuarioMapper extends AbstractMapper<UsuarioBean, UsuarioDto> {
    @Override
    public UsuarioBean toBean(UsuarioDto dto) {
        return mapper.map(dto, UsuarioBean.class);
    }

    @Override
    public UsuarioDto toDto(UsuarioBean bean) {
        return mapper.map(bean, UsuarioDto.class);
    }
}
