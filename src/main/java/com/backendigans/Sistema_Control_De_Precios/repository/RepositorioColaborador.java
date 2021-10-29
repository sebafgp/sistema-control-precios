package com.backendigans.Sistema_Control_De_Precios.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

import com.backendigans.Sistema_Control_De_Precios.model.Colaborador;

public interface RepositorioColaborador extends JpaRepository<Colaborador, Integer> {
    Optional<Colaborador> findFirstByEmailAndContrasena(String email, String contrasena);  
}  
