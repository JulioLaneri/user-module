package com.api.distri.daos;

import com.api.distri.beans.DireccionBean;
import com.api.distri.beans.UsuarioBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DireccionDao extends JpaRepository<DireccionBean, Long>{
    Page<DireccionBean> findAllByActivoIsTrue(Pageable pageable);
    DireccionBean findByIdAndActivoIsTrue(Long id);
    List<DireccionBean> findByUsuario_idAndActivoIsTrue(Long usuario_id);
}
