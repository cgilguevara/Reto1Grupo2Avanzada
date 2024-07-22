package com.example.reto01_pizzeria;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {
  private FirebaseAuth mAuth;
  private EditText editTextEmail;
  private EditText editTextPassword;
  private Button btnLogin;
  private Button btnRegister;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    EdgeToEdge.enable(this);
    setContentView(R.layout.activity_login);
    // Inicialización de Firebase Authentication
    mAuth = FirebaseAuth.getInstance();

    // Vinculación de las vistas con los objetos correspondientes
    editTextEmail = findViewById(R.id.editTextEmail);
    editTextPassword = findViewById(R.id.editTextPassword);
    btnLogin = findViewById(R.id.btnLogin);
    btnRegister = findViewById(R.id.btnRegister);

    // Configuración del botón de registro --> abrir la actividad de registro
    btnRegister.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
        startActivity(intent);
        finish(); // Finaliza esta actividad para que el usuario no pueda volver atrás
      }
    });

    // Configuración del botón de inicio de sesión --> llama al método loginUser()
    btnLogin.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        loginUser();
      }
    });
  }

  // Método para iniciar sesión
  private void loginUser() {
    String email = editTextEmail.getText().toString().trim();
    String password = editTextPassword.getText().toString().trim();

    // Validación de campos de entrada
    if (email.isEmpty() || password.isEmpty()) {
      Toast.makeText(this, "Por favor, completa todos los campos", Toast.LENGTH_SHORT).show();
      return;
    }

    // Iniciar sesión con Firebase Authentication
    mAuth.signInWithEmailAndPassword(email, password)
        .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
          @Override
          public void onComplete(@NonNull Task<AuthResult> task) {
            if (task.isSuccessful()) {
              // Inicio de sesión exitoso, mostrar mensaje de éxito y redirigir a la actividad principal HomeActivity
              Toast.makeText(LoginActivity.this, "Inicio de sesión exitoso", Toast.LENGTH_SHORT).show();
              Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
              startActivity(intent);
              finish(); // Cierra esta actividad para que el usuario no pueda volver atrás
            } else {
              // Si falla el inicio de sesión, mostrar mensaje de error
              Toast.makeText(LoginActivity.this, "Error al iniciar sesión", Toast.LENGTH_SHORT).show();
            }
          }
        });
  }
}