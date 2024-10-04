package com.api.distri.services;

import com.api.distri.dtos.UsuarioDto;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TransactionalProxyService {

    private final TransactionalService transactionalService;

    @Autowired
    public TransactionalProxyService(TransactionalService transactionalService) {
        this.transactionalService = transactionalService;
    }

    public UsuarioDto handleRequiredTransaction(UsuarioDto dto) throws Exception {
        return transactionalService.handleRequiredTransaction(dto, false);
    }

    public UsuarioDto handleSupportsTransaction(UsuarioDto dto) throws Exception {
        return transactionalService.handleSupportsTransaction(dto, false);
    }

    public UsuarioDto handleNotSupportedTransaction(UsuarioDto dto) throws Exception {
        return transactionalService.handleNotSupportedTransaction(dto, false);
    }

    public UsuarioDto handleRequiresNewTransaction(UsuarioDto dto) throws Exception {
        return transactionalService.handleRequiresNewTransaction(dto, false);
    }

    public UsuarioDto handleMandatoryTransaction(UsuarioDto dto) throws Exception {
        return transactionalService.handleMandatoryTransaction(dto, false);
    }

    public UsuarioDto handleNeverTransaction(UsuarioDto dto) throws Exception {
        return transactionalService.handleNeverTransaction(dto, false);
    }

    //CON TRANSACCIONES

    @Transactional
    public UsuarioDto executeRequiredTransaction(UsuarioDto dto) throws Exception {
        return transactionalService.handleRequiredTransaction(dto, false);
    }

    @Transactional
    public UsuarioDto executeSupportsTransaction(UsuarioDto dto) throws Exception {
        return transactionalService.handleSupportsTransaction(dto, false);
    }

    @Transactional
    public UsuarioDto executeNotSupportedTransaction(UsuarioDto dto) throws Exception {
        return transactionalService.handleNotSupportedTransaction(dto, false);
    }

    @Transactional
    public UsuarioDto executeRequiresNewTransaction(UsuarioDto dto) throws Exception {
        return transactionalService.handleRequiresNewTransaction(dto, false);
    }

    @Transactional
    public UsuarioDto executeMandatoryTransaction(UsuarioDto dto) throws Exception {
        return transactionalService.handleMandatoryTransaction(dto, false);
    }

    @Transactional
    public UsuarioDto executeNeverTransaction(UsuarioDto dto) throws Exception {
        return transactionalService.handleNeverTransaction(dto, false);
    }


}
