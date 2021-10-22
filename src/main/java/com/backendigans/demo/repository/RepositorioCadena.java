package com.backendigans.demo.repository;

import com.backendigans.demo.model.Cadena;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RepositorioCadena extends JpaRepository<Cadena, Integer>{
    
}
