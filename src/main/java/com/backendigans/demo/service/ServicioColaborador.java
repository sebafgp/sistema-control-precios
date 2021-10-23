package com.backendigans.demo.service;

import java.util.List;
import com.backendigans.demo.model.Colaborador;

public interface ServicioColaborador {
    public List<Colaborador> getColaboradores();

    public Colaborador getColaborador(int id);

    public void guardarColaborador(Colaborador colaborador);

    public void borrarColaborador(int id);
}
