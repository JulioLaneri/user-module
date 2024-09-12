package com.api.distri.abstracts;

import com.api.distri.interfaces.IDto;
import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

@Data
@NoArgsConstructor
@AllArgsConstructor
public abstract class AbstractDto implements IDto {
    private Long id;

    @Column(columnDefinition = "boolean")
    @ColumnDefault("true")
    private boolean activo;
}
