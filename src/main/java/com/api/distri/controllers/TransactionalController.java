package com.api.distri.controllers;

import com.api.distri.dtos.UsuarioDto;
import com.api.distri.services.TransactionalProxyService;
import com.api.distri.services.TransactionalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.TransactionTimedOutException;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/transaction")
public class TransactionalController {

    private final TransactionalService transactionalService;
    private final TransactionalProxyService transactionalProxyService;

    @Autowired
    public TransactionalController(TransactionalService transactionalService, TransactionalProxyService transactionalProxyService) {
        this.transactionalService = transactionalService;
        this.transactionalProxyService = transactionalProxyService;
    }

    // Endpoint para Propagation.REQUIRED
    @PostMapping("/required/{rollback}")
    public ResponseEntity<UsuarioDto> handleRequiredTransaction(
            @RequestBody UsuarioDto usuarioDto,
            @PathVariable boolean rollback) throws Exception {
        UsuarioDto usuario = transactionalService.handleRequiredTransaction(usuarioDto, rollback);
        return ResponseEntity.ok(usuario);
    }

    // Endpoint para Propagation.SUPPORTS
    @PostMapping("/supports/{rollback}")
    public ResponseEntity<UsuarioDto> handleSupportsTransaction(
            @RequestBody UsuarioDto usuarioDto,
            @PathVariable boolean rollback) throws Exception {
        UsuarioDto usuario = transactionalService.handleSupportsTransaction(usuarioDto, rollback);
        return ResponseEntity.ok(usuario);
    }

    // Endpoint para Propagation.NOT_SUPPORTED
    @PostMapping("/not-supported/{rollback}")
    public ResponseEntity<UsuarioDto> handleNotSupportedTransaction(
            @RequestBody UsuarioDto usuarioDto,
            @PathVariable boolean rollback) throws Exception {
        UsuarioDto usuario = transactionalService.handleNotSupportedTransaction(usuarioDto, rollback);
        return ResponseEntity.ok(usuario);
    }

    // Endpoint para Propagation.REQUIRES_NEW
    @PostMapping("/requires-new/{rollback}")
    public ResponseEntity<UsuarioDto> handleRequiresNewTransaction(
            @RequestBody UsuarioDto usuarioDto,
            @PathVariable boolean rollback) throws Exception {
        UsuarioDto usuario = transactionalService.handleRequiresNewTransaction(usuarioDto, rollback);
        return ResponseEntity.ok(usuario);
    }

    // Endpoint para Propagation.MANDATORY
    @PostMapping("/mandatory/{rollback}")
    public ResponseEntity<UsuarioDto> handleMandatoryTransaction(
            @RequestBody UsuarioDto usuarioDto,
            @PathVariable boolean rollback) throws Exception {
        UsuarioDto usuario = transactionalService.handleMandatoryTransaction(usuarioDto, rollback);
        return ResponseEntity.ok(usuario);
    }

    // Endpoint para Propagation.NEVER
    @PostMapping("/never/{rollback}")
    public ResponseEntity<UsuarioDto> handleNeverTransaction(
            @RequestBody UsuarioDto usuarioDto,
            @PathVariable boolean rollback) throws Exception {
        UsuarioDto usuario = transactionalService.handleNeverTransaction(usuarioDto, rollback);
        return ResponseEntity.ok(usuario);
    }

    // Endpoint para manejar rollback automático en caso de excepción
    @PostMapping("/required-with-rollback")
    public ResponseEntity<UsuarioDto> handleRequiredWithRollback(
            @RequestBody UsuarioDto usuarioDto) throws Exception {
        UsuarioDto usuario = transactionalService.handleRequiredWithRollback(usuarioDto);
        return ResponseEntity.ok(usuario);
    }

    @PostMapping("/execute-with-timeout")
    public ResponseEntity<UsuarioDto> executeWithTimeout(@RequestBody UsuarioDto dto) {
        try {
            UsuarioDto result = transactionalService.executeWithTimeout(dto);
            return ResponseEntity.ok(result);
        } catch (TransactionTimedOutException e) {
            return ResponseEntity.status(408).body(null); // 408 Request Timeout
        } catch (Exception e) {
            return ResponseEntity.status(500).body(null);
        }
    }

