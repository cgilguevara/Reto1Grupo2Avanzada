package com.example.reto01_pizzeria.model;

public class Producto {
  // Esta clase solo tinen contructores, getters y setteers.
  private int id;
  private String nombre;
  private String ingrediente;
  private String precio;
  private String cantidad;

  public Producto(String nombre, String ingrediente, String precio, String cantidad) {
    this.nombre = nombre;
    this.ingrediente = ingrediente;
    this.precio = precio;
    this.cantidad = cantidad;
  }
  public Producto() {
//Vacio
  }

  public int getId() {
    return id;
  }
  public String getNombre() {
    return nombre;
  }
  public String getIngrediente() {
    return ingrediente;
  }
  public String getPrecio() {
    return precio;
  }
  public String getCantidad() {
    return cantidad;
  }

  public void setId(int id) {
    this.id = id;
  }
  public void setNombre(String nombre) {
    this.nombre = nombre;
  }
  public void setIngrediente(String ingrediente) {
    this.ingrediente = ingrediente;
  }
  public void setPrecio(String precio) {
    this.precio = precio;
  }
  public void setCantidad(String cantidad) {
    this.cantidad = cantidad;
  }
}


