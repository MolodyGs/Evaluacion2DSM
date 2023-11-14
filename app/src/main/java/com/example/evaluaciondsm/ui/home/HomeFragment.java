package com.example.evaluaciondsm.ui.home;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.core.database.sqlite.SQLiteDatabaseKt;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.evaluaciondsm.AdminSQLiteOpenHelper;
import com.example.evaluaciondsm.databinding.FragmentHomeBinding;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;

    private EditText codigo, precio, descripcion;
    int codigoAux;
    int precioAux;
    String descripcionAux;
    SQLiteDatabase db;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        codigo = (EditText) binding.txtCode;
        precio = (EditText) binding.txtPrice;
        descripcion = (EditText) binding.txtDescription;

        codigoAux = Integer.getInteger(codigo.getText().toString());
        precioAux = Integer.getInteger(precio.getText().toString());
        descripcionAux = descripcion.getText().toString();

        AdminSQLiteOpenHelper helper = new AdminSQLiteOpenHelper(getContext());
        db = helper.getWritableDatabase();

        binding.btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                save();
            }
        });

        binding.btnSearchP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                search();
            }
        });

        binding.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                delete();
            }
        });

        binding.btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                edit();
            }
        });

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    //Metodo para guardar los productos
    public void save() {

        codigoAux = Integer.getInteger(codigo.getText().toString());
        precioAux = Integer.getInteger(precio.getText().toString());
        descripcionAux = descripcion.getText().toString();

        if(codigo.getText().toString().isEmpty() || precio.getText().toString().isEmpty() || descripcion.getText().toString().isEmpty())
        {
            Toast.makeText(getContext(), "Todos los campos son obligatorios", Toast.LENGTH_LONG).show();
            return;
        }

        db.execSQL("INSERT INTO products (codigo, precio, descripcion) VALUES (" +
                ""+codigoAux+"," +precioAux+"," + descripcionAux + " )");
        Toast.makeText(getContext(), "Los datos fueron ingresados", Toast.LENGTH_LONG).show();

    }

    //Metodo para buscar productos
    public void search() {

        codigoAux = Integer.getInteger(codigo.getText().toString());
        precioAux = Integer.getInteger(precio.getText().toString());
        descripcionAux = descripcion.getText().toString();

        if(codigo.getText().toString().isEmpty())
        {
            Toast.makeText(getContext(), "Debe indicar un codigo", Toast.LENGTH_SHORT).show();
            return;
        }

        db.execSQL("SELECT * FROM products WHERE codigo = " + codigoAux);
        Toast.makeText(getContext(), "", Toast.LENGTH_SHORT).show();

    }

    public void delete() {

        codigoAux = Integer.getInteger(codigo.getText().toString());
        precioAux = Integer.getInteger(precio.getText().toString());
        descripcionAux = descripcion.getText().toString();

        if(codigo.getText().toString().isEmpty())
        {
            Toast.makeText(getContext(), "Debe indicar un codigo", Toast.LENGTH_SHORT).show();
            return;
        }

        db.execSQL("DELETE FROM products WHERE codigo = " + codigoAux);
        Toast.makeText(getContext(), "Elemento Eliminado Exitosamente", Toast.LENGTH_SHORT).show();
    }

    public void edit() {

        if(codigo.getText().toString().isEmpty() || precio.getText().toString().isEmpty() || descripcion.getText().toString().isEmpty())
        {
            Toast.makeText(getContext(), "Debe indicar un codigo", Toast.LENGTH_LONG).show();
            return;
        }

        db.execSQL("UPDATE products SET precio = " + precioAux + "WHERE codigo = " + codigoAux);
        db.execSQL("UPDATE products SET descripcion = " + descripcion + "WHERE codigo = " + codigoAux);
        Toast.makeText(getContext(), "Los datos fueron modificados exitosamente", Toast.LENGTH_LONG).show();
    }

    public void cleanForm() {
    }

}