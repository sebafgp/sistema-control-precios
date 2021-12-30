package com.backendigans.Sistema_Control_De_Precios.utilities;

import com.backendigans.Sistema_Control_De_Precios.model.*;

import java.util.ArrayList;
import java.util.List;

public class FuncionesUtilidad {
    public static Colaborador crearColaborador(){
        return new Colaborador("ex@mail.com", "password", "nick");
    }

    public static Sucursal crearSucursal() {
        return new Sucursal(1, 1, "Chillan", "Collin", 111);
    }
    public static Actualizacion crearActualizacion(){
        Colaborador c = crearColaborador();
        Inventario i = crearInventario();
        return new Actualizacion(c, i, 1000);
    }

    public static Producto crearProducto(){
        return new Producto(1, "Tallarines", "Carozzi", 100, "g");
    }

    public static Inventario crearInventario() {
        int inventarioID = 1;
        Sucursal sucursal = crearSucursal();
        Producto producto = crearProducto();
        return new Inventario(inventarioID, sucursal, producto);
    }

    public static Recompensa crearRecompensa() {
        return new Recompensa(1, "Giftcard", 1000, 20, "Es una giftcard");
    }

    public static List<Producto> cargarProductos(){
        List<Producto> productos = new ArrayList<>();

        Producto p1 = new Producto();
        p1.setProductoID(1);
        p1.setNombre("Tallarines");
        p1.setMarca("Lucchetti");
        p1.setCantidad(1);
        productos.add(p1);

        Producto p2 = new Producto();
        p2.setProductoID(2);
        p2.setNombre("Tallarines");
        p2.setMarca("Carozzi");
        p2.setCantidad(1);
        productos.add(p2);

        return productos;
    }

    public static List<Inventario> cargarInventarios(){
        List<Inventario> invs = new ArrayList<>();
        int precio = 1000;
        Sucursal s = crearSucursal();
        Inventario i1 = new Inventario(precio, s, cargarProductos().get(0));
        invs.add(i1);

        precio = 1500;
        Inventario i2 = new Inventario(precio, s, cargarProductos().get(1));
        invs.add(i2);

        return invs;

    }
}
