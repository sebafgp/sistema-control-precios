package com.backendigans.Sistema_Control_De_Precios.service;

import com.backendigans.Sistema_Control_De_Precios.model.Colaborador;
import com.backendigans.Sistema_Control_De_Precios.repository.RepositorioColaborador;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

import java.util.ArrayList;
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

    @Override
    /*public List<Colaborador> getTopColaboradores() {
            List <Colaborador> colaboradores=colaboradorRepository.findAll();

            List <Colaborador> colaboradores=colaboradorRepository.findTopColaboradores(Pageable.unpaged().getSort().by(Sort.Order.desc("reputacion")));
            return colaboradorRepository.findTop10ByOrderByReputacionDesc();
    }*/
    
    public List<String> getTopColaboradores(){

        List<Colaborador> colaboradores = colaboradorRepository.findByOrderByReputacionDesc(Sort.by(Sort.Order.desc("reputacion")));
        colaboradores = colaboradores.subList(0, 3);
        List<String> topTres=new ArrayList<String>();
        for (Colaborador c : colaboradores) {
            topTres.add(c.getNickname()+", reputacion: "+c.getReputacion());
        }

        return topTres;

    }
}
