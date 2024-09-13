package com.api.distri.controllers;

import com.api.distri.dtos.UsuarioDto;
import com.api.distri.interfaces.IUsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.NoHandlerFoundException;

import java.util.List;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {
    private final IUsuarioService usuarioService;

    @Autowired
    public UsuarioController(IUsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @PostMapping
    public UsuarioDto create(@RequestBody UsuarioDto dto){
        return usuarioService.create(dto);
    }

    @GetMapping("/{id}")
    public UsuarioDto getById(@PathVariable Long id) throws NoHandlerFoundException {
        return usuarioService.getById(id);
    }
    @GetMapping("/page/{page}")
    public List<UsuarioDto> getAll(@PathVariable int page){
        return usuarioService.getAll(page).getContent();
    }

    @PutMapping("/{id}")
    public UsuarioDto update(@PathVariable Long id, @RequestBody UsuarioDto dto){
        return usuarioService.update(id, dto);
    }

    @DeleteMapping("/{id}")
    public Boolean delete(@PathVariable Long id) {
        return usuarioService.delete(id);
    }

}
