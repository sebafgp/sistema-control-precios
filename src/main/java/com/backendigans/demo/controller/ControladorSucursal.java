package com.backendigans.demo.controller;

import java.util.List;
import com.backendigans.demo.model.Sucursal;
import com.backendigans.demo.service.ServicioSucursal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/sucursal")
public class ControladorSucursal {
    @Autowired
    ServicioSucursal servSucursal;

    @GetMapping("")
    public List<Sucursal> list() {
        return null;
    }
}
