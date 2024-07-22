package com.example.reto01_pizzeria.adapter;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.example.reto01_pizzeria.R;
import com.example.reto01_pizzeria.model.Producto;

import java.util.List;

public class ProductoEditAdapter extends BaseAdapter{
  private List<Producto> productos; // Lista de productos a mostrar
  private LayoutInflater inflater; // Inflador de diseño para inflar vistas
  private ProductoEditAdapter.OnEditClickListener editClickListener; // Escucha para clics en el botón de editar
  private ProductoEditAdapter.OnDeleteClickListener deleteClickListener; // Escucha para clics en el botón de eliminar

  // Interfaz para escuchar clics en el botón de editar
  public interface OnEditClickListener {
    void onEditClick(int position);
  }

  // Interfaz para escuchar clics en el botón de eliminar
  public interface OnDeleteClickListener {
    void onDeleteClick(int position);
  }
  // Métodos para establecer los listeners
  public void setOnEditClickListener(ProductoEditAdapter.OnEditClickListener listener) {
    this.editClickListener = listener;
  }

  public void setOnDeleteClickListener(ProductoEditAdapter.OnDeleteClickListener listener) {
    this.deleteClickListener = listener;
  }
  // Constructor
  public ProductoEditAdapter(Context context, List<Producto> productos) {
    this.productos = productos;
    this.inflater = LayoutInflater.from(context);
  }
  // Devuelve el número de elementos en la lista
  @Override
  public int getCount() {
    return productos.size();
  }
  // Devuelve el producto en la posición especificada
  @Override
  public Producto getItem(int position) {
    return productos.get(position);
  }
  // Devuelve el ID del elemento en la posición especificada
  @Override
  public long getItemId(int position) {
    return position;
  }

  // Método principal para crear y actualizar las vistas de cada elemento en la lista
  @Override
  public View getView(int position, View convertView, ViewGroup parent) {
    ProductoEditAdapter.ViewHolder viewHolder;
    if (convertView == null) { // Si la vista no está inflada, inflarla
      convertView = inflater.inflate(R.layout.list_edit_producto, parent, false);
      viewHolder = new ProductoEditAdapter.ViewHolder(); // Crear un nuevo ViewHolder para almacenar las vistas
      viewHolder.txtId = convertView.findViewById(R.id.etId); // Asignar vistas a los elementos del ViewHolder
      viewHolder.txtNombre = convertView.findViewById(R.id.etNombre);
      viewHolder.txtIngrediente = convertView.findViewById(R.id.etIngredientes);
      viewHolder.txtPrecio = convertView.findViewById(R.id.etPrecio);
      viewHolder.txtCantidad = convertView.findViewById(R.id.etCantidad);
      viewHolder.btnEditar = convertView.findViewById(R.id.btnEditar); // Asignar botón de editar
      viewHolder.btnEliminar = convertView.findViewById(R.id.btnEliminar); // Asignar botón de eliminar
      convertView.setTag(viewHolder); // Establecer el ViewHolder como una etiqueta de la vista
    } else {
      viewHolder = (ProductoEditAdapter.ViewHolder) convertView.getTag(); // Si la vista ya está inflada, obtener el ViewHolder de la etiqueta
    }

    // Obtener el producto en la posición actual
    Producto producto = productos.get(position);

    // Establecer los valores de los TextView con la información del producto
    viewHolder.txtId.setText(String.valueOf("ID de producto: " + producto.getId()));
    viewHolder.txtNombre.setText("Nombre: "+ producto.getNombre());
    viewHolder.txtIngrediente.setText("Ingredientes: "+ producto.getIngrediente());
    viewHolder.txtPrecio.setText("Precio: "+ producto.getPrecio());
    viewHolder.txtCantidad.setText("Cantidad: "+ producto.getCantidad());

    // Establecer listeners para los botones de editar y eliminar
    viewHolder.btnEditar.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        if (editClickListener != null) {
          editClickListener.onEditClick(position);
        }
      }
    });

    viewHolder.btnEliminar.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        if (deleteClickListener != null) {
          deleteClickListener.onDeleteClick(position);
        }
      }
    });

    return convertView; // Devolver la vista actualizada
  }
  // Clase estática para almacenar vistas de elementos de la lista
  static class ViewHolder {
    TextView txtId;
    TextView txtNombre;
    TextView txtIngrediente;
    TextView txtPrecio;
    TextView txtCantidad;
    Button btnEditar;
    Button btnEliminar;
  }
}