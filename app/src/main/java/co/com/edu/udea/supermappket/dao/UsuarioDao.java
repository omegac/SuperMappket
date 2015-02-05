package co.com.edu.udea.supermappket.dao;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import co.com.edu.udea.supermappket.dto.UsuUsuario;

/**
 * Created by Alan on 01/01/2015.
 */
public class UsuarioDao {

    public static final String tablaUsuario = "USU_USUARIO";
    public static final String colUSUId = "USU_Id";
    public static final String colUSUNombre = "USU_Nombre";
    public static final String colUSUContrasena = "USU_Contrasena";
    private String[] columnas = new String[]{colUSUId, colUSUNombre, colUSUContrasena};
    public static final String crearTablaUSU = "CREATE TABLE " + tablaUsuario + " (" +
            colUSUId + " INTEGER PRIMARY KEY AUTOINCREMENT," +
            colUSUNombre + " TEXT NOT NULL," +
            colUSUContrasena + " TEXT NOT NULL);";

    public ContentValues genContentValues(String nombre, String Password){

        ContentValues cv = new ContentValues();
        cv.put(colUSUNombre, nombre);
        cv.put(colUSUContrasena, Password);
        return cv;
    }

    public void insertar(SQLiteDatabase db, UsuUsuario usuario){
        try {
            db.insert(tablaUsuario, null, genContentValues(usuario.getUsUser(), usuario.getUsPassword()));
            System.out.print("Inserte carajo!!!");
        } catch (Exception e) {
            System.out.print("Error insertando4");
            e.printStackTrace();
        }
    }

    public Cursor obtener(SQLiteDatabase db, String nombre, String contrasena){

        return db.query(tablaUsuario, columnas, colUSUNombre + "=? AND " + colUSUContrasena + "=?",
                new String[]{nombre, contrasena},null,null,null);
    }
}
