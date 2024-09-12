package com.api.distri.abstracts;

import com.api.distri.interfaces.IMapper;
import jakarta.jws.WebParam;
import org.modelmapper.ModelMapper;

public abstract class AbstractMapper<B extends AbstractBean, D extends AbstractDto> implements IMapper<B, D> {
    protected ModelMapper mapper = new ModelMapper();
}
