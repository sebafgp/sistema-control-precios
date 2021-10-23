package com.backendigans.demo.service;

import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import com.backendigans.demo.model.Recompensa;
import com.backendigans.demo.repository.RepositorioRecompensa;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class ImplServicioRecompensa implements ServicioRecompensa {

    @Autowired
    RepositorioRecompensa repRecompensa;

    @Override
    public List<Recompensa> getRecompensas() {
        return repRecompensa.findAll();
    }

    @Override
    public Recompensa getRecompensa(int id) {
        return repRecompensa.findById(id).get();
    }

    @Override
    public void guardarRecompensa(Recompensa recompensa) {
        repRecompensa.save(recompensa);
    }

    @Override
    public void borrarRecompensa(int id) {
        repRecompensa.deleteById(id);
    }

}
