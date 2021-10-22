package com.backendigans.demo.controller;

import java.util.List;
import com.backendigans.demo.model.Cadena;
import com.backendigans.demo.service.ServicioCadena;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/cadena")
public class ControladorCadena {
    @Autowired
    ServicioCadena servCadena;

    @GetMapping("")
    public List<Cadena> list() {
        return null;
    }
}
