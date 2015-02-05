package co.com.edu.udea.supermappket.utils;

import android.app.Application;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.CursorTreeAdapter;

import java.util.ArrayList;
import java.util.Date;

import co.com.edu.udea.supermappket.dao.CarritoDao;
import co.com.edu.udea.supermappket.dao.CategoriaDao;
import co.com.edu.udea.supermappket.dao.ListaDao;
import co.com.edu.udea.supermappket.dao.ProductoDao;
import co.com.edu.udea.supermappket.dao.UsuarioDao;
import co.com.edu.udea.supermappket.dto.CarCarrito;
import co.com.edu.udea.supermappket.dto.CatCategoria;
import co.com.edu.udea.supermappket.dto.LisLista;
import co.com.edu.udea.supermappket.dto.ProProducto;
import co.com.edu.udea.supermappket.dto.UsuUsuario;

/**
 * Created by Alan on 31/12/2014.
 */
public class DataBaseManager extends Application {

    private DbHelper helper;
    private static SQLiteDatabase db;
    UsuarioDao usuarioDao;
    CategoriaDao categoriaDao;
    ProductoDao productoDao;
    ListaDao listaDao;
    CarritoDao carritoDao;

    public DataBaseManager(Context context) {

        helper = new DbHelper(context);
        db = helper.getWritableDatabase();
        usuarioDao = new UsuarioDao();
        categoriaDao = new CategoriaDao();
        productoDao = new ProductoDao();
        listaDao = new ListaDao();
        carritoDao = new CarritoDao();
    }

    public void insertarUsuario(String nombre, String password) {
        UsuUsuario usuario = new UsuUsuario(0, nombre, password);
        usuarioDao.insertar(db, usuario);
    }

    public int obtenerIdUsuario(String nombre, String password) {
        int id = 0;
        Cursor cursor;
        int columnaId;

        cursor = usuarioDao.obtener(db, nombre, password);
        if (cursor.moveToFirst()) {
            columnaId = cursor.getColumnIndex(UsuarioDao.colUSUId);
            id = Integer.parseInt(cursor.getString(columnaId));
        }
        return id;
    }

    public ArrayList<LisLista> obtenerListas(int idUsuario){

        ArrayList<LisLista> listas = new ArrayList<LisLista>();
        LisLista lista;
        Cursor cursor;
        int colIdLista;
        int colNombLista;
        int colIdusuario;
        int idLista;
        String nombreLista;

        cursor = listaDao.obtenerListasUsuario(db, idUsuario);
        if (cursor.moveToFirst()) {
            for (int i = 0; i < cursor.getCount(); i++) {
                colIdLista = cursor.getColumnIndex(ListaDao.colLISId);
                colNombLista = cursor.getColumnIndex(ListaDao.colLISNombre);

                idLista = cursor.getInt(colIdLista);
                nombreLista = cursor.getString(colNombLista);

                lista = new LisLista(idLista, nombreLista, idUsuario, null);
                listas.add(lista);
                cursor.moveToNext();
            }
        }
        cursor.close();

        return listas;
    }

    public ArrayList<CatCategoria> obtenerCategorias() {
        ArrayList<CatCategoria> categorias = new ArrayList<CatCategoria>();
        CatCategoria categoria;
        ArrayList<ProProducto> productos;
        Cursor cursor;
        int colId;
        int colNomb;
        int colDesc;
        int id;
        String nombre;
        String Desc;

        cursor = categoriaDao.obtenerCategorias(db);
        if (cursor.moveToFirst()) {
            for (int i = 0; i < cursor.getCount(); i++) {
                colId = cursor.getColumnIndex(CategoriaDao.colCATId);
                colNomb = cursor.getColumnIndex(CategoriaDao.colCATNombre);
                colDesc = cursor.getColumnIndex(CategoriaDao.colCATDescripcion);
                id = cursor.getInt(colId);
                nombre = cursor.getString(colNomb);
                Desc = cursor.getString(colDesc);
                productos = obtenerProdsPorCat(id);
                categoria = new CatCategoria(id, nombre, Desc, productos);
                categorias.add(categoria);
                cursor.moveToNext();
            }
        }
        cursor.close();
        return categorias;
    }

    public ArrayList<ProProducto> obtenerProdsPorCat(int catId) {
        ArrayList<ProProducto> productos = new ArrayList<ProProducto>();
        ProProducto producto;
        Cursor cursor;
        int colId;
        int colNomb;
        int colPrecio;
        int colDesc;
        int colIcon;
        int id;
        String nombre;
        double precio;
        String desc;
        String nombreIcono;
        int cantidad = 0;

        cursor = productoDao.obtenerPorCatId(db, catId);
        if (cursor.moveToFirst()) {
            for (int i = 0; i < cursor.getCount(); i++) {
                colId = cursor.getColumnIndex(ProductoDao.colPROId);
                colNomb = cursor.getColumnIndex(ProductoDao.colPRONombre);
                colPrecio = cursor.getColumnIndex(ProductoDao.colPROPrecio);
                colDesc = cursor.getColumnIndex(ProductoDao.colPRODescripcion);
                colIcon = cursor.getColumnIndex(ProductoDao.colPROIcono);
                id = cursor.getInt(colId);
                nombre = cursor.getString(colNomb);
                precio = cursor.getDouble(colPrecio);
                desc = cursor.getString(colDesc);
                nombreIcono = cursor.getString(colIcon);
                producto = new ProProducto(id, nombre, precio, desc, cantidad, catId, nombreIcono);
                productos.add(producto);
                cursor.moveToNext();
            }
        }
        cursor.close();
        return productos;
    }

    public ProProducto obtenerProducto(int idProducto){

        ProProducto producto = new ProProducto();
        Cursor cursor;
        int colNombProd;
        int colPrecioProd;
        int colDescProd;
        int colCatProd;
        int colIconProd;
        String nombreProd;
        double precioProd;
        String descProd;
        String nombreIconProd;
        int catProd;

        cursor = productoDao.obtenerPorId(db, idProducto);
        if(cursor.moveToFirst()){
            colNombProd = cursor.getColumnIndex(ProductoDao.colPRONombre);
            colPrecioProd = cursor.getColumnIndex(ProductoDao.colPROPrecio);
            colDescProd = cursor.getColumnIndex(ProductoDao.colPRODescripcion);
            colCatProd = cursor.getColumnIndex(ProductoDao.colPROCATId);
            colIconProd = cursor.getColumnIndex(ProductoDao.colPROIcono);

            nombreProd = cursor.getString(colNombProd);
            precioProd = cursor.getInt(colPrecioProd);
            descProd = cursor.getString(colDescProd);
            catProd = cursor.getInt(colCatProd);
            nombreIconProd = cursor.getString(colIconProd);
            producto = new ProProducto(idProducto, nombreProd, precioProd, descProd, 0, catProd, nombreIconProd);
        }

        return producto;
    }

    public int insertarLista(String nombre, int idUser) {

        Cursor cursor;
        int idLista = -1;

        cursor = listaDao.obtenerListaPorNomb(db, nombre);
        if(cursor.moveToFirst()){
            cursor.close();
        }else {
            LisLista lista = new LisLista(0, nombre, idUser, null);
            idLista = (int) listaDao.insertar(db, lista);
        }

        return idLista;
    }

    public ArrayList<ProProducto> obtenerProdsLista(int idLista) {
        ArrayList<ProProducto> productos = new ArrayList<ProProducto>();
        ProProducto producto;
        Cursor cursorProdsLista;
        Cursor cursorProd;
        int colIdProd;
        int colCantProd;
        int colNombProd;
        int colPrecioProd;
        int colDescProd;
        int colCatProd;
        int colIconProd;
        int idProd;
        int cantProd;
        String nombreProd;
        double precioProd;
        String descProd;
        int catProd;
        String nombreIconProd;

        cursorProdsLista = listaDao.obtenerProdsLista(db, idLista);
        if (cursorProdsLista.moveToFirst()) {
            for (int i = 0; i < cursorProdsLista.getCount(); i++) {
                colIdProd = cursorProdsLista.getColumnIndex(ListaDao.colLPPROId);
                colCantProd = cursorProdsLista.getColumnIndex(ListaDao.colLPCantidad);
                idProd = cursorProdsLista.getInt(colIdProd);
                cantProd = cursorProdsLista.getInt(colCantProd);

                cursorProd = productoDao.obtenerPorId(db, idProd);
                if (cursorProdsLista.moveToFirst()) {
                    colNombProd = cursorProd.getColumnIndex(ProductoDao.colPRONombre);
                    colPrecioProd = cursorProd.getColumnIndex(ProductoDao.colPROPrecio);
                    colDescProd = cursorProd.getColumnIndex(ProductoDao.colPRODescripcion);
                    colCatProd = cursorProd.getColumnIndex(ProductoDao.colPROCATId);
                    colIconProd = cursorProd.getColumnIndex(ProductoDao.colPROIcono);
                    nombreProd = cursorProd.getString(colNombProd);
                    precioProd = cursorProd.getInt(colPrecioProd);
                    descProd = cursorProd.getString(colDescProd);
                    catProd = cursorProd.getInt(colCatProd);
                    nombreIconProd = cursorProd.getString(colIconProd);
                    producto = new ProProducto(idProd, nombreProd, precioProd, descProd, cantProd, catProd, nombreIconProd);
                    productos.add(producto);
                }
                cursorProd.close();

                cursorProdsLista.moveToNext();
            }
        }
        cursorProdsLista.close();
        return productos;
    }

    public void insertarProdsLista(int idLista, ArrayList<ProProducto> productos) {

        Cursor cursor;
        LisLista lista;
        int colNomb;
        int colIdUser;
        String nombreLista;
        int idUser;

        cursor = listaDao.obtenerListaPorId(db, idLista);
        if (cursor.moveToFirst()) {
            colNomb = cursor.getColumnIndex(ListaDao.colLISNombre);
            colIdUser = cursor.getColumnIndex(ListaDao.colLISUSUId);
            nombreLista = cursor.getString(colNomb);
            idUser = cursor.getInt(colIdUser);
            cursor.close();
            lista = new LisLista(idLista, nombreLista, idUser, productos);
            listaDao.insertarProdsList(db, lista);
        }

    }

    public void insertarCarrito(int idUser, double precio, ArrayList<ProProducto> productos) {

        Date fechaActual = new Date();
        int idCarrito;
        CarCarrito carrito = new CarCarrito(0, idUser, precio, fechaActual, productos);

        idCarrito = (int) carritoDao.insertar(db, carrito);
        carrito.setCarId(idCarrito);
        carritoDao.insertarProdsCar(db, carrito);
    }
}
