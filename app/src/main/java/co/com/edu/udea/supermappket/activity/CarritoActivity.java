package co.com.edu.udea.supermappket.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import co.com.edu.udea.supermappket.R;
import co.com.edu.udea.supermappket.dto.CarCarrito;
import co.com.edu.udea.supermappket.dto.ProProducto;
import co.com.edu.udea.supermappket.utils.CarritoListAdpter;

/**
 * Created by Alan on 10/01/2015.
 */
public class CarritoActivity extends Activity {

    private CarritoListAdpter adapter;
    private static TextView total;
    private static  TextView carVacio;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_carrito);
        setTitle(getResources().getString(R.string.title_activity_carrito));
        getActionBar().setIcon(R.drawable.cart);

        Button btnComprar = (Button) findViewById(R.id.comprarBTN);
        carVacio = (TextView) findViewById(R.id.carVacioTV);

        total = (TextView) findViewById(R.id.totalTV);
        veriricarCarVacioTV();

        btnComprar.setOnClickListener(new View.OnClickListener() {
            /**
             * Called when a view has been clicked.
             *
             * @param v The view that was clicked.
             */
            @Override
            public void onClick(View v) {
                if(CategoriasActivity.productosCar.isEmpty()){
                    Toast.makeText(getBaseContext(), "Carrito vacío.", Toast.LENGTH_SHORT).show();
                } else {
                    MainActivity.manager.insertarCarrito(CategoriasActivity.identificadorUser,
                            CategoriasActivity.precioCarrito, CategoriasActivity.productosCar);
                    CategoriasActivity.actSaldoYPTotal(CategoriasActivity.Comprar_Carrito, 0);
                    CategoriasActivity.productosCar.clear();
                    actualizarTotal();
                    veriricarCarVacioTV();
                    Toast.makeText(getBaseContext(), "Compra registrada satisfactoriamente." +
                            " Saldo actual: " +  String.format("%.2f",CategoriasActivity.saldo), Toast.LENGTH_LONG).show();
                    onBackPressed();
                }
            }
        });

        setupListViewCarAdapter();
        total.setText("Precio total: " + "$" + String.format("%.2f", CategoriasActivity.precioCarrito));

        //setupAddPaymentButton();
    }

    public void removProdOnClickHandler(final View v) {
        final ProProducto productoRemover = (ProProducto) v.getTag();
        new AlertDialog.Builder(this, AlertDialog.THEME_HOLO_DARK)
                .setTitle(R.string.titulo_dialog_carrito)
                .setMessage("Remover producto: " + productoRemover.getProNombre() + "?")
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setPositiveButton(R.string.aceptar_dialog_carrito, new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int whichButton) {
                        removerProducto(v, productoRemover);
                    }})
                .setNegativeButton(R.string.cancelar_dialog, null).show();
    }

    public void removerProducto(View v, ProProducto productoRemover){
        double precioAgregado = productoRemover.getProPrecio() * productoRemover.getCantidad();
        adapter.remove(productoRemover);
        CategoriasActivity.actSaldoYPTotal(CategoriasActivity.Remover_Producto, precioAgregado);
        actualizarTotal();
        veriricarCarVacioTV();
        Toast.makeText(getBaseContext(), "Producto '" + productoRemover.getProNombre() +
                "' removido del carrito.", Toast.LENGTH_SHORT).show();
    }

/*    private Dialog createDialogRemoveConfirm() {
        return new AlertDialog.Builder(getApplicationContext())
                .setIcon(R.drawable.trashbin_icon)
                .setTitle(R.string.titulo_dialog_carrito)
                .setPositiveButton(R.string.aceptar_dialog_carrito, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        handleRemoveConfirm(dialogRemove);
                    }
                })
                .setNegativeButton(R.string.cancelar_dialog_carrito, null)
                .create();
    }*/

    private void setupListViewCarAdapter() {
        adapter = new CarritoListAdpter(CarritoActivity.this, R.layout.carrito_item, CategoriasActivity.productosCar);
        ListView listaCarrito = (ListView)findViewById(R.id.miCarritoLV);

        listaCarrito.setAdapter(adapter);
    }

    private void veriricarCarVacioTV(){
        if (CategoriasActivity.productosCar.isEmpty()){
            carVacio.setVisibility(View.VISIBLE);
        } else carVacio.setVisibility(View.INVISIBLE);
    }

    private void actualizarTotal(){
        total.setText("Precio total: " + "$" + String.format("%.2f", CategoriasActivity.precioCarrito));
    }

    /*private void setupAddPaymentButton() {
        findViewById(R.id.EnterPays_addAtomPayment).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                adapter.insert(new AtomPayment("", 0), 0);
            }
        });
    }*/
}
