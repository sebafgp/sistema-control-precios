package com.backendigans.demo.service;

import org.springframework.transaction.annotation.Transactional;
import com.backendigans.demo.repository.RepositorioRecompensa;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class ImplServicioRecompensa implements ServicioRecompensa {

    @Autowired
    RepositorioRecompensa repRecompensa;

    @Override
    public int busca(int num) {
        // TODO Auto-generated method stub
        return 0;
    }
    
}
