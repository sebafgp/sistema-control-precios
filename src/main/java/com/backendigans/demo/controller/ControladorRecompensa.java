package com.backendigans.demo.controller;

import java.util.List;
import com.backendigans.demo.model.Recompensa;
import com.backendigans.demo.service.ServicioRecompensa;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/recompensa")
public class ControladorRecompensa {
    @Autowired
    ServicioRecompensa servRecompensa;

    @GetMapping("")
    public List<Recompensa> list() {
        return null;
    }
}
