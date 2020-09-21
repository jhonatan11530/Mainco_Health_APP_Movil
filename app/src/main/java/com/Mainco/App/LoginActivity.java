package com.Mainco.App;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiConfiguration;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;


@SuppressWarnings("ALL")
public class LoginActivity extends AppCompatActivity {

    final WifiConfiguration conf = new WifiConfiguration();
    EditText login, pass;
    Button validar;
    TextView registre;
    TSS textToSpeech = null;
    CheckBox GUARDARUTO;
    ProgressDialog pd;
    String networkSSID = "WIFIMainco";
    String networkPass = "A125277935";

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        mostrarguardado();

        textToSpeech = new TSS();
        textToSpeech.init(this);

        GUARDARUTO = findViewById(R.id.OK);

        login = findViewById(R.id.estado);
        pass = findViewById(R.id.ID);

        registre = findViewById(R.id.registre);

        validar = findViewById(R.id.login);


    }

    public void onBackPressed() {

        //  Intent e = new Intent(getApplicationContext(), Modulos.class);
        // startActivity(e);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.ayuda, menu);


        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        if (item.getItemId() == R.id.ayuda) {
            Intent e = new Intent(getApplicationContext(), options.class);
            startActivity(e);
        }
        return super.onOptionsItemSelected(item);
    }

    public void validartodo(View v) {
        if (pass.getText().toString().length() == 0 && login.getText().toString().length() == 0) {

            pass.setError("CONTRASEÑA ES REQUERIDO !");

            login.setError("ID ES REQUERIDO !");

        } else if (pass.getText().toString().length() != 0 && login.getText().toString().length() != 0) {


            ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

            if (networkInfo != null && networkInfo.isConnected()) {


                new TASK().execute(login.getText().toString(), pass.getText().toString());


            } else {
                AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
                builder.setTitle("NO ESTAS CONECTADO A INTERNET");
                builder.setMessage("Porfavor verifica la conexion a internet");
                builder.setPositiveButton("ACEPTAR", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });

                AlertDialog alert = builder.create();
                alert.show();
                alert.setCanceledOnTouchOutside(false);
            }

        }
    }

    public void guardar() {

        SharedPreferences preferences = getSharedPreferences("ARCHIVO_LOGIN", Context.MODE_PRIVATE);
        SharedPreferences shared = getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor usu = shared.edit();
        usu.putString("usuario", login.getText().toString());
        usu.commit();

    }

    public void mostrarguardado() {

        SharedPreferences mostrardato = getPreferences(Context.MODE_PRIVATE);
        final String user = mostrardato.getString("usuario", "");

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(1000);
                    login.setText(user);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    public void registro(View v) {

        Intent e = new Intent(getApplicationContext(), RegistroActivity.class);
        startActivity(e);

    }

    public void olvidoC(View v) {
        Intent e = new Intent(getApplicationContext(), OlvidoActivity.class);
        startActivity(e);
    }

    class TASK extends AsyncTask<String, Void, String> {

        @Override
        protected void onPreExecute() {

            pd = new ProgressDialog(LoginActivity.this);
            pd.setTitle("INICIANDO SESION");
            pd.setMessage("Porfavor espere");

            pd.show();
            pd.setCancelable(false);
            pd.setCanceledOnTouchOutside(false);


        }

        @Override
        protected void onCancelled(String s) {
            pd.cancel();

            runOnUiThread(new Runnable() {
                @Override
                public void run() {

                    String titleText = "ERROR AL INICIAR SESSION";
                    ForegroundColorSpan foregroundColorSpan = new ForegroundColorSpan(Color.parseColor("#E82F2E"));

                    SpannableStringBuilder ssBuilder = new SpannableStringBuilder(titleText);

                    ssBuilder.setSpan(
                            foregroundColorSpan,
                            0,
                            titleText.length(),
                            Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
                    );

                    final AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
                    builder.setTitle(ssBuilder);
                    builder.setIcon(R.drawable.peligro);
                    builder.setMessage("VERIFIQUE EL USUARIO Y CONTRASEÑA");
                    textToSpeech.speak("ERROR AL INICIAR SESIÓN. VERIFIQUE EL USUARIO Y CONTRASEÑA");
                    builder.setPositiveButton("ACEPTAR", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            builder.setCancelable(true);
                        }
                    });
                    AlertDialog alert = builder.create();
                    alert.show();
                    alert.setCanceledOnTouchOutside(false);

                }
            });

        }

        @Override
        protected String doInBackground(final String... strings) {


            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        String response = HttpRequest.get("http://" + cambiarIP.ip + "/validar/validar.php?cedula=" + login.getText().toString() + "&pass=" + pass.getText().toString()).body();
                        System.out.println("NO SE " + response.length());
                        if (response.length() > 0) {
                            textToSpeech.speak("BIENVENIDO");
                            Intent e = new Intent(getApplicationContext(), OperadorActivity.class);
                            startActivity(e);

                            if (GUARDARUTO.isChecked()) {

                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Toast.makeText(getApplicationContext(), "SE GUARDO EL USUARIO Y CONTRASEÑA", Toast.LENGTH_SHORT).show();

                                        guardar();

                                    }
                                });


                            }

                        } else {


                            onCancelled(null);


                        }


                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }).start();

            return strings[0];
        }

    }

}
