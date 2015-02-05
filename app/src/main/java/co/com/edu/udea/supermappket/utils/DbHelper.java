package co.com.edu.udea.supermappket.utils;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import co.com.edu.udea.supermappket.dao.CarritoDao;
import co.com.edu.udea.supermappket.dao.CategoriaDao;
import co.com.edu.udea.supermappket.dao.ListaDao;
import co.com.edu.udea.supermappket.dao.ProductoDao;
import co.com.edu.udea.supermappket.dao.UsuarioDao;
import co.com.edu.udea.supermappket.dto.CatCategoria;
import co.com.edu.udea.supermappket.dto.LisLista;
import co.com.edu.udea.supermappket.dto.ProProducto;
import co.com.edu.udea.supermappket.dto.UsuUsuario;
import co.com.edu.udea.supermappket.utils.DataBaseManager;

/**
 * Created by Alan on 31/12/2014.
 */
public class DbHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "SuperMappketDB.sqlite";
    private static final int DB_SCHEME_VERSION = 1;


    public DbHelper(Context context) {
        super(context, DB_NAME, null, DB_SCHEME_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        sqLiteDatabase.execSQL(UsuarioDao.crearTablaUSU);
        sqLiteDatabase.execSQL(CategoriaDao.crearTablaCAT);
        sqLiteDatabase.execSQL(ProductoDao.crearTablaPRO);
        sqLiteDatabase.execSQL(CarritoDao.crearTablaCAR);
        sqLiteDatabase.execSQL(CarritoDao.crearTablaCARPRO);
        sqLiteDatabase.execSQL(ListaDao.crearTablaLIS);
        sqLiteDatabase.execSQL(ListaDao.crearTablaLISPRO);
        this.insertarUsuario(sqLiteDatabase);
        this.insertarCategorias(sqLiteDatabase);
        this.insertarLista(sqLiteDatabase);
        this.insertarProductos(sqLiteDatabase);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i2) {

    }

    public void insertarUsuario(SQLiteDatabase db){
        UsuarioDao usuarioDao = new UsuarioDao();
        UsuUsuario usuario = new UsuUsuario(0,"user", "asdf");
        usuarioDao.insertar(db, usuario);
    }

    public void insertarCategorias(SQLiteDatabase db){

        CategoriaDao cd = new CategoriaDao();
        String cat1 = "Miscelanea";
        String desCat1 = "Leche, agua, salsas y más";
        String cat2 = "Frutas";
        String desCat2 = "Manzanas, peras, naranjas y mas";
        String cat3 = "Ropa para hombres";
        String desCat3 = "Camisetas, camisas, jeans y mas";
        String cat4 = "Ropa para mujeres";
        String desCat4 = "Vestidos, blusas y tops, pantalones y jeans y mas";
        final String[] nombres = new String[]{cat1, cat2, cat3, cat4};
        final String[] descripciones = new String[]{desCat1,desCat2,desCat3, desCat4};

        for(int i=0; i<nombres.length; i++){
            CatCategoria cat = new CatCategoria(0, nombres[i], descripciones[i], null);
            cd.insertar(db, cat);
        }
    }

    public void insertarProductos(SQLiteDatabase db){

        ProductoDao pd = new ProductoDao();
        CategoriaDao cd = new CategoriaDao();
        int columnaId;
        int columnaNombre;
        int columnaDescripcion;
        Cursor cursor1;
        Cursor cursor2;
        Cursor cursor3;
        Cursor cursor4;
        cursor1 = cd.obtenerCategoria(db, "Miscelanea");
        cursor1.moveToFirst();
        columnaId = cursor1.getColumnIndex(CategoriaDao.colCATId);
        columnaNombre = cursor1.getColumnIndex(CategoriaDao.colCATDescripcion);
        columnaDescripcion = cursor1.getColumnIndex(CategoriaDao.colCATDescripcion);
        CatCategoria cat1 = new CatCategoria(Integer.parseInt(cursor1.getString(columnaId)),
                cursor1.getString(columnaNombre), cursor1.getString(columnaDescripcion), null);
        cursor1.close();
        cursor2 = cd.obtenerCategoria(db, "Frutas");
        cursor2.moveToFirst();
        columnaId = cursor2.getColumnIndex(CategoriaDao.colCATId);
        columnaNombre = cursor2.getColumnIndex(CategoriaDao.colCATDescripcion);
        columnaDescripcion = cursor2.getColumnIndex(CategoriaDao.colCATDescripcion);
        CatCategoria cat2 = new CatCategoria(Integer.parseInt(cursor2.getString(columnaId)),
                cursor2.getString(columnaNombre), cursor2.getString(columnaDescripcion), null);
        cursor2.close();
        cursor3 = cd.obtenerCategoria(db, "Ropa para hombres");
        cursor3.moveToFirst();
        columnaId = cursor3.getColumnIndex(CategoriaDao.colCATId);
        columnaNombre = cursor3.getColumnIndex(CategoriaDao.colCATDescripcion);
        columnaDescripcion = cursor3.getColumnIndex(CategoriaDao.colCATDescripcion);
        CatCategoria cat3 = new CatCategoria(Integer.parseInt(cursor3.getString(columnaId)),
                cursor3.getString(columnaNombre), cursor3.getString(columnaDescripcion), null);
        cursor3.close();
        cursor4 = cd.obtenerCategoria(db, "Ropa para mujeres");
        cursor4.moveToFirst();
        columnaId = cursor4.getColumnIndex(CategoriaDao.colCATId);
        columnaNombre = cursor4.getColumnIndex(CategoriaDao.colCATDescripcion);
        columnaDescripcion = cursor4.getColumnIndex(CategoriaDao.colCATDescripcion);
        CatCategoria cat4 = new CatCategoria(Integer.parseInt(cursor4.getString(columnaId)),
                cursor4.getString(columnaNombre), cursor4.getString(columnaDescripcion), null);
        cursor4.close();

        String prod1 = "Leche en bolsa Colanta";
        double prod1Precio = 1200;
        String prod1Desc = "Baja en grasas y XXXXX";
        int cantProd1 = 0;
        int prod1Cat = 1;
        String prod1Icono = "leche_bolsa";
        String prod2 = "Agua Manantial";
        double prod2Precio = 999.99;
        String prod2Desc = "Agua pura y refrescante";
        int cantProd2 = 0;
        int prod2Cat = 1;
        String prod2Icono = "agua_manantial";

        String prod3 = "Manzana roja";
        double prod3Precio = 500;
        String prod3Desc = "Fresca";
        int cantProd3 = 0;
        int prod3Cat = 2;
        String prod3Icono = "manzana_roja";
        String prod4 = "Guanabana";
        double prod4Precio = 1450;
        String prod4Desc = "Mediana y jugosa";
        int cantProd4 = 0;
        int prod4Cat = 2;
        String prod4Icono = "guanabana_mediana";

        String prod5 = "Camisa Jack & Brick";
        double prod5Precio = 34999.99;
        String prod5Desc = "Basic blanco estampado";
        int cantProd5 = 0;
        int prod5Cat = 3;
        String prod5Icono = "camisa_jack";
        String prod6 = "Jean Jack & brick";
        double prod6Precio = 149999.99;
        String prod6Desc = "Ptrolizado-azul Petroleo";
        int cantProd6 = 0;
        int prod6Cat = 3;
        String prod6Icono = "jean_jack";

        String prod7 = "Vestido Recto Le Minuit";
        double prod7Precio = 69900;
        String prod7Desc = "Nathasha-Negro";
        int cantProd7 = 0;
        int prod7Cat = 4;
        String prod7Icono = "vestido_leminuit";
        String prod8 = "Pantalon Le Minuit";
        double prod8Precio = 69900;
        String prod8Desc = "Manchester 2-Gris Estampado Flores";
        int cantProd8 = 0;
        int prod8Cat = 4;
        String prod8Icono = "pantalon_leminuit";

        String[] nombres = new String[]{prod1, prod2, prod3, prod4, prod5, prod6, prod7, prod8};
        double[] precios = new double[]{prod1Precio, prod2Precio, prod3Precio,prod4Precio, prod5Precio,
                prod6Precio, prod7Precio, prod8Precio};
        String[] descripciones = new String[]{prod1Desc, prod2Desc, prod3Desc, prod4Desc, prod5Desc,
                prod6Desc, prod7Desc, prod8Desc};
        String[] iconos = new String[]{prod1Icono, prod2Icono, prod3Icono, prod4Icono, prod5Icono,
                prod6Icono, prod7Icono, prod8Icono};
        int[] cantidades = new int[]{cantProd1, cantProd2, cantProd3, cantProd4, cantProd5, cantProd6, cantProd7, cantProd8};
        CatCategoria[] categorias = new CatCategoria[]{cat1, cat1, cat2, cat2, cat3, cat3, cat4, cat4};

        for(int i=0; i<nombres.length; i++){
            pd.insertar(db, new ProProducto(0,nombres[i], precios[i], descripciones[i], cantidades[i],
                    categorias[i].getCatId(), iconos[i]));
        }
    }

    public void insertarLista(SQLiteDatabase db){

        ListaDao listaDao = new ListaDao();
        LisLista lista;
        String nombreLista = "Mercado";
        int idUsuario = 1;

        lista = new LisLista(0, nombreLista, idUsuario, null);
        listaDao.insertar(db, lista);
    }
}
