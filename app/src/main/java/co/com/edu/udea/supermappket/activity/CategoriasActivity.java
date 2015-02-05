package co.com.edu.udea.supermappket.activity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.Fragment;
import android.app.SearchManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.ExpandableListView;
import android.widget.NumberPicker;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import co.com.edu.udea.supermappket.R;
import co.com.edu.udea.supermappket.dto.CarCarrito;
import co.com.edu.udea.supermappket.dto.CatCategoria;
import co.com.edu.udea.supermappket.dto.ProProducto;
import co.com.edu.udea.supermappket.utils.ELVAdapter;
import co.com.edu.udea.supermappket.zxing.integration.android.IntentIntegrator;
import co.com.edu.udea.supermappket.zxing.integration.android.IntentResult;

public class CategoriasActivity extends FragmentActivity implements SearchView.OnQueryTextListener,
        SearchView.OnCloseListener{

    static int identificadorUser;
    static double saldo = 10000;
    static double precioCarrito= 0;
    static final int Ingresar_Saldo = 0;
    static final int Agregar_Producto = 1;
    static final int Remover_Producto = 2;
    static final int Comprar_Carrito = 3;
    static ArrayList<ProProducto> productosCar = new ArrayList<ProProducto>();
    private SearchView searchView;
    private ELVAdapter elvAdapter;
    private ExpandableListView listaExpandible;
    private ArrayList<CatCategoria> listaCategorias = new ArrayList<CatCategoria>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categorias);
        setTitle(getResources().getString(R.string.app_name));
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        searchView = (SearchView) findViewById(R.id.buscarSV);

        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setIconifiedByDefault(false);
        searchView.setOnQueryTextListener(this);
        searchView.setOnCloseListener(this);

        getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN
        );
        identificadorUser = getIntent().getExtras().getInt("idUser");
        mostrarLista();

        //Toast.makeText(getBaseContext(), "Existe papá!! " + Integer.toString(id), Toast.LENGTH_SHORT).show();
    }

    /*
     * Metodo para recibir el resultado arrojado por el lector de codigos QR
     */
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        //se obtiene el resultado del scaneo
        IntentResult scanningResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, intent);
        if (scanningResult != null && scanningResult.getContents() != null) {
            //se tiene un resultado
            try {
                String scanContent = scanningResult.getContents();
                ProProducto producto;
                int codigoProducto = Integer.parseInt(scanContent.trim());

                producto = MainActivity.manager.obtenerProducto(codigoProducto);
                Dialog productoDialog = mostrarDialogNumProds(producto);
                productoDialog.show();
            } catch (Exception e){
                Toast toast = Toast.makeText(this,
                        "Código QR no reconocido.", Toast.LENGTH_SHORT);
                toast.show();
            }
        }else{
            Toast toast = Toast.makeText(this,
                    "No se leyó ningún código QR", Toast.LENGTH_SHORT);
            toast.show();
        }
    }

    private void expandirTodo(){

        int contador = elvAdapter.getGroupCount();
        for (int i = 0; i < contador; i++) {
            listaExpandible.expandGroup(i);
        }
    }

    private void mostrarLista(){

        listaCategorias = MainActivity.manager.obtenerCategorias();
        listaExpandible = (ExpandableListView) findViewById(R.id.categoriasELV);
        elvAdapter = new ELVAdapter(CategoriasActivity.this, listaCategorias);

        listaExpandible.setAdapter(elvAdapter);
        listaExpandible.setItemsCanFocus(false);
        listaExpandible.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v,
                                        int groupPosition, int childPosition, long id) {
                ProProducto productoCar;
                productoCar = (ProProducto) elvAdapter.getChild(groupPosition, childPosition);
                Dialog dNumProductos = mostrarDialogNumProds(productoCar);
                dNumProductos.show();
                return false;
            }
        });
    }

    public Dialog mostrarDialogNumProds(final ProProducto producto) {

        Context mContext = this;

        AlertDialog.Builder builder = new AlertDialog.Builder(mContext, AlertDialog.THEME_HOLO_DARK);
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(LAYOUT_INFLATER_SERVICE);
        View layout = inflater.inflate(R.layout.dialog_productos,null);
        final TextView precioProdsTV = (TextView) layout.findViewById(R.id.precioActualTV);
        final NumberPicker np = (NumberPicker) layout.findViewById(R.id.numberPicker1);
        final int maxVal = 100;
        final int minVal = 1;
        final double precioProd = producto.getProPrecio();
        final String nombreProd = producto.getProNombre();

        np.setMaxValue(maxVal); // max value 100
        np.setMinValue(minVal);   // min value 1
        np.setBackgroundColor(Color.WHITE);
        precioProdsTV.setText("Precio: $" + String.format("%.2f", precioProd));
        cambiarColorTVPrec(precioProdsTV, precioProd);
        np.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                // do something here
                double precioProds = precioProd * np.getValue();
                precioProdsTV.setText("Precio: $" + String.format("%.2f", precioProds));
                cambiarColorTVPrec(precioProdsTV, precioProds);
            }
        });


        builder.setPositiveButton("Agregar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                int nProds = np.getValue();
                int indexProducto;
                int cantidadActual;
                FragmentAcciones fragmentCats = (FragmentAcciones) getSupportFragmentManager().findFragmentById(R.id.fragmentCats);
                double precio = precioProd *nProds;

                if(saldo < (precio)){
                    Toast.makeText(getBaseContext(), "Saldo insuficiente. El saldo actual es $"
                            + String.format("%.2f", saldo) + ".", Toast.LENGTH_SHORT).show();
                }else {
                    // Si el producto ya ha sido agregado
                    indexProducto = productosCar.indexOf(producto);
                    if(indexProducto !=  -1){
                        cantidadActual = productosCar.get(indexProducto).getCantidad();
                        productosCar.get(indexProducto).setCantidad(cantidadActual + nProds);
                    } else {
                        producto.setCantidad(nProds);
                        productosCar.add(producto);
                    }
                    actSaldoYPTotal(Agregar_Producto, precio);
                    fragmentCats.actualizarTVs();
                    Toast.makeText(getBaseContext(), "Agregadas " + Integer.toString(nProds) +
                            " unidades de " + nombreProd + " al carrito. El saldo actual" +
                            " es $" + String.format("%.2f", saldo) + ".", Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                }
            }
        })
                .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();
                    }
                });
        builder.setTitle(nombreProd);
        builder.setView(layout);
        return builder.create();
    }

    private void cambiarColorTVPrec(TextView textView, double precio){
        if(saldo < precio){
            textView.setTextColor(Color.RED);
        } else {
            textView.setTextColor(Color.GREEN);
        }
    }

    public static void actSaldoYPTotal(int idAccion, double valor){

        switch (idAccion) {
            case Ingresar_Saldo:
                saldo = valor;
                break;
            case Agregar_Producto:
                saldo = saldo - valor;
                precioCarrito = precioCarrito + valor;
                break;
            case Remover_Producto:
                saldo = saldo + valor;
                precioCarrito = precioCarrito - valor;
                break;
            case Comprar_Carrito:
                precioCarrito = 0;
                break;
        }
    }

    /**
     * The user is attempting to close the SearchView.
     *
     * @return true if the listener wants to override the default behavior of clearing the
     * text field and dismissing it, false otherwise.
     */
    @Override
    public boolean onClose() {

        elvAdapter.filtrarInfo("");
        expandirTodo();
        return false;
    }

    /**
     * Called when the user submits the query. This could be due to a key press on the
     * keyboard or due to pressing a submit button.
     * The listener can override the standard behavior by returning true
     * to indicate that it has handled the submit request. Otherwise return false to
     * let the SearchView handle the submission by launching any associated intent.
     *
     * @param query the query text that is to be submitted
     * @return true if the query has been handled by the listener, false to let the
     * SearchView perform the default action.
     */
    @Override
    public boolean onQueryTextSubmit(String query) {
        elvAdapter.filtrarInfo(query);
        expandirTodo();
        return true;
    }

    /**
     * Called when the query text is changed by the user.
     *
     * @param newText the new content of the query text field.
     * @return false if the SearchView should perform the default action of showing any
     * suggestions if available, true if the action was handled by the listener.
     */
    @Override
    public boolean onQueryTextChange(String newText) {

        elvAdapter.filtrarInfo(newText);
        expandirTodo();
        return true;
    }
}
