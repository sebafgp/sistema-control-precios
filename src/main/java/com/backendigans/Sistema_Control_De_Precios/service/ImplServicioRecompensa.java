package com.backendigans.Sistema_Control_De_Precios.service;

import com.backendigans.Sistema_Control_De_Precios.model.Recompensa;
import com.backendigans.Sistema_Control_De_Precios.repository.RepositorioRecompensa;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
@Service
@Transactional
public class ImplServicioRecompensa implements ServicioRecompensa {
    @Autowired
    private RepositorioRecompensa recompensaRepository;

    @Override 
    public List<Recompensa> listAllRecompensas() {
    	return recompensaRepository.findAll(); 
	} 

    @Override
	public void saveRecompensa(Recompensa recompensa) { 
    	recompensaRepository.save(recompensa); 
   	}

    @Override
    public Recompensa getRecompensa(Integer recompensaID) {
        return recompensaRepository.findById(recompensaID).get();
    }

    @Override
    public void deleteRecompensa(Integer recompensaID) {
        recompensaRepository.deleteById(recompensaID);
    }
}