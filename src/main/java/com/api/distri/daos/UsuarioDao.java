package com.api.distri.daos;

import com.api.distri.beans.UsuarioBean;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioDao extends JpaRepository<UsuarioBean, Long> {
}
