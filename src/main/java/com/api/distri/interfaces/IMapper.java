package com.api.distri.interfaces;

import com.api.distri.abstracts.AbstractBean;
import com.api.distri.abstracts.AbstractDto;
import org.springframework.stereotype.Component;

@Component
public interface IMapper<B extends AbstractBean,D extends AbstractDto>{
    public B toBean(D dto);
    public D toDto(B bean);
}
