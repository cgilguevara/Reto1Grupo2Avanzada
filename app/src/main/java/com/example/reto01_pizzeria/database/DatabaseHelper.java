package com.example.reto01_pizzeria.database;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {
  private static final String DATABASE_NAME = "miBaseDeDatos";
  private static final int DATABASE_VERSION = 1;
  // Se define una sentencia SQL para crear una tabla llamada "productos"
  // y se asigna la sentencia SQL a una constante CREATE_TABLE_USERS:
  private static final String query = "CREATE TABLE productos (id INTEGER PRIMARY KEY AUTOINCREMENT, nombre TEXT, ingrediente TEXT, precio REAL, cantidad INTEGER)";
  private static final String CREATE_TABLE_USERS = query;
  //	Se define el constructor de la clase DatabaseHelper que recibe un contexto
  public DatabaseHelper(Context context)    {
    super(context, DATABASE_NAME, null, DATABASE_VERSION);
  }

  @Override
  public void onCreate(SQLiteDatabase db) {
    // Se ejecuta la sentencia SQL de creación de la tabla
    db.execSQL(CREATE_TABLE_USERS);
  }
  @Override
  public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    // Se elimina la versión anterior de la tabla
    db.execSQL("DROP TABLE IF EXISTS productos");
    // Se crea la nueva versión de la tabla
    onCreate(db);
  }
}

