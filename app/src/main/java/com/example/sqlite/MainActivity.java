package com.example.sqlite;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.Toast;

import com.example.sqlite.Adaptador;
import com.example.sqlite.Contacto;
import com.example.sqlite.daoContacto;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    daoContacto dao;
    Adaptador adaptador;
    ArrayList<Contacto>listacontact;
    Contacto c;
    RatingBar ratingBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dao= new daoContacto(MainActivity.this);
        listacontact = dao.verTodo();
        adaptador=new Adaptador(listacontact, dao, this);
        ListView list= findViewById(R.id.lis);
        Button insertar = findViewById(R.id.btn_insertar);
        list.setAdapter(adaptador);
        ratingBar.getRating();

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

            }
        });
        insertar.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Dialog dialog = new Dialog(Encuestas.this);
                dialog.setTitle("Nuevo Registro");
                dialog.setCancelable(true);
                dialog.setContentView(R.layout.dialogo);
                dialog.show();


                final EditText nombre = dialog.findViewById(R.id.et_nombre);
                final EditText email = dialog.findViewById(R.id.et_email);
                final EditText libreria = dialog.findViewById(R.id.et_libreria);
                final RatingBar ratingBar1 = dialog.findViewById(R.id.et_calificacion);
                final EditText descripcion = dialog.findViewById(R.id.et_descripcion);
                Button guardar = dialog.findViewById(R.id.btn_add);
                guardar.setText("Agregar");
                Button cancelar = dialog.findViewById(R.id.btn_cancel);


                guardar.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        try {
                            float calificacion = ratingBar1.getRating();

                            c = new Contacto(
                                    nombre.getText().toString(),
                                    email.getText().toString(),
                                    libreria.getText().toString(),
                                    descripcion.getText().toString(),
                                    calificacion);

                            dao.insertar(c);
                            listacontact = dao.verTodo();
                            adaptador.notifyDataSetChanged();
                            dialog.dismiss();
                        }catch (Exception e){
                            Toast.makeText(getApplication(), "ERROR", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                cancelar.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });
            }
        });

    }
}