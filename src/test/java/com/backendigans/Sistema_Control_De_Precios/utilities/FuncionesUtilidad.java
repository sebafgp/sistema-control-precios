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
        int precio = 1000;
        Sucursal sucursal = crearSucursal();
        Producto producto = crearProducto();
        return new Inventario(inventarioID, precio, sucursal, producto);
    }

    public static Cadena crearCadena(){
        int id = 99;
        String nombre = "Cadena de prueba";
        return new Cadena(id, nombre);
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

    public static List<Sucursal> cargarSucursalesHU13(){
        List<Sucursal> sucursales = new ArrayList<>();
        Sucursal s1 = new Sucursal(1, 2, "Chillan", "Collin", 121);
        Sucursal s2 = new Sucursal(2, 2, "Chillan", "Libertad", 55);
        Sucursal s3 = new Sucursal(3, 2, "Chillan", "Arauco", 77);
        Sucursal s4 = new Sucursal(4, 2, "Chillan", "5 de Abril", 88);
        sucursales.add(s1);
        sucursales.add(s2);
        sucursales.add(s3);
        sucursales.add(s4);
        return sucursales;
    }

    public static List<Sucursal> cargarSucursalesFinalesHU13(){
        List<Sucursal> sucursales = cargarSucursalesHU13();
        sucursales.remove(1);
        return sucursales;
    }

    public static List<Inventario> cargarInventariosHU13(){
        List<Inventario> inventarios = new ArrayList<>();
        List<Sucursal> sucursales = cargarSucursalesHU13();
        Producto producto = crearProducto();
        Inventario i1 = new Inventario(1, 1200, sucursales.get(0), producto);
        Inventario i2 = new Inventario(2, 1250, sucursales.get(1), producto);
        Inventario i3 = new Inventario(3, 900, sucursales.get(2), producto);
        Inventario i4 = new Inventario(4, 100, sucursales.get(3), producto);
        inventarios.add(i1);
        inventarios.add(i2);
        inventarios.add(i3);
        inventarios.add(i4);
        return inventarios;
    }
}
