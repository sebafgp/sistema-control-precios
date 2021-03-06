package com.backendigans.Sistema_Control_De_Precios.service;

import java.util.List;
import com.backendigans.Sistema_Control_De_Precios.model.Colaborador;

public interface ServicioColaborador {
    public List<Colaborador> listAllColaboradores();

    public Colaborador saveColaborador(Colaborador colaborador);

    public Colaborador getColaborador(Integer colaboradorID);

    public Colaborador deleteColaborador(Integer colaboradorID);

    public Colaborador buscarColaboradorPorEmail(String email, String contrasena);

    public List<Colaborador> getTopColaboradores();

    public Colaborador getColaboradorByNickname(String nickname);
}
