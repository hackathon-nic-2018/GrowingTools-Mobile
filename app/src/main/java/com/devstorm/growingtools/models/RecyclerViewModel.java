package com.devstorm.growingtools.models;

public class RecyclerViewModel {

    private Double precio;
    private int cantidad;
    private String Categoria;
    private Double costo;
    private int source;
    private String nombre;
    private String nombreProveedor;

    public RecyclerViewModel(Double precio, int cantidad, String categoria, Double costo, int source, String nombre, String nombreProveedor, int ID) {
        this.precio = precio;
        this.cantidad = cantidad;
        Categoria = categoria;
        this.costo = costo;
        this.source = source;
        this.nombre = nombre;
        this.nombreProveedor = nombreProveedor;
        this.ID = ID;
    }

    private int ID;

    public Double getPrecio() {
        return precio;
    }

    public void setPrecio(Double precio) {
        this.precio = precio;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public String getCategoria() {
        return Categoria;
    }

    public void setCategoria(String categoria) {
        Categoria = categoria;
    }

    public Double getCosto() {
        return costo;
    }

    public void setCosto(Double costo) {
        this.costo = costo;
    }

    public int getSource() {
        return source;
    }

    public void setSource(int source) {
        this.source = source;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getNombreProveedor() {
        return nombreProveedor;
    }

    public void setNombreProveedor(String nombreProveedor) {
        this.nombreProveedor = nombreProveedor;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }
}

