package com.api.distri.beans;

import com.api.distri.abstracts.AbstractBean;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Data
@Table(name = "direccion")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DireccionBean extends AbstractBean {
    private String direccion;
    private String ciudad;

    @Column(name = "codigo_postal")
    private String codigoPostal;

    // Relación con la entidad Usuario (muchos a uno)
    @ManyToOne
    @JoinColumn(name = "cliente_id", insertable = false, updatable = false)
    private UsuarioBean usuario;
}
