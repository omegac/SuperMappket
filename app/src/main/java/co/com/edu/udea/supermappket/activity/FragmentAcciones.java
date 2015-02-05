package co.com.edu.udea.supermappket.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Locale;

import co.com.edu.udea.supermappket.R;
import co.com.edu.udea.supermappket.zxing.integration.android.IntentIntegrator;
import co.com.edu.udea.supermappket.zxing.integration.android.IntentResult;

/**
 * Created by Alan on 03/01/2015.
 */
public class FragmentAcciones extends Fragment{

    /*static TextView saldoActual;
    static TextView precioCarrito;*/
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view;
        view = inflater.inflate(R.layout.fragment_acciones, container);
        ImageButton btnLista = (ImageButton) view.findViewById(R.id.btnListaIB);
        ImageButton btnSaldo = (ImageButton) view.findViewById(R.id.btnSaldoIB);
        ImageButton btnCarrito = (ImageButton) view.findViewById(R.id.btnCarritoIB);
        ImageButton btnQR= (ImageButton) view.findViewById(R.id.btnQrIB);

        TextView saldoActualTV = (TextView) view.findViewById(R.id.saldoTV);
        TextView precioCarritoTV = (TextView) view.findViewById(R.id.precioCarritoTV);
        saldoActualTV.setText("$" + String.format("%.2f", CategoriasActivity.saldo));
        precioCarritoTV.setText("$" + String.format("%.2f", CategoriasActivity.saldo));

        /*
         * Seccion para inicializar el listener del boton de las listas
         */
        btnLista.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //if(R.layout.activity_lista != getActivity())
                Intent intent = new Intent(getActivity(), ListaActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT);
                startActivity(intent);
            }
        });

        /*
         * Seccion para inicializar el listener del boton para ingresar el saldo actual
         */
        btnSaldo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder alertDialog = new AlertDialog.Builder(getActivity(), AlertDialog.THEME_HOLO_DARK);
                alertDialog.setTitle(R.string.titulo_dialog_saldo);
                alertDialog.setMessage("Ingrese su saldo.");
                final EditText input = new EditText(getActivity());

                input.setInputType(InputType.TYPE_CLASS_NUMBER|InputType.TYPE_NUMBER_FLAG_DECIMAL);
                input.setTextColor(Color.WHITE);
                input.setText(Double.toString(CategoriasActivity.saldo));
                input.selectAll();
                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.MATCH_PARENT);
                input.setLayoutParams(lp);

                alertDialog.setView(input);
                alertDialog.setIcon(R.drawable.money);

                alertDialog.setPositiveButton(R.string.aceptar_dialog_saldo,
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                double saldoIngresado;
                                try {
                                    saldoIngresado = Double.parseDouble(input.getText().toString());
                                    CategoriasActivity.actSaldoYPTotal(CategoriasActivity.Ingresar_Saldo, saldoIngresado);
                                    actualizarTVs();
                                }catch (Exception e){
                                    Toast.makeText(getActivity(),
                                            "Ingrese un numero valido.", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });

                alertDialog.setNegativeButton(R.string.cancelar_dialog,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        });

                alertDialog.show();
            }

        });

        /*
         * Seccion para inicializar el listener del boton que inicia el intent del carrito de compras
         */
        btnCarrito.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(),CarritoActivity.class);
                startActivity(intent);
            }
        });

        /*
         * asdasd
         */
        btnQR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                IntentIntegrator intentIntegrator = new IntentIntegrator(getActivity());
                intentIntegrator.initiateScan();
            }
        });

        return view;
    }



    /**
     * Called when the fragment is visible to the user and actively running.
     * This is generally
     * tied to {Activity#onResume() Activity.onResume} of the containing
     * Activity's lifecycle.
     */
    @Override
    public void onResume() {
        super.onResume();
        actualizarTVs();
    }

    public void actualizarTVs(){
        TextView saldoTV = (TextView) this.getView().findViewById(R.id.saldoTV);
        TextView precioTV = (TextView) this.getView().findViewById(R.id.precioCarritoTV);
        saldoTV.setText("$" + String.format("%.2f", CategoriasActivity.saldo));
        precioTV.setText("$" + String.format("%.2f", CategoriasActivity.precioCarrito));
    }
}
