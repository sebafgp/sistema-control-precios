package com.backendigans.Sistema_Control_De_Precios.service;

import com.backendigans.Sistema_Control_De_Precios.model.Actualizacion;
import com.backendigans.Sistema_Control_De_Precios.model.Colaborador;
import com.backendigans.Sistema_Control_De_Precios.repository.RepositorioColaborador;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonIncludeProperties;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javassist.expr.NewArray;

import javax.transaction.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;
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

    @Override
    public List<Object> getColaboradorRepAct(String nickname) {
        Colaborador colaborador = colaboradorRepository.findFirstByNickname(nickname);
        List<Object> ob = new ArrayList<>();
        ob.add(colaborador.getNickname());
        ob.add(colaborador.getReputacion());
        ob.add(colaborador.getActualizaciones());
        return ob;
    }
}
