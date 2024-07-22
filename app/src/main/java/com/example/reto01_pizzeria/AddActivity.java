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
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.reto01_pizzeria.adapter.ProductoListAdapter;
import com.example.reto01_pizzeria.dao.ProductoDAO;
import com.example.reto01_pizzeria.model.Producto;

import java.util.List;

public class AddActivity extends AppCompatActivity {
  private ProductoDAO productoDAO;

  private EditText etlNombre, etlIngrediente, etlPrecio, etlCantidad;
  private Button btnAgregar, btnHome;
  private ListView listView;
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    EdgeToEdge.enable(this);
    setContentView(R.layout.activity_add);
// Inicialización de la base de datos y conexión
    productoDAO = new ProductoDAO(this);
    productoDAO.abrir();

    // Obtención de referencias a elementos de la interfaz de producto
    etlNombre = findViewById(R.id.etlNombre);
    etlIngrediente = findViewById(R.id.etlIngrediente);
    etlPrecio = findViewById(R.id.etlPrecio);
    etlCantidad = findViewById(R.id.etlCantidad);
    btnAgregar = findViewById(R.id.btnAgregar);
    btnHome = findViewById(R.id.btnHome);
    listView = findViewById(R.id.listView);

    // Configuración de listener para el botón de agregar
    btnAgregar.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        agregarProducto();
      }
    });

    // Configuración de listener para el botón de mostrar
    btnHome.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        Intent intent = new Intent(AddActivity.this, HomeActivity.class);
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
        Toast.makeText(AddActivity.this, "Producto agregado correctamente", Toast.LENGTH_SHORT).show();
        etlNombre.setText("");
        etlIngrediente.setText("");
        etlPrecio.setText("");
        etlCantidad.setText("");
      } else {
        Toast.makeText(AddActivity.this, "Error al agregar producto", Toast.LENGTH_SHORT).show();
      }
    } else {
      Toast.makeText(AddActivity.this, "Por favor, complete todos los campos", Toast.LENGTH_SHORT).show();
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
    ProductoListAdapter adapter = new ProductoListAdapter(this, productos);
    listView.setAdapter(adapter);

    // Configuración de listeners para el adaptador
    adapter.setOnEditClickListener(new ProductoListAdapter.OnEditClickListener() {
      @Override
      public void onEditClick(int position) {
        // Lógica para editar un producto
        Producto producto = productos.get(position);
        etlNombre.setText(producto.getNombre());
        etlIngrediente.setText(producto.getIngrediente());
        etlPrecio.setText(producto.getPrecio());
        etlCantidad.setText(producto.getCantidad());
        int idProducto = producto.getId();
        btnAgregar.setText("Guardar");
        btnAgregar.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
            actualizarProducto(idProducto);
          }
        });
      }
    });

    adapter.setOnDeleteClickListener(new ProductoListAdapter.OnDeleteClickListener() {
      @Override
      public void onDeleteClick(int position) {
        // Lógica para eliminar un producto
        Producto producto = productos.get(position);
        eliminarProducto(producto.getId());
        adapter.notifyDataSetChanged();
      }
    });

  }

  private void actualizarProducto(int idProducto) {
    String nombre = etlNombre.getText().toString().trim();
    String ingrediente = etlIngrediente.getText().toString().trim();
    String precio = etlPrecio.getText().toString().trim();
    String cantidad = etlCantidad.getText().toString().trim();

    if (!nombre.isEmpty() && !ingrediente.isEmpty()) {
      // Actualización de los datos del usuario en la base de datos
      productoDAO.actualizarProducto(idProducto, nombre, ingrediente, precio, cantidad);
      Toast.makeText(AddActivity.this, "Usuario actualizado correctamente", Toast.LENGTH_SHORT).show();
      etlNombre.setText("");
      etlIngrediente.setText("");
      etlPrecio.setText("");
      etlCantidad.setText("");
      btnAgregar.setText("Agregar");
      btnAgregar.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
          agregarProducto();
        }
      });
    } else {
      Toast.makeText(AddActivity.this, "Por favor, complete todos los campos", Toast.LENGTH_SHORT).show();
    }
    // Actualización de la lista de usuarios después de la actualización
    actualizarListaProductos();
  }

  // Método para eliminar un usuario
  private void eliminarProducto(int idProducto) {
    // Eliminación del usuario de la base de datos
    productoDAO.eliminarProducto(idProducto);
    Toast.makeText(AddActivity.this, "Usuario eliminado correctamente", Toast.LENGTH_SHORT).show();
    // Actualización de la lista de usuarios después de eliminar uno
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
