package co.com.edu.udea.supermappket.dao;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import co.com.edu.udea.supermappket.dto.CatCategoria;

/**
 * Created by Alan on 02/01/2015.
 */
public class CategoriaDao {

    /**
     * Propiedades tabla CAT_Categoria
     */
    public static final String tablaCategoria = "CAT_Categoria";
    public static final String colCATId = "CAT_Id";
    public static final String colCATNombre = "CAT_Nombre";
    public static final String colCATDescripcion = "CAT_Descripcion";
    private String[] columnas = new String[]{colCATId, colCATNombre, colCATDescripcion};
    public static final String crearTablaCAT = "CREATE TABLE " + tablaCategoria + " (" +
            colCATId + " INTEGER PRIMARY KEY AUTOINCREMENT," +
            colCATNombre + " TEXT NOT NULL," +
            colCATDescripcion + " TEXT);";

    public ContentValues genContentValues(String nombre, String Descripcion){

        ContentValues cv = new ContentValues();
        cv.put(colCATNombre, nombre);
        cv.put(colCATDescripcion, Descripcion);
        return cv;
    }

    public void insertar(SQLiteDatabase db, CatCategoria categoria){
        try {
            db.insert(tablaCategoria, null, genContentValues(categoria.getcatNombre(), categoria.getCatDescripcion()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Cursor obtenerCategoria(SQLiteDatabase db, String nombre){

        return db.query(tablaCategoria, columnas, colCATNombre + "=?", new String[]{nombre},null,null,null);
    }

    public Cursor obtenerCategorias(SQLiteDatabase db){

        return db.query(tablaCategoria, columnas, null, null, null, null, null);
    }
}
