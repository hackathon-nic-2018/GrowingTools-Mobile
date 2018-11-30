package com.devstorm.growingtools.POJOS;

public class Inventario {


    private String Categoria;
    private int ID_inventario;
    private Double Precio_venta;
    private Double Costo;
    private int Cantidad;
    private String Nombre;

    public String getCategoria() {
        return Categoria;
    }

    public void setCategoria(String categoria) {
        Categoria = categoria;
    }

    public int getID_inventario() {
        return ID_inventario;
    }

    public void setID_inventario(int ID_inventario) {
        this.ID_inventario = ID_inventario;
    }

    public Double getPrecio_venta() {
        return Precio_venta;
    }

    public void setPrecio_venta(Double precio_venta) {
        Precio_venta = precio_venta;
    }

    public Double getCosto() {
        return Costo;
    }

    public void setCosto(Double costo) {
        Costo = costo;
    }

    public int getCantidad() {
        return Cantidad;
    }

    public void setCantidad(int cantidad) {
        Cantidad = cantidad;
    }

    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String nombre) {
        Nombre = nombre;
    }

    public String getNombreProveedor() {
        return NombreProveedor;
    }

    public void setNombreProveedor(String nombreProveedor) {
        NombreProveedor = nombreProveedor;
    }

    private String NombreProveedor;


}
