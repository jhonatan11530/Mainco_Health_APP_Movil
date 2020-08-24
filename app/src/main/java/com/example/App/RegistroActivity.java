package com.example.App;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.loopj.android.http.AsyncHttpClient;

public class RegistroActivity extends AppCompatActivity {

    EditText nombre, apellido, cedula, pass;
    CheckBox resultado;
    Button registro;
    AsyncHttpClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registro);

        client = new AsyncHttpClient();


        nombre = findViewById(R.id.nombre);
        apellido = findViewById(R.id.apellido);
        cedula = findViewById(R.id.cedula);
        pass = findViewById(R.id.pass);

        resultado = findViewById(R.id.rol);

        registro = findViewById(R.id.registro);


    }

    public void onBackPressed() {

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.salir, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        if (item.getItemId() == R.id.salir) {
            Intent e = new Intent(getApplicationContext(), LoginActivity.class);
            startActivity(e);
        }
        return super.onOptionsItemSelected(item);
    }


    public void registrar(View v) {

        nombre = findViewById(R.id.nombre);
        apellido = findViewById(R.id.apellido);
        cedula = findViewById(R.id.cedula);
        pass = findViewById(R.id.pass);
        resultado = findViewById(R.id.rol);

        if (nombre.getText().toString().length() == 0) {
            nombre.setError("NOMBRE ES REQUERIDO !");
        }
        if (apellido.getText().toString().length() == 0) {
            apellido.setError("APELLIDO ES REQUERIDO !");
        }
        if (cedula.getText().toString().length() == 0) {
            cedula.setError("CEDULA ES REQUERIDO !");
        }
        if (pass.getText().toString().length() == 0) {
            pass.setError("CONTRASEÑA ES REQUERIDO !");
        } else {

            new Thread(new Runnable() {
                @Override
                public void run() {

                    if (resultado.isChecked()) {

                        String ROL = "operador";

                        HttpRequest.get("http://" + cambiarIP.ip + "/validar/registro.php?nombre=" + nombre.getText().toString() + "&apellido=" + apellido.getText().toString() + "&cedula=" + cedula.getText().toString() + "&pass=" + pass.getText().toString() + "&rol=" + ROL).body();


                        try {

                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {


                                    AlertDialog.Builder builder = new AlertDialog.Builder(RegistroActivity.this);

                                    builder.setTitle("REGISTRO DE USUARIO EXITOSO");

                                    builder.setMessage("Se completo el registro exitosamente");


                                    builder.setPositiveButton("Iniciar Sesiòn", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialogInterface, int i) {


                                            Intent e = new Intent(getApplicationContext(), LoginActivity.class);
                                            startActivity(e);
                                            finish();
                                        }
                                    });

                                    builder.create().show();
                                }
                            });

                        } catch (Exception e) {
                            // TODO: handle exception
                            e.printStackTrace();
                        }

                    } else {

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {


                                AlertDialog.Builder builder = new AlertDialog.Builder(RegistroActivity.this);

                                builder.setTitle("NO SE PUDO REGISTRAR DEBE SELECCIONAR EL ROL DEL USUARIO");

                                builder.setMessage("el usuario debe tener un rol asignado");


                                builder.setPositiveButton("VOLVER AL REGISTRO ", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {

                                    }
                                });

                                builder.create().show();
                            }
                        });
                    }

                }
            }).start();


        }
    }


}
