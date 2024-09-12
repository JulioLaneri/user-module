package com.api.distri.dtos;

import com.api.distri.abstracts.AbstractDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UsuarioDto extends AbstractDto {
    private String nombre;
    private String correo;
    private String contrasena;
    private String telefono;
    private List<DireccionDto> direcciones;
}
