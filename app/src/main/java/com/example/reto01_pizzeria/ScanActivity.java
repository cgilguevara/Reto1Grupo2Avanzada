package com.example.reto01_pizzeria;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.example.reto01_pizzeria.adapter.ProductoEditAdapter;
import com.example.reto01_pizzeria.dao.ProductoDAO;
import com.example.reto01_pizzeria.model.Producto;

import java.util.List;

public class ScanActivity extends AppCompatActivity {
  private ProductoDAO productoDAO;

  private EditText etlNombre, etlIngrediente, etlPrecio, etlCantidad;
  private Button btnScan, btnHome;
  private ListView listView;
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    EdgeToEdge.enable(this);
    setContentView(R.layout.activity_scan);
// Inicialización de la base de datos y conexión
    productoDAO = new ProductoDAO(this);
    productoDAO.abrir();

    // Obtención de referencias a elementos de la interfaz de producto
    etlNombre = findViewById(R.id.etlNombre);
    etlIngrediente = findViewById(R.id.etlIngrediente);
    etlPrecio = findViewById(R.id.etlPrecio);
    etlCantidad = findViewById(R.id.etlCantidad);
    btnScan = findViewById(R.id.btnScan);
    btnHome = findViewById(R.id.btnHome);
    listView = findViewById(R.id.listView);

    // Configuración de listener para el botón de agregar
    btnScan.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        agregarProducto();
      }
    });

    // Configuración de listener para el botón de mostrar
    btnHome.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        Intent intent = new Intent(ScanActivity.this, HomeActivity.class);
        startActivity(intent);
        finish(); // Finaliza esta actividad para que el usuario no pueda volver atrás
      }
    });

  }

  // Método para agregar un producto
  private void agregarProducto() {
    String nombre = etlNombre.getText().toString().trim();
    String ingrediente = etlIngrediente.getText().toString().trim();
    String precio = etlPrecio.getText().toString().trim();
    String cantidad = etlCantidad.getText().toString().trim();

    // Validación de campos
    if (!nombre.isEmpty() && !ingrediente.isEmpty() && !precio.isEmpty() && !cantidad.isEmpty()) {
      // Inserción del producto en la base de datos
      long resultado = productoDAO.insertarProducto(nombre, ingrediente, precio, cantidad);
      if (resultado != -1) {
        Toast.makeText(ScanActivity.this, "Producto agregado correctamente", Toast.LENGTH_SHORT).show();
        etlNombre.setText("");
        etlIngrediente.setText("");
        etlPrecio.setText("");
        etlCantidad.setText("");
      } else {
        Toast.makeText(ScanActivity.this, "Error al agregar producto", Toast.LENGTH_SHORT).show();
      }
    } else {
      Toast.makeText(ScanActivity.this, "Por favor, complete todos los campos", Toast.LENGTH_SHORT).show();
    }
    // Actualización de la lista de productos después de agregar uno nuevo
    actualizarListaProductos();
  }

  // Método para mostrar productos
  private void mostrarProductos() {
    // Obtención de la lista de productos desde la base de datos
    List<Producto> productos = productoDAO.obtenerTodosProductos();
    // Construcción de una cadena con los detalles de los productos
    StringBuilder stringBuilder = new StringBuilder();
    for (Producto producto : productos) {
      stringBuilder.append("ID: ").append(producto.getId()).append(", Nombre: ").append(producto.getNombre().toString()).append(", Ingrediente: ").append(producto.getIngrediente().toString()).append(", Precio: ").append(producto.getPrecio().toString()).append(", Cantidad: ").append(producto.getCantidad().toString()).append("\n");
    }
    // Creación de un adaptador personalizado y configuración del ListView
    ProductoEditAdapter adapter = new ProductoEditAdapter(this, productos);
    listView.setAdapter(adapter);

    // Configuración de listeners para el adaptador
    adapter.setOnEditClickListener(new ProductoEditAdapter.OnEditClickListener() {
      @Override
      public void onEditClick(int position) {
        // Lógica para editar un producto
        Producto producto = productos.get(position);
        etlNombre.setText(producto.getNombre());
        etlIngrediente.setText(producto.getIngrediente());
        etlPrecio.setText(producto.getPrecio());
        etlCantidad.setText(producto.getCantidad());
        int idProducto = producto.getId();
        btnScan.setText("Guardar");
        btnScan.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
            actualizarProducto(idProducto);
          }
        });
      }
    });

    adapter.setOnDeleteClickListener(new ProductoEditAdapter.OnDeleteClickListener() {
      @Override
      public void onDeleteClick(int position) {
        // Lógica para eliminar un producto
        Producto producto = productos.get(position);
        eliminarProducto(producto.getId());
        adapter.notifyDataSetChanged();
      }
    });
  }

  // Método para actualizar un producto
  private void actualizarProducto(int idProducto) {
    String nombre = etlNombre.getText().toString().trim();
    String ingrediente = etlIngrediente.getText().toString().trim();
    String precio = etlPrecio.getText().toString().trim();
    String cantidad = etlCantidad.getText().toString().trim();

    if (!nombre.isEmpty() && !ingrediente.isEmpty() && !precio.isEmpty() && !cantidad.isEmpty()) {
      // Actualización de los datos del producto la base de datos
      productoDAO.actualizarProducto(idProducto, nombre, ingrediente, precio, cantidad);
      Toast.makeText(ScanActivity.this, "Producto actualizado correctamente", Toast.LENGTH_SHORT).show();
      etlNombre.setText("");
      etlIngrediente.setText("");
      etlPrecio.setText("");
      etlCantidad.setText("");
      btnScan.setText("Agregar");
      btnScan.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
          agregarProducto();
        }
      });
    } else {
      Toast.makeText(ScanActivity.this, "Por favor, complete todos los campos", Toast.LENGTH_SHORT).show();
    }
    // Actualización de la lista de productoss después de la actualización
    actualizarListaProductos();
  }

  // Método para eliminar un producto
  private void eliminarProducto(int idProducto) {
    // Eliminación del producto de la base de datos
    productoDAO.eliminarProducto(idProducto);
    Toast.makeText(ScanActivity.this, "Producto eliminado correctamente", Toast.LENGTH_SHORT).show();
    // Actualización de la lista de productoss después de eliminar uno
    actualizarListaProductos();
  }

  // Método para actualizar la lista de productos
  private void actualizarListaProductos() {
    mostrarProductos();
  }

  @Override
  protected void onDestroy() {
    super.onDestroy();
    // Cierre de la conexión con la base de datos al destruir la actividad
    productoDAO.cerrar();
  }

 }