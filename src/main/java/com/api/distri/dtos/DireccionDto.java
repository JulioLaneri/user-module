package com.api.distri.dtos;

import com.api.distri.abstracts.AbstractDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DireccionDto extends AbstractDto {
    private Long usuarioId;
    private String ciudad;
    private String codigoPostal;
    private String calle;
    private String pais;
}
