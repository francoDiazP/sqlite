package com.example.sqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

public class daoContacto {
    //instanciar la base de datos

    SQLiteDatabase db;
    //Almacenar una lista de objetos
    ArrayList<Contacto>lista = new ArrayList<Contacto>();
    //Instanciar clase Contacto
    Contacto c;
    //Contexto de la aplicacion
    Context cn;
    //Nombre db
    String nombreDB= "DBContacts";
    //Se crea la cadena de sentencia sql para crear las tablas
    String tabla = "create table if not exists contacto(id integer primary key autoincrement, nombre text, apellido text, email text, telefono text, ciudad text)";

    //Constructor de la clase
    public daoContacto(Context c){
        this.cn=c;
        db=c.openOrCreateDatabase(nombreDB,Context.MODE_PRIVATE,null);
        db.execSQL(tabla);
    }

    //Método para insertar contacto nuevo
    public boolean insertar(Contacto c){
        ContentValues container = new ContentValues();
        container.put("nombre",c.getNombre());
        container.put("apellido",c.getApellido());
        container.put("email",c.getEmail());
        container.put("telefono",c.getTelefono());
        container.put("ciudad",c.getCiudad());

        return (db.insert("contacto",null,container))>0;

    }

    public boolean eliminar(int id){
        return (db.delete("contacto","id="+id,null))>0;
    }

    public boolean editar(Contacto c){
        ContentValues container = new ContentValues();
        container.put("nombre",c.getNombre());
        container.put("apellido",c.getApellido());
        container.put("email",c.getEmail());
        container.put("telefono",c.getTelefono());
        container.put("ciudad",c.getCiudad());

        return (db.update("contacto",container,"id="+c.getId(),null))>0;

    }

    //Método para recuperar la lista de contacto
    public ArrayList<Contacto>verTodo(){
        lista.clear();
        Cursor cursor = db.rawQuery("select * from contacto",null);
        if(cursor!=null && cursor.getCount()>0){
            cursor.moveToFirst();

        }do{
            lista.add(new Contacto(cursor.getInt(0),
                    cursor.getString(1),cursor.getString(2),
                    cursor.getString(3),cursor.getString(4),
                    cursor.getString(5)));

        }while(cursor.moveToNext());
        return lista;
    }

    //Método para buscar solo 1
    public Contacto verUno(int posicion){
        Cursor cursor = db.rawQuery("select * from contacto",null);
        cursor.moveToPosition(posicion);
        c=new Contacto(cursor.getInt(0),
                cursor.getString(1),cursor.getString(2),
                cursor.getString(3),cursor.getString(4),
                cursor.getString(5));
        return c;
    }
}
