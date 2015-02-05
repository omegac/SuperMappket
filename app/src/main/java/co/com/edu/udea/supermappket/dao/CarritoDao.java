package co.com.edu.udea.supermappket.dao;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import co.com.edu.udea.supermappket.dto.CarCarrito;
import co.com.edu.udea.supermappket.dto.ProProducto;

/**
 * Created by Alan on 02/01/2015.
 */
public class CarritoDao {

    /**
     * Propiedades tabla CAR_Carrito
     */
    public static final String tablaCarrito = "CAR_Carrito";
    public static final String colCARId = "CAR_Id";
    public static final String colCARUSUId = "CAR_USU_Id";
    public static final String colCARPrecioTotal = "CAR_PrecioTotal";
    public static final String colCARFecha = "CAR_Fecha";
    private String[] columnas = new String[]{colCARId, colCARUSUId, colCARPrecioTotal, colCARFecha};
    public static final String crearTablaCAR = "CREATE TABLE " + tablaCarrito + " (" +
            colCARId + " INTEGER PRIMARY KEY AUTOINCREMENT," +
            colCARUSUId + " INTEGER NOT NULL," +
            colCARPrecioTotal + " REAL NOT NULL," +
            colCARFecha + " TEXT NOT NULL," +
            " FOREIGN KEY(" + colCARUSUId + ") REFERENCES " + UsuarioDao.tablaUsuario + "(" + UsuarioDao.colUSUId + "));";

    /**
     * Propiedades tabla CAR_PRO
     */
    public static final String tablaCARPRO = "CAR_PRO";
    public static final String colCPCARId = "CP_CAR_Id";
    public static final String colCPPROId = "CP_PRO_Id";
    public static final String colCPCantidad = "CP_Cantidad";
    public static final String crearTablaCARPRO = "CREATE TABLE " + tablaCARPRO + " (" +
            colCPCARId + " INTEGER," +
            colCPPROId + " INTEGER," +
            colCPCantidad + " INTEGER NOT NULL," +
            " PRIMARY KEY (" + colCPCARId + "," + colCPPROId + ")," +
            " FOREIGN KEY(" + colCPCARId + ") REFERENCES " + CarritoDao.tablaCarrito + "(" + CarritoDao.colCARId + ")," +
            " FOREIGN KEY(" + colCPPROId + ") REFERENCES " + ProductoDao.tablaProducto + "(" + ProductoDao.colPROId + "));";

    public ContentValues genContentValuesCar(int idUsuario, double precioTotal, Date fecha){

        ContentValues cv = new ContentValues();
        DateFormat dateFormat = new SimpleDateFormat(
                "yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        cv.put(colCARUSUId, idUsuario);
        cv.put(colCARPrecioTotal, precioTotal);
        cv.put(colCARFecha, dateFormat.format(fecha));
        return cv;
    }

    public ContentValues genContentValuesCarPro(int idCarrito, int idProd, int cantidad){

        ContentValues cv = new ContentValues();
        cv.put(colCPCARId, idCarrito);
        cv.put(colCPPROId, idProd);
        cv.put(colCPCantidad, cantidad);
        return cv;
    }

    public long insertar(SQLiteDatabase db, CarCarrito carrito){
        long id = -1;
        try {
            id = db.insert(tablaCarrito, null, genContentValuesCar(carrito.getIdUsuario(), carrito.getCarPrecioTotal(),
                    carrito.getCarFecha()));
        } catch (Exception e) {
            e.printStackTrace();
        }

        return id;
    }

    public Cursor obtenerCarrito(SQLiteDatabase db, int id){

        return db.query(tablaCarrito, columnas, colCARId + "=?", new String[]{Integer.toString(id)},null,null,null);
    }

    public void insertarProdsCar(SQLiteDatabase db, CarCarrito carrito){
        int idCarrito = carrito.getCarId();
        try {
            for(ProProducto producto: carrito.getProductos()){
                db.insert(tablaCARPRO, null, genContentValuesCarPro(idCarrito, producto.getProId(),
                        producto.getCantidad()));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
