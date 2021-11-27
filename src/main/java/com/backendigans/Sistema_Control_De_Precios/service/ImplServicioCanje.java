package com.backendigans.Sistema_Control_De_Precios.service;

import java.util.List;

import javax.transaction.Transactional;

import com.backendigans.Sistema_Control_De_Precios.model.Canje;
import com.backendigans.Sistema_Control_De_Precios.repository.RepositorioCanje;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class ImplServicioCanje implements ServicioCanje{
    @Autowired
    private RepositorioCanje canjeRepository;

	@Override
	public List<Canje> listAllCanje() {
		return canjeRepository.findAll();
	}

}