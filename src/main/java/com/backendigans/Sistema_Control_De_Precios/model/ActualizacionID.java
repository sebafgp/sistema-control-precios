package com.backendigans.Sistema_Control_De_Precios.model;

import java.io.Serializable;
import java.util.Objects;

public class ActualizacionID implements Serializable {
    
    private int colaborador;
    private int producto;

    // getters/setters and most importantly equals() and hashCode()

    public ActualizacionID(){
        
    }

    public ActualizacionID(int colaborador, int producto){
        this.colaborador = colaborador;
        this.producto = producto;
    }

    //implements equals and hashCode

    public boolean equals(ActualizacionID other){
        if (this.colaborador == other.colaborador && this.producto == other.producto){
            return true;
        } else {
            return false;
        }
    }

    public int hashCode() {
        return Objects.hash(this.colaborador, this.producto);
    }

}