    @PostMapping("/required")
    public ResponseEntity<UsuarioDto> handleRequiredTransaction(@RequestBody UsuarioDto dto) {
        try {
            UsuarioDto result = transactionalProxyService.handleRequiredTransaction(dto);
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            return ResponseEntity.status(500).body(null);
        }
    }

    @PostMapping("/supports")
    public ResponseEntity<UsuarioDto> handleSupportsTransaction(@RequestBody UsuarioDto dto) {
        try {
            UsuarioDto result = transactionalProxyService.handleSupportsTransaction(dto);
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            return ResponseEntity.status(500).body(null);
        }
    }

    @PostMapping("/not-supported")
    public ResponseEntity<UsuarioDto> handleNotSupportedTransaction(@RequestBody UsuarioDto dto) {
        try {
            UsuarioDto result = transactionalProxyService.handleNotSupportedTransaction(dto);
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            return ResponseEntity.status(500).body(null);
        }
    }

    @PostMapping("/requires-new")
    public ResponseEntity<UsuarioDto> handleRequiresNewTransaction(@RequestBody UsuarioDto dto) {
        try {
            UsuarioDto result = transactionalProxyService.handleRequiresNewTransaction(dto);
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            return ResponseEntity.status(500).body(null);
        }
    }

    @PostMapping("/mandatory")
    public ResponseEntity<UsuarioDto> handleMandatoryTransaction(@RequestBody UsuarioDto dto) {
        try {
            UsuarioDto result = transactionalProxyService.handleMandatoryTransaction(dto);
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            return ResponseEntity.status(500).body(null);
        }
    }

    @PostMapping("/never")
    public ResponseEntity<UsuarioDto> handleNeverTransaction(@RequestBody UsuarioDto dto) {
        try {
            UsuarioDto result = transactionalProxyService.handleNeverTransaction(dto);
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            return ResponseEntity.status(500).body(null);
        }
    }

    // Métodos con transacciones

    @PostMapping("/execute/required")
    public ResponseEntity<UsuarioDto> executeRequiredTransaction(@RequestBody UsuarioDto dto) {
        try {
            UsuarioDto result = transactionalProxyService.executeRequiredTransaction(dto);
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            return ResponseEntity.status(500).body(null);
        }
    }

    @PostMapping("/execute/supports")
    public ResponseEntity<UsuarioDto> executeSupportsTransaction(@RequestBody UsuarioDto dto) {
        try {
            UsuarioDto result = transactionalProxyService.executeSupportsTransaction(dto);
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            return ResponseEntity.status(500).body(null);
        }
    }

    @PostMapping("/execute/not-supported")
    public ResponseEntity<UsuarioDto> executeNotSupportedTransaction(@RequestBody UsuarioDto dto) {
        try {
            UsuarioDto result = transactionalProxyService.executeNotSupportedTransaction(dto);
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            return ResponseEntity.status(500).body(null);
        }
    }

    @PostMapping("/execute/requires-new")
    public ResponseEntity<UsuarioDto> executeRequiresNewTransaction(@RequestBody UsuarioDto dto) {
        try {
            UsuarioDto result = transactionalProxyService.executeRequiresNewTransaction(dto);
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            return ResponseEntity.status(500).body(null);
        }
    }

    @PostMapping("/execute/mandatory")
    public ResponseEntity<UsuarioDto> executeMandatoryTransaction(@RequestBody UsuarioDto dto) {
        try {
            UsuarioDto result = transactionalProxyService.executeMandatoryTransaction(dto);
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            return ResponseEntity.status(500).body(null);
        }
    }

    @PostMapping("/execute/never")
    public ResponseEntity<UsuarioDto> executeNeverTransaction(@RequestBody UsuarioDto dto) {
        try {
            UsuarioDto result = transactionalProxyService.executeNeverTransaction(dto);
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            return ResponseEntity.status(500).body(null);
        }
    }

    @DeleteMapping("/readOnlyDelete/{id}")
    public ResponseEntity<String> readOnlyDelete(@PathVariable Long id) {
        try {
            transactionalService.readOnlyExecute(id);
            return ResponseEntity.ok("Usuario eliminado exitosamente.");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error: " + e.getMessage());
        }
    }
}
