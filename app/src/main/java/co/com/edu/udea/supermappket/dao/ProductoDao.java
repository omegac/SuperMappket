package co.com.edu.udea.supermappket.dao;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import co.com.edu.udea.supermappket.dto.ProProducto;

/**
 * Created by Alan on 02/01/2015.
 */
public class ProductoDao {

    /**
     * Propiedades tabla PRO_Producto
     */
    public static final String tablaProducto = "PRO_Producto";
    public static final String colPRONombre = "PRO_Nombre";
    public static final String colPROId = "PRO_Id";
    public static final String colPROPrecio = "PRO_Precio";
    public static final String colPRODescripcion = "PRO_Descripcion";
    public static final String colPROCATId = "PRO_CAT_Id";
    public static final String colPROIcono = "PRO_Icono";
    private String[] columnas = new String[]{colPROId, colPRONombre, colPROPrecio, colPRODescripcion, colPROCATId, colPROIcono};
    public static final String crearTablaPRO = "CREATE TABLE " + tablaProducto + " (" +
            colPROId + " INTEGER PRIMARY KEY AUTOINCREMENT," +
            colPRONombre + " TEXT NOT NULL," +
            colPROPrecio + " TEXT NOT NULL," +
            colPRODescripcion + " TEXT," +
            colPROCATId + " INTEGER," +
            colPROIcono + " TEXT NOT NULL," +
            " FOREIGN KEY(" + colPROCATId + ") REFERENCES " + CategoriaDao.tablaCategoria + "(" + CategoriaDao.colCATId + "));";

    public ContentValues genContentValues(String nombre, double precio, String descripcion, int catId, String nombreIcono){

        ContentValues cv = new ContentValues();
        cv.put(colPRONombre, nombre);
        cv.put(colPROPrecio, precio);
        cv.put(colPRODescripcion, descripcion);
        cv.put(colPROCATId, catId);
        cv.put(colPROIcono, nombreIcono);
        return cv;
    }

    public void insertar(SQLiteDatabase db, ProProducto producto){
        try {
            db.insert(tablaProducto, null, genContentValues(producto.getProNombre(), producto.getProPrecio(),
                    producto.getProDescripcion(), producto.getCategoria(), producto.getProIcono()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Cursor obtenerPorId(SQLiteDatabase db, int id){

        return db.query(tablaProducto, columnas, colPROId + "=?", new String[]{Integer.toString(id)},null,null,null);
    }

    public Cursor obtenerPorNombre(SQLiteDatabase db, String nombre){

        return db.query(tablaProducto, columnas, colPRONombre + "=?", new String[]{nombre},null,null,null);
    }

    public Cursor obtenerPorCatId(SQLiteDatabase db, int catId){

        return db.query(tablaProducto, columnas, colPROCATId + "=?", new String[]{Integer.toString(catId)},null,null,null);
    }
}
