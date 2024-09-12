package com.api.distri.daos;

import com.api.distri.beans.DireccionBean;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DireccionDao extends JpaRepository<DireccionBean, Long>{
}
