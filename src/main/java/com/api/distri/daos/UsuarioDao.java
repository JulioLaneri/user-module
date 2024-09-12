package com.api.distri.daos;

import com.api.distri.beans.UsuarioBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioDao extends JpaRepository<UsuarioBean, Long> {

    Page<UsuarioBean> findAllByActivoIsTrue(Pageable pageable);
    UsuarioBean findByIdAndActivoIsTrue(Long id);
}
