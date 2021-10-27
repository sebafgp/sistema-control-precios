package com.backendigans.Sistema_Control_De_Precios.service;

import com.backendigans.Sistema_Control_De_Precios.model.Cadena;
import com.backendigans.Sistema_Control_De_Precios.repository.RepositorioCadena;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
@Service
@Transactional
public class ImplServicioCadena implements ServicioCadena{
    @Autowired
    private RepositorioCadena cadenaRepository;

    @Override
    public List<Cadena> listAllCadenas() {
        return cadenaRepository.findAll();
    }

    @Override
    public void saveCadena(Cadena cadena) {
        cadenaRepository.save(cadena);
    }

    @Override
    public Cadena getCadena(Integer cadenaID) {
        return cadenaRepository.findById(cadenaID).get();
    }

    @Override
    public void deleteCadena(Integer cadenaID) {
        cadenaRepository.deleteById(cadenaID);
    }
}