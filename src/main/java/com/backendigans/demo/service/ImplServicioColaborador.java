package com.backendigans.demo.service;

import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import com.backendigans.demo.model.Colaborador;
import com.backendigans.demo.repository.RepositorioColaborador;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class ImplServicioColaborador implements ServicioColaborador{

    @Autowired
    RepositorioColaborador repColaborador;

    @Override
    public List<Colaborador> getColaboradores() {
        return repColaborador.findAll();
    }

    @Override
    public Colaborador getColaborador(int id) {
        return repColaborador.findById(id).get();
    }

    @Override
    public void guardarColaborador(Colaborador colaborador) {
        repColaborador.save(colaborador);
    }

    @Override
    public void borrarColaborador(int id) {
        repColaborador.deleteById(id);
    }
    
}
