package com.api.distri.services;

import com.api.distri.daos.UsuarioDao;
import com.api.distri.dtos.UsuarioDto;
import com.api.distri.exceptions.CustomRollbackException;
import com.api.distri.mappers.UsuarioMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionTimedOutException;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;


@Service
public class TransactionalService {

    private final UsuarioDao usuarioDao;
    private final UsuarioMapper mapper;
    private final UsuarioServiceImpl usuarioService;

    @Autowired
    public TransactionalService(UsuarioDao usuarioDao, UsuarioMapper mapper, UsuarioServiceImpl usuarioService) {
        this.usuarioDao = usuarioDao;
        this.mapper = mapper;
        this.usuarioService = usuarioService;
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = CustomRollbackException.class)
    public UsuarioDto handleTransaction(UsuarioDto dto, boolean shouldRollback) throws Exception {
        UsuarioDto usuario = usuarioService.create(dto);
        if (shouldRollback) {
            throw new CustomRollbackException("Rollback");
        }
        return usuario;
    }

    @Transactional(propagation = Propagation.REQUIRED, timeout = 100)
    public UsuarioDto handleRequiredTransaction(UsuarioDto dto, boolean shouldRollback) throws Exception {
        return handleTransaction(dto, shouldRollback);
    }

    @Transactional(propagation = Propagation.SUPPORTS, timeout = 100)
    public UsuarioDto handleSupportsTransaction(UsuarioDto dto, boolean shouldRollback) throws Exception {
        return handleTransaction(dto, shouldRollback);
    }

    @Transactional(propagation = Propagation.NOT_SUPPORTED, timeout = 100)
    public UsuarioDto handleNotSupportedTransaction(UsuarioDto dto, boolean shouldRollback) throws Exception {
        return handleTransaction(dto, shouldRollback);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, timeout = 100)
    public UsuarioDto handleRequiresNewTransaction(UsuarioDto dto, boolean shouldRollback) throws Exception {
        return handleTransaction(dto, shouldRollback);
    }

    @Transactional(propagation = Propagation.MANDATORY, timeout = 100)
    public UsuarioDto handleMandatoryTransaction(UsuarioDto dto, boolean shouldRollback) throws Exception {
        return handleTransaction(dto, shouldRollback);
    }

    @Transactional(propagation = Propagation.NEVER, timeout = 100)
    public UsuarioDto handleNeverTransaction(UsuarioDto dto, boolean shouldRollback) throws Exception {
        return handleTransaction(dto, shouldRollback);
    }

    // Ejemplo de rollback automático en caso de excepción
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public UsuarioDto handleRequiredWithRollback(UsuarioDto dto) throws Exception {
        return handleTransaction(dto, true);
    }

    @Transactional(timeout = 1, rollbackFor = TransactionTimedOutException.class)
    public UsuarioDto executeWithTimeout(UsuarioDto dto) throws Exception {
        return handleTransaction(dto, false);
    }

    @Transactional(readOnly = true)
    public void readOnlyExecute(Long id) throws Exception {
        usuarioDao.deleteById(id);
        if (usuarioDao.existsById(id)){
            throw new CustomRollbackException("La transaccion es solo de lectura, no se pueden borrar ususarios");
        }

    }

}
