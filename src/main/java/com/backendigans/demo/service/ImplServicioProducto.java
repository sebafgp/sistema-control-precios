package com.backendigans.demo.service;

import org.springframework.transaction.annotation.Transactional;
import com.backendigans.demo.repository.RepositorioProducto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class ImplServicioProducto implements ServicioProducto {

    @Autowired
    RepositorioProducto repProducto;

    @Override
    public int busca(int num) {
        // TODO Auto-generated method stub
        return 0;
    }
    
}
