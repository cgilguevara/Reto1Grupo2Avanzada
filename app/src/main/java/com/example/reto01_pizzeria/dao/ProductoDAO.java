package com.example.reto01_pizzeria.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;


import com.example.reto01_pizzeria.database.DatabaseHelper;
import com.example.reto01_pizzeria.model.Producto;

public class ProductoDAO {

  private SQLiteDatabase db;// Objeto para interactuar con la base de datos
  private DatabaseHelper dbHelper; // Instancia de DatabaseHelper para crear y actualizar la base de datos
  // Constructor que recibe el contexto de la aplicación y crea una instancia de DatabaseHelper
  public ProductoDAO(Context context){
    dbHelper = new DatabaseHelper(context);
  }
  // Método para abrir la conexión con la base de datos en modo escritura
  public void abrir() {
    db = dbHelper.getWritableDatabase();

  }
  // Método para cerrar la conexión con la base de datos
  public void cerrar() {
    dbHelper.close();
  }
  // Método para insertar un nuevo producto en la tabla 'productos'
  public long insertarProducto(String nombre, String ingrediente, String precio, String cantidad) {
    ContentValues values = new ContentValues(); // Objeto para almacenar los valores a insertar
    values.put("nombre", nombre); // Inserción del nombre del producto
    values.put("ingrediente", ingrediente); // Inserción del ingrediente del producto
    values.put("precio", precio); // Inserción del precio del producto
    values.put("cantidad", cantidad); // Inserción del cantidad del producto
    return db.insert("productos", null, values); // Ejecución de la inserción y retorno del ID del nuevo registro
  }

  // Método para obtener todos los   productos de la tabla 'productos'
  public List<Producto> obtenerTodosProductos() {
    List<Producto> productos = new ArrayList<>(); // Lista para almacenar los productoss obtenidos
    Cursor cursor = db.rawQuery("SELECT * FROM productos", null); // Ejecución de la consulta SQL

    // Iteración sobre los resultados del cursor para obtener los datos de cada producto
    if (cursor.moveToFirst()) {
      do {
        Producto producto = new Producto(); // Creación de una nueva instancia de Producto
        producto.setId(cursor.getInt(0)); // Asignación del ID del producto
        producto.setNombre(cursor.getString(1)); // Asignación del nombre del producto
        producto.setIngrediente(cursor.getString(2)); // Asignación del ngrediente del producto
        producto.setPrecio(String.valueOf(cursor.getFloat(3))); // Asignación del precio del producto
        producto.setCantidad(String.valueOf(cursor.getInt(4))); // Asignación del cantidad del producto
        productos.add(producto); // Agregación del producto a la lista
      } while (cursor.moveToNext());
    }
    cursor.close(); // Cierre del cursor
    return productos; // Retorno de la lista de productos
  }

  // Método para actualizar los datos de un producto en la tabla 'productoss'
  public void actualizarProducto(int id, String nombre, String ingrediente, String precio, String cantidad) {
    ContentValues values = new ContentValues(); // Objeto para almacenar los nuevos valores
    values.put("nombre", nombre); // Asignación del nuevo nombre
    values.put("ingrediente", ingrediente); // Asignación del nuevo ingrediente
    values.put("precio", precio); // Asignación del nuevo precio
    values.put("cantidad", cantidad); // Asignación del nuevo cantidad
    // Actualización del producto con el ID proporcionado
    db.update("productos", values, "id = ?", new String[]{String.valueOf(id)});
  }

  // Método para eliminar un producto de la tabla 'productos' mediante su ID
  public void eliminarProducto(int id) {
    // Eliminación del producto con el ID proporcionado
    db.delete("productos", "id = ?", new String[]{String.valueOf(id)});
  }
}