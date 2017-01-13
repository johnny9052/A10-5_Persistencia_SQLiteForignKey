package Infraestructura;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Johnny on 13/01/2017.
 */
public class Conexion extends SQLiteOpenHelper {

    private static final String database = "administracionForeign8.db";
    private static final SQLiteDatabase.CursorFactory factory = null;
    private static final int version = 12;
    SQLiteDatabase bd;

    public Conexion(Context context, String name, SQLiteDatabase.CursorFactory factory,
                    int version) {
        super(context, name, factory, version);
    }

    public Conexion(Context context) {
        super(context, database, factory, version);
    }

    @Override
    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);
        if (!db.isReadOnly()) {
            // Enable foreign key constraints
            db.execSQL("PRAGMA foreign_keys=ON;");
            //db.setForeignKeyConstraintsEnabled(true);
        }
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table genero(id integer primary key,nombre text)");

        db.execSQL("insert into genero values(0,'Masculino')");
        db.execSQL("insert into genero values(1,'Femenino')");

        db.execSQL("create table usuario("
                + "cedula text primary key, "
                + "nombre text, "
                + "apellido text, "
                + "edad integer, "
                + "genero integer REFERENCES genero ON DELETE CASCADE)");


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int versionAnte, int versionNue) {
        db.execSQL("drop table if exists usuario");
        db.execSQL("drop table if exists genero");
        onCreate(db);
    }

    public void cerrarConexion() {
        bd.close();
    }

    public boolean ejecutarInsert(String tabla, ContentValues registro) {
        try {
            // Objeto para lectura y escritura en la base de datos
            bd = this.getWritableDatabase();
            // Se hace un insert indicando la tabla y mandando los datos, el
            // null es los campos que no se van a registrar
            int res = (int) bd.insert(tabla, null, registro);
            cerrarConexion();
            if (res != -1) {
                return true;
            } else {
                return false;
            }

        } catch (Exception e) {
            return false;
        }
    }

    public boolean ejecutarDelete(String tabla, String condicion) {
        bd = this.getWritableDatabase();
        int cant = bd.delete(tabla, condicion, null);
        cerrarConexion();

        if (cant >= 1) {
            return true;
        } else {
            return false;
        }
    }




    public boolean ejecutarUpdate(String tabla, String condicion,
                                  ContentValues registro) {
        try {

            bd = this.getWritableDatabase();

            int cant = bd.update(tabla, registro, condicion, null);

            cerrarConexion();

            if (cant == 1) {
                return true;
            } else {
                return false;
            }

        } catch (Exception e) {
            return false;
        }
    }

    public Cursor ejecutarSearch(String consulta) {
        try {
            // Objeto para lectura y escritura en la base de datos
            bd = this.getWritableDatabase();

            // Definimos un objeto de tipo cursor que almacena la info de la
            // base de datos, ademas ejecutamos una consulta sql
            Cursor fila = bd.rawQuery(consulta, null);

            return fila;

        } catch (Exception e) {
            cerrarConexion();
            return null;
        }
    }
}
