package co.com.edu.udea.supermappket.activity;

import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import co.com.edu.udea.supermappket.utils.DataBaseManager;
import co.com.edu.udea.supermappket.R;
import co.com.edu.udea.supermappket.utils.DataPreferences;

public class MainActivity extends Activity {

    static DataBaseManager manager;
    DataPreferences dp;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

        manager = new DataBaseManager(this);
        dp = new DataPreferences(MainActivity.this);

        if(dp.getLoggedIn()) {
            Intent intent = new Intent(MainActivity.this, CategoriasActivity.class);
            intent.putExtra("idUser", dp.getUserId());
            finish();
            startActivity(intent);
        }else {
            ImageButton btnIngresar = (ImageButton) findViewById(R.id.btnIngresar);
            final EditText etUsuario = (EditText) findViewById(R.id.etUsuario);
            final EditText etPassword = (EditText) findViewById(R.id.etPass);

            btnIngresar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    try {
                        String usuario = etUsuario.getText().toString();
                        String password = etPassword.getText().toString();

                        if (usuario == null || "".equals(usuario)) {
                            Toast.makeText(getBaseContext(), "Ingrese el nombre de usuario", Toast.LENGTH_SHORT).show();
                        } else if (password == null || "".equals(password)) {
                            Toast.makeText(getBaseContext(), "Ingrese la contraseña", Toast.LENGTH_SHORT).show();
                        } else {
                            int idUsuario = manager.obtenerIdUsuario(usuario, password);
                            if (idUsuario != 0) {
                                dp.setLoggedIn(true);
                                dp.setUserId(idUsuario);
                                Intent intent = new Intent(MainActivity.this, CategoriasActivity.class);
                                intent.putExtra("idUser", idUsuario);
                                finish();


                                startActivity(intent);
                                //Toast.makeText(getBaseContext(), "Existe papá!! " + Integer.toString(idUsuario), Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(getBaseContext(), "Usuario o contraseña incorrecta", Toast.LENGTH_SHORT).show();
                            }
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
        }
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
}
