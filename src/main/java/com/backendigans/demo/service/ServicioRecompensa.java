package com.backendigans.demo.service;

import java.util.List;
import com.backendigans.demo.model.Recompensa;

public interface ServicioRecompensa {
    public List<Recompensa> getRecompensas();

    public Recompensa getRecompensa(int id);

    public void guardarRecompensa(Recompensa recompensa);

    public void borrarRecompensa(int id);
}
