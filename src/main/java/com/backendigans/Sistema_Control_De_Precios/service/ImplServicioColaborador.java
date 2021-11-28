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
    public Colaborador saveColaborador(Colaborador colaborador) {
        if (colaborador.getEmail() == null || colaborador.getEmail().isEmpty()
                || colaborador.getContrasena() == null || colaborador.getContrasena().isEmpty()
                || colaborador.getNickname() == null || colaborador.getNickname().isEmpty())
            throw new IllegalArgumentException();
        return colaboradorRepository.save(colaborador);
    }

    @Override
    public Colaborador getColaborador(Integer colaboradorID) {
        return colaboradorRepository.findById(colaboradorID).get();
    }

    @Override
    public Colaborador deleteColaborador(Integer colaboradorID) {
        Colaborador c = getColaborador(colaboradorID);
        if(c == null) return null;
        colaboradorRepository.deleteById(colaboradorID);
        return c;
    }

    @Override
    public Colaborador buscarColaboradorPorEmail(String email, String contrasena) {
        return colaboradorRepository.findFirstByEmailAndContrasena(email, contrasena).get();
    }

    @Override

    public List<Colaborador> getTopColaboradores() {

        List<Colaborador> colaboradores = colaboradorRepository
                .findByOrderByReputacionDesc(Sort.by(Sort.Order.desc("reputacion")));
        if (colaboradores.size() == 0 || colaboradores.size() == 1) {
            return colaboradores;

        } else if (colaboradores.size() == 2) {
            colaboradores = colaboradores.subList(0, 2);
        } else {
            colaboradores = colaboradores.subList(0, 3);
        }

        return colaboradores;

    }

    @Override
    public Colaborador getColaboradorByNickname(String nickname) {
        return colaboradorRepository.findFirstByNickname(nickname);
    }
}
