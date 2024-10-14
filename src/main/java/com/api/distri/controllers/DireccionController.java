package com.api.distri.controllers;

import com.api.distri.dtos.DireccionDto;
import com.api.distri.interfaces.IDireccionService;
import com.api.distri.utils.PageResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.NoHandlerFoundException;

import java.util.List;

@RestController
@RequestMapping("/direcciones")
public class DireccionController {
    private final IDireccionService direccionService;

    @Autowired
    public DireccionController( IDireccionService direccionService) {
        this.direccionService = direccionService;
    }

    @PostMapping
    public DireccionDto create(@RequestBody DireccionDto dto){
        return direccionService.create(dto);
    }

    @GetMapping("/{id}")
    public DireccionDto getById(@PathVariable Long id) throws NoHandlerFoundException {

        return direccionService.getById(id);
    }
    @GetMapping("/page/{page}")
    public PageResponse<DireccionDto> getAll(@PathVariable int page){
        return direccionService.getAll(page);
    }

    @PutMapping("/{id}")
    public DireccionDto update(@PathVariable Long id, @RequestBody DireccionDto dto){
        return direccionService.update(id, dto);
    }

    @DeleteMapping("/{id}")
    public Boolean delete(@PathVariable Long id) {
        return direccionService.delete(id);
    }

    @GetMapping("/prueba/{id}")
    public List<DireccionDto> get(@PathVariable Long id){
        return direccionService.get(id);
    }
}
