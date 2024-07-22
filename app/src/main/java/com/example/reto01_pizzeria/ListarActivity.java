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

public class ListarActivity extends AppCompatActivity {
  private ProductoDAO productoDAO;

  private EditText etlNombre, etlIngrediente, etlPrecio, etlCantidad;
  private Button btnHome;
  private ListView listView;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    EdgeToEdge.enable(this);
    setContentView(R.layout.activity_listar);
// Inicialización de la base de datos y conexión
    productoDAO = new ProductoDAO(this);
    productoDAO.abrir();

    // Obtención de referencias a elementos de la interfaz de producto
    etlNombre = findViewById(R.id.etlNombre);
    etlIngrediente = findViewById(R.id.etlIngrediente);
    etlPrecio = findViewById(R.id.etlPrecio);
    etlCantidad = findViewById(R.id.etlCantidad);
    btnHome = findViewById(R.id.btnHome);
//    btnMostrar = findViewById(R.id.btnMostrar);
    listView = findViewById(R.id.listView);

    // Configuración de listener para el botón de mostrar
    btnHome.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        Intent intent = new Intent(ListarActivity.this, HomeActivity.class);
        startActivity(intent);
        finish(); // Finaliza esta actividad para que el usuario no pueda volver atrás
      }
    });
    mostrarProductos();
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
  }

  @Override
  protected void onDestroy () {
      super.onDestroy();
      // Cierre de la conexión con la base de datos al destruir la actividad
      productoDAO.cerrar();
  }
}
