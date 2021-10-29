package com.backendigans.Sistema_Control_De_Precios.service;

import com.backendigans.Sistema_Control_De_Precios.model.Colaborador;
import com.backendigans.Sistema_Control_De_Precios.repository.RepositorioColaborador;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
@Service
@Transactional
public class ImplServicioColaborador implements ServicioColaborador {
    @Autowired
    private RepositorioColaborador colaboradorRepository;
    
    @Override
    public List<Colaborador> listAllColaboradores() {
        return colaboradorRepository.findAll();
    }

    @Override
    public void saveColaborador(Colaborador colaborador) {
        colaboradorRepository.save(colaborador);
    }

    @Override
    public Colaborador getColaborador(Integer colaboradorID) {
        return colaboradorRepository.findById(colaboradorID).get();
    }

    @Override
    public void deleteColaborador(Integer colaboradorID) {
        colaboradorRepository.deleteById(colaboradorID);
    }

    @Override
    public Colaborador buscarColaboradorPorEmail(String email, String contrasena) {
        return colaboradorRepository.findFirstByEmailAndContrasena(email, contrasena).get();
    }
}
