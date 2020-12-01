package com.Mainco.App;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import Http_Conexion.HttpRequest;
import IP.cambiarIP;
import TSS.TTS;
import cz.msebera.android.httpclient.Header;

public class OlvidoActivity extends AppCompatActivity {

    TTS textToSpeech = null;
    private TextInputEditText id, cedula;
    private AsyncHttpClient olvido;

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.olvido);

        olvido = new AsyncHttpClient();

        textToSpeech = new TTS();
        textToSpeech.init(this);

        id = findViewById(R.id.CDUNICO);
        cedula = findViewById(R.id.cc);

    }

    public void onBackPressed() {


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.

        getMenuInflater().inflate(R.menu.atras, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        if (item.getItemId() == R.id.atras) {
            Intent e = new Intent(getApplicationContext(), LoginActivity.class);
            startActivity(e);
        }
        return super.onOptionsItemSelected(item);
    }

    public void comprobar(View v) {

        //noinspection ConstantConditions
        if (id.getText().toString().length() == 0) {
            id.setError("ID ES REQUERIDO !");
        }
        //noinspection ConstantConditions
        if (cedula.getText().toString().length() == 0) {
            cedula.setError("CONTRASEÑA ES REQUERIDO !");
        } else {

            String url ="http://" + cambiarIP.ip + "/validar/olvido.php?cedula=" + id.getText().toString();
            olvido.post(url, new AsyncHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            HttpRequest.get("http://" + cambiarIP.ip + "/validar/olvido.php?cedula=" + id.getText().toString() + "&pass=" + cedula.getText().toString()).body();
                        }
                    }).start();

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

                }

                @Override
                public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                    Toast.makeText(getApplicationContext(), "NO SE ENCUENTRA EL ID O USUARIO", Toast.LENGTH_SHORT).show();
                }
            });

        }
    }


}
