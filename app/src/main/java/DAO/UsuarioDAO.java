package DAO;

import android.app.Activity;
import android.content.ContentValues;
import android.database.Cursor;

import java.util.ArrayList;
import java.util.List;

import Infraestructura.Conexion;
import Modelo.Usuario;

/**
 * Created by Johnny on 13/01/2017.
 */
public class UsuarioDAO {

    Conexion conex;

    public UsuarioDAO(Activity activity) {
        // Creacion o conexion de la base de datos en caso de que no exista,
        // ademas se indica el # de la version anterior y la actual
        conex = new Conexion(activity);
    }

    public boolean guardar(Usuario usuario) {
        // Objeto que contendra la info a almacenar
        ContentValues registro = new ContentValues();
        registro.put("cedula", usuario.getCedula());
        registro.put("nombre", usuario.getNombre());
        registro.put("apellido", usuario.getApellido());
        registro.put("edad", usuario.getEdad());
        registro.put("genero", usuario.getGenero());
        return conex.ejecutarInsert("usuario", registro);
    }

    public Usuario buscar(String cedula) {
        Usuario usuario = null;
        String consulta = "select nombre,apellido,edad,genero  from usuario where "
                + "cedula=" + cedula + "";
        Cursor temp = conex.ejecutarSearch(consulta);

        // El resultado tiene mas de un registro?
        if (temp.getCount() > 0) {
            temp.moveToFirst();
            usuario = new Usuario(cedula, temp.getString(0),
                    temp.getString(1),
                    Integer.parseInt(temp.getString(2)),
                    Integer.parseInt(temp.getString(3)));
        }

        conex.cerrarConexion();
        return usuario;
    }

    public boolean eliminar(Usuario usuario) {
        String tabla = "usuario";
        String condicion = "cedula=" + usuario.getCedula() + "";
        return conex.ejecutarDelete(tabla, condicion);
    }

    public boolean modificar(Usuario usuario) {
        String tabla = "usuario";
        String condicion = "cedula=" + usuario.getCedula();

        ContentValues registro = new ContentValues();

        registro.put("nombre", usuario.getNombre());
        registro.put("apellido", usuario.getApellido());
        registro.put("edad", usuario.getEdad());

        return conex.ejecutarUpdate(tabla, condicion, registro);
    }



    public List<Usuario> listar() {
        List<Usuario> listaUsuarios = new ArrayList<Usuario>(); // Select All
        // Query
        String consulta = "select cedula,nombre,apellido,edad,genero from usuario";
        Cursor temp = conex.ejecutarSearch(consulta);

        // looping through all rows and adding to list
        if (temp.moveToFirst()) {
            do {
                Usuario usuario = new Usuario(temp.getString(0),
                        temp.getString(1), temp.getString(2),
                        Integer.parseInt(temp.getString(3)),Integer.parseInt(temp.getString(4)));
                listaUsuarios.add(usuario);
            } while (temp.moveToNext());
        }
        return listaUsuarios;
    }


}
