package com.backendigans.demo.service;

import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import com.backendigans.demo.model.Cadena;
import com.backendigans.demo.repository.RepositorioCadena;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class ImplServicioCadena implements ServicioCadena {

    @Autowired
    RepositorioCadena repCadena;

    @Override
    public List<Cadena> getCadenas() {
        return repCadena.findAll();
    }

    @Override
    public Cadena getCadena(int id) {
        return repCadena.findById(id).get();
    }

    @Override
    public void guardarCadena(Cadena cadena) {
        repCadena.save(cadena);
    }

    @Override
    public void borrarCadena(int id) {
        repCadena.deleteById(id);
    }
    
}
