package com.api.distri.beans;

import com.api.distri.abstracts.AbstractBean;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@Table(name = "usuario")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioBean extends AbstractBean {
    private String nombre;
    private String correo;
    private String contrasena;
    private String telefono;

    // Relación con la entidad Direccion (uno a muchos)
    @OneToMany(mappedBy = "usuario", orphanRemoval = true)
    private List<DireccionBean> direcciones;

}
