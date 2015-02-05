package co.com.edu.udea.supermappket.dao;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import co.com.edu.udea.supermappket.dto.LisLista;
import co.com.edu.udea.supermappket.dto.ProProducto;

/**
 * Created by Alan on 02/01/2015.
 */
public class ListaDao {

    /**
     * Propiedades tabla LIS_Lista
     */
    public static final String tablaLista = "LIS_Lista";
    public static final String colLISId = "LIS_Id";
    public static final String colLISNombre = "LIS_Nombre";
    public static final String colLISUSUId = "LIS_USU_Id";
    private String[] columnas = new String[]{colLISId, colLISNombre, colLISUSUId};
    public static final String crearTablaLIS = "CREATE TABLE " + tablaLista + " (" +
            colLISId + " INTEGER PRIMARY KEY AUTOINCREMENT," +
            colLISNombre + " TEXT NOT NULL," +
            colLISUSUId + " INTEGER NOT NULL," +
            " FOREIGN KEY(" + colLISUSUId + ") REFERENCES " + UsuarioDao.tablaUsuario + "(" + UsuarioDao.colUSUId + "));";

    /**
     * Propiedades tabla LIS_PRO
     */
    public static final String tablaLISPRO = "LIS_PRO";
    public static final String colLPLISId = "LP_LIS_Id";
    public static final String colLPPROId = "LP_PRO_Id";
    public static final String colLPCantidad = "LP_Cantidad";
    private String[] columnasLP = new String[]{colLPLISId, colLPPROId, colLPCantidad};
    public static final String crearTablaLISPRO = "CREATE TABLE " + tablaLISPRO + " (" +
            colLPLISId + " INTEGER," +
            colLPPROId + " INTEGER," +
            colLPCantidad + " INTEGER NOT NULL," +
            " PRIMARY KEY (" + colLPLISId + "," + colLPPROId + ")," +
            " FOREIGN KEY(" + colLPLISId + ") REFERENCES " + ListaDao.tablaLista + "(" + ListaDao.colLISId + ")," +
            " FOREIGN KEY(" + colLPPROId + ") REFERENCES " + ProductoDao.tablaProducto + "(" + ProductoDao.colPROId + "));";


    private ContentValues genContentValuesList(String nombre, int idUsuario){

        ContentValues cv = new ContentValues();
        cv.put(colLISNombre, nombre);
        cv.put(colLISUSUId, idUsuario);
        return cv;
    }

    private ContentValues genContentValuesLisPro(int idLista, int idProd, int cantidad){

        ContentValues cv = new ContentValues();
        cv.put(colLPLISId, idLista);
        cv.put(colLPPROId, idProd);
        cv.put(colLPCantidad, cantidad);
        return cv;
    }

    public long insertar(SQLiteDatabase db, LisLista lista){
        long id = -1;
        try {
            id = db.insert(tablaLista, null, genContentValuesList(lista.getLisNombre(), lista.getIdUsuario()));
        } catch (Exception e) {
            e.printStackTrace();
        }

        return id;
    }

    public void insertarProdsList(SQLiteDatabase db, LisLista lista){
        int idLista = lista.getLisId();
        try {
            for(ProProducto producto: lista.getProductos()){
                db.insertWithOnConflict(tablaLISPRO, null, genContentValuesLisPro(idLista, producto.getProId(),
                        producto.getCantidad()), SQLiteDatabase.CONFLICT_REPLACE);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Cursor obtenerListasUsuario(SQLiteDatabase db, int idUser){

        return db.query(tablaLista, columnas, colLISUSUId + "=?", new String[]{Integer.toString(idUser)},null,null,null);
    }

    public Cursor obtenerListaPorId(SQLiteDatabase db, int idLista){

        return db.query(tablaLista, columnas, colLISId + "=?", new String[]{Integer.toString(idLista)},null,null,null);
    }

    public Cursor obtenerListaPorNomb(SQLiteDatabase db, String nombreLista){

        return db.query(tablaLista, columnas, colLISNombre + "=?", new String[]{nombreLista},null,null,null);
    }

    public Cursor obtenerProdsLista(SQLiteDatabase db, int idLista){

        String[] camposProds = new String[]{colLPPROId, colLPCantidad};
        return db.query(tablaLista, camposProds, colLPLISId + "=?", new String[]{Integer.toString(idLista)},null,null,null);
    }

    public void eliminar(SQLiteDatabase db, LisLista lista){

    }
}
