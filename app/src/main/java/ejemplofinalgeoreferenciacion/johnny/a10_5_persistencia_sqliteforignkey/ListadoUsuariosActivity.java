package ejemplofinalgeoreferenciacion.johnny.a10_5_persistencia_sqliteforignkey;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import Controlador.CtlUsuario;
import Modelo.Usuario;

public class ListadoUsuariosActivity extends AppCompatActivity {

    private ListView lstUsuarios;

    CtlUsuario controlador;
    List<Usuario> listaUsuarios;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listado_usuarios);
        controlador = new CtlUsuario(this);
        lstUsuarios = (ListView) findViewById(R.id.lstUsuarios);
        configurarLista();
    }


    public void configurarLista() {

        listaUsuarios = controlador.listarUsuario();
        List<String> listaAdapter = new ArrayList<String>();
        ArrayAdapter<String> adapter;

        if (listaUsuarios.size() > 0) {

            for (int x = 0; x < listaUsuarios.size(); x++) {
                listaAdapter.add(listaUsuarios.get(x).getCedula() + "-"
                        + listaUsuarios.get(x).getNombre() + "-"
                        + listaUsuarios.get(x).getApellido() + "-"
                        + listaUsuarios.get(x).getEdad());
            }

            adapter = new ArrayAdapter<String>(this,
                    android.R.layout.simple_list_item_1, listaAdapter);

        } else {

            listaAdapter.add("No hay registros en la base de datos");
            adapter = new ArrayAdapter<String>(this,
                    android.R.layout.simple_list_item_1, listaAdapter);
        }

        lstUsuarios.setAdapter(adapter);

        lstUsuarios.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View v,
                                    int posicion, long id) {
                mostrarInformacion(posicion);

            }
        });

    }

    public void mostrarInformacion(int pos) {
        Toast.makeText(this,
                "Cedula: "+ listaUsuarios.get(pos).getCedula()+" "+
                        "Nombre: "+ listaUsuarios.get(pos).getNombre()+" "+
                        "Apellido: "+ listaUsuarios.get(pos).getApellido()+" "+
                        "Edad: "+ listaUsuarios.get(pos).getEdad()+" ",
                Toast.LENGTH_SHORT).show();

    }

}
