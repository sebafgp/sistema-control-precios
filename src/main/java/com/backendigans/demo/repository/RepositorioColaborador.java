package com.backendigans.demo.repository;

import com.backendigans.demo.model.Colaborador;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RepositorioColaborador extends JpaRepository<Colaborador, Integer>{
    
}
