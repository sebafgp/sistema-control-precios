package com.backendigans.Sistema_Control_De_Precios.repository;

import java.util.Optional;

import com.backendigans.Sistema_Control_De_Precios.model.Cadena;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RepositorioCadena extends JpaRepository<Cadena, Integer>{
    Optional<Cadena> findByNombre(String nombre);
}
