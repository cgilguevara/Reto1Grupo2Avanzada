package com.example.reto01_pizzeria;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.example.reto01_pizzeria.dao.ProductoDAO;

public class HomeActivity extends AppCompatActivity {
  // Declaración de variables
  private ProductoDAO productoDAO;

  private Button btnAgregar, btnMostrar, btnbuscar, btneditar, btnLogin;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    EdgeToEdge.enable(this);
    setContentView(R.layout.activity_home);
// Inicialización de la base de datos y conexión
    productoDAO = new ProductoDAO(this);
    productoDAO.abrir();

    // Obtención de referencias a elementos de la interfaz de producto
    btneditar = findViewById(R.id.btneditar);
    btnbuscar = findViewById(R.id.btnbuscar);
    btnAgregar = findViewById(R.id.btnAgregar);
    btnMostrar = findViewById(R.id.btnMostrar);
    btnLogin = findViewById(R.id.btnLogin);
    btnAgregar.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        Intent intent = new Intent(HomeActivity.this, AddActivity.class);
        startActivity(intent);
        finish(); // Finaliza esta actividad para que el usuario no pueda volver atrás
      }
    });

    btnMostrar.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        Intent intent = new Intent(HomeActivity.this, ListarActivity.class);
        startActivity(intent);
        finish(); // Finaliza esta actividad para que el usuario no pueda volver atrás
      }
    });

    btnbuscar.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        Intent intent = new Intent(HomeActivity.this, ScanActivity.class);
        startActivity(intent);
        finish(); // Finaliza esta actividad para que el usuario no pueda volver atrás
      }
    });

    btneditar.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        Intent intent = new Intent(HomeActivity.this, EditActivity.class);
        startActivity(intent);
        finish(); // Finaliza esta actividad para que el usuario no pueda volver atrás
      }
    });

    btnLogin.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        Intent intent = new Intent(HomeActivity.this, LoginActivity.class);
        startActivity(intent);
        finish(); // Finaliza esta actividad para que el usuario no pueda volver atrás
      }
    });

  }
}