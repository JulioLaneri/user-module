package com.api.distri.dtos;

import com.api.distri.abstracts.AbstractDto;

import java.util.List;

public class UsuarioDto extends AbstractDto {
    private String nombre;
    private String correo;
    private String contrasena;
    private String telefono;

    private List<DireccionDto> direcciones;
}
