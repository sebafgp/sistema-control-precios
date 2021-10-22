package com.backendigans.demo.controller;

import java.util.List;
import com.backendigans.demo.model.Producto;
import com.backendigans.demo.service.ServicioProducto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/producto")
public class ControladorProducto {
    @Autowired
    ServicioProducto servProducto;

    @GetMapping("")
    public List<Producto> list() {
        return null;
    }
}
