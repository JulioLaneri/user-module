package com.api.distri.controllers;

import com.api.distri.dtos.UsuarioDto;
import com.api.distri.interfaces.IUsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {
    private final IUsuarioService usuarioService;

    @Autowired
    public UsuarioController(IUsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody UsuarioDto dto){
        return null;
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable Long id){
        return null;
    }
    @GetMapping("/page/{page}")
    public ResponseEntity<?> getAll(@PathVariable int page){
        return null;
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable int ID, @RequestBody UsuarioDto dto){
        return null;
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        return ResponseEntity.ok("Eliminado exitosamente");
    }

}
