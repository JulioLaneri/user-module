package com.api.distri.mappers;

import com.api.distri.abstracts.AbstractMapper;
import com.api.distri.beans.DireccionBean;
import com.api.distri.dtos.DireccionDto;
import org.springframework.stereotype.Component;

@Component
public class DireccionMapper extends AbstractMapper<DireccionBean, DireccionDto> {
    @Override
    public DireccionBean toBean(DireccionDto dto) {
        return mapper.map(dto, DireccionBean.class);
    }

    @Override
    public DireccionDto toDto(DireccionBean bean) {
        return mapper.map(bean, DireccionDto.class);
    }
}
