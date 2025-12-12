package com.huertohogar.backend.controller;

import com.huertohogar.backend.model.Boleta;
import com.huertohogar.backend.service.BoletaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/checkout")
public class BoletaController {

    @Autowired
    private BoletaService service;

    // Crear boleta (pendiente)
    @PostMapping
    public ResponseEntity<Boleta> finalizeCheckout(@RequestBody Boleta boleta) {
        try {
            Boleta savedBoleta = service.save(boleta);
            return new ResponseEntity<>(savedBoleta, HttpStatus.CREATED);
        } catch (Exception e) {
            System.err.println("Error al guardar la boleta: " + e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
