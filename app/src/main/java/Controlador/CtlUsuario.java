package Controlador;

import android.app.Activity;

import java.util.List;

import DAO.UsuarioDAO;
import Modelo.Usuario;

/**
 * Created by Johnny on 13/01/2017.
 */
public class CtlUsuario {

    UsuarioDAO dao;

    public CtlUsuario(Activity activity) {
        dao = new UsuarioDAO(activity);
    }

    public boolean guardarUsuario(String cedula, String nombre,
                                  String apellido, int edad, int genero) {
        Usuario usuario = new Usuario(cedula, nombre, apellido, edad, genero);
        return dao.guardar(usuario);
    }

    public Usuario buscarUsuario(String cedula) {
        return dao.buscar(cedula);
    }

    public boolean eliminarUsuario(String cedula) {
        Usuario usuario = new Usuario(cedula, "", "", 0,0);
        return dao.eliminar(usuario);
    }

    public boolean modificarUsuario(String cedula, String nombre,
                                    String apellido, int edad, int genero) {
        Usuario usuario = new Usuario(cedula, nombre, apellido, edad, genero);
        return dao.modificar(usuario);
    }

    public List<Usuario> listarUsuario() {
        return dao.listar();
    }



}
