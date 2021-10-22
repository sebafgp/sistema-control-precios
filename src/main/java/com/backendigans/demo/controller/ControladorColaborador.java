package com.backendigans.demo.controller;

import java.util.List;
import com.backendigans.demo.model.Colaborador;
import com.backendigans.demo.service.ServicioColaborador;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/colaborador")
public class ControladorColaborador {
    @Autowired
    ServicioColaborador servColaborador;

    @GetMapping("")
    public List<Colaborador> list() {
        return null;
    }
}
