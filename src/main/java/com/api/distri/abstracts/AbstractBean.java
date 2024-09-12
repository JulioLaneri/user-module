package com.api.distri.abstracts;

import com.api.distri.interfaces.IBean;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.ColumnDefault;

@MappedSuperclass
@Data
public abstract class AbstractBean implements IBean {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "boolean")
    @ColumnDefault("true")
    private boolean activo;
}
