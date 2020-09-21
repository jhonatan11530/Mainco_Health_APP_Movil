package com.Mainco.App;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONArray;

public class OlvidoActivity extends AppCompatActivity {

    TSS textToSpeech = null;
    private EditText id, cedula;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.olvido);

        id = findViewById(R.id.CDUNICO);
        cedula = findViewById(R.id.cc);
        textToSpeech = new TSS();
        textToSpeech.init(this);

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

    public void comprobar(View v) {


        id = findViewById(R.id.CDUNICO);
        cedula = findViewById(R.id.cc);
        if (id.getText().toString().length() == 0) {
            id.setError("ID ES REQUERIDO !");
        }
        if (cedula.getText().toString().length() == 0) {
            cedula.setError("CONTRASEÑA ES REQUERIDO !");
        } else {

            new Thread(new Runnable() {
                @Override
                public void run() {
                    String response = HttpRequest.get("http://" + cambiarIP.ip + "/validar/olvido.php?cedula=" + id.getText().toString()).body();

                    try {
                        JSONArray objecto = new JSONArray(response);

                        if (objecto.length() > 0) {

                            new Thread(new Runnable() {
                                @Override
                                public void run() {
                                    HttpRequest.get("http://" + cambiarIP.ip + "/validar/olvido.php?cedula=" + id.getText().toString() + "&pass=" + cedula.getText().toString()).body();

                                    try {

                                        runOnUiThread(new Runnable() {
                                            @Override
                                            public void run() {


                                                AlertDialog.Builder builder = new AlertDialog.Builder(OlvidoActivity.this);

                                                builder.setTitle("SE RESTAURO LA CONTRASEÑA");
                                                builder.setMessage("Se cambio la contraseña exitosamente");
                                                textToSpeech.speak("Se cambio la contraseña exitosamente");
                                                builder.setPositiveButton("Iniciar Sesiòn", new DialogInterface.OnClickListener() {
                                                    @Override
                                                    public void onClick(DialogInterface dialogInterface, int i) {
                                                        Intent e = new Intent(getApplicationContext(), LoginActivity.class);
                                                        startActivity(e);
                                                        finish();
                                                    }
                                                });
                                                AlertDialog alert = builder.create();
                                                alert.show();
                                                alert.setCanceledOnTouchOutside(false);

                                            }
                                        });
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }


                                }
                            }).start();

                        } else {

                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(getApplicationContext(), "NO SE ENCUENTRA EL ID O USUARIO", Toast.LENGTH_SHORT).show();

                                }
                            });


                        }

                    } catch (Exception e) {

                        e.printStackTrace();
                    }

                }
            }).start();
        }
    }


}
