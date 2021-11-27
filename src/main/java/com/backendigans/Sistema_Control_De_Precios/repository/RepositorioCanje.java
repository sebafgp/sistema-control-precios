package com.backendigans.Sistema_Control_De_Precios.repository;

import java.util.List;
import java.util.Optional;

import com.backendigans.Sistema_Control_De_Precios.model.Canje;
import com.backendigans.Sistema_Control_De_Precios.model.Colaborador;
import com.backendigans.Sistema_Control_De_Precios.model.Recompensa;

import org.springframework.data.jpa.repository.JpaRepository;

public interface RepositorioCanje extends JpaRepository<Canje, Integer>{


}
