package com.example.mainco;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONArray;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class RegistroActivity extends AppCompatActivity {

    EditText id,nombre,apellido,cedula,pass;
    Spinner resultado;
    Button registro;
    TSSManager ttsManager=null;

    private AsyncHttpClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);
        setRequestedOrientation( ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        client = new AsyncHttpClient();

        ttsManager=new TSSManager();
        ttsManager.init(this);

        nombre = (EditText)findViewById(R.id.nombre);
        apellido = (EditText)findViewById(R.id.apellido);
        cedula = (EditText)findViewById(R.id.cedula);
        pass = (EditText)findViewById(R.id.pass);

        resultado = (Spinner)findViewById(R.id.rol);

        registro = (Button)findViewById(R.id.registro);

        llenarSpinner();


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
        switch (item.getItemId()) {

            case R.id.salir:
                Intent e = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(e);
                break;

            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }
    public  void llenarSpinner(){
        String url = "http://"+cambiarIP.ip+"/validar/RegistroSpinner.php";
        client.post(url, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                if(statusCode == 200){
                    cargarSpinner(new String(responseBody));
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

            }
        });
    }
    public void cargarSpinner(String cargarSpinner){
        ArrayList <roles> dato = new ArrayList <roles>();
        try {
            JSONArray objecto = new JSONArray(cargarSpinner);
            for(int i = 0; i< objecto.length(); i ++){
                roles a = new roles();
                a.setId(objecto.getJSONObject(i).getString("cargo"));
                dato.add(a);
            }
            ArrayAdapter <roles> a = new ArrayAdapter<roles> (this, android.R.layout.simple_dropdown_item_1line, dato );
            resultado.setAdapter(a);

        }catch (Exception e){
            e.printStackTrace();

        }

    }



    public void registrar (View v){

        nombre = (EditText)findViewById(R.id.nombre);
        apellido = (EditText)findViewById(R.id.apellido);
        cedula = (EditText)findViewById(R.id.cedula);
        pass = (EditText)findViewById(R.id.pass);



        resultado = (Spinner)findViewById(R.id.rol);

        final String ROL = resultado.getSelectedItem().toString();

        if ( nombre.getText().toString().length() == 0 ){
            nombre.setError( "NOMBRE ES REQUERIDO !" );
        }
        if ( apellido.getText().toString().length() == 0 ){
            apellido.setError( "APELLIDO ES REQUERIDO !" );
        }
        if ( cedula.getText().toString().length() == 0 ){
            cedula.setError( "CEDULA ES REQUERIDO !" );
        }
        if ( pass.getText().toString().length() == 0 ){
            pass.setError( "CONTRASEÑA ES REQUERIDO !" );
        }
        else{

            new Thread(new Runnable() {
                @Override
                public void run() {


                String response = HttpRequest.get("http://"+cambiarIP.ip+"/validar/registro.php?nombre="+nombre.getText().toString()+"&apellido="+apellido.getText().toString()+"&cedula="+cedula.getText().toString()+"&pass="+pass.getText().toString()+"&rol="+ROL.toString()).body();
                try {

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {


                            AlertDialog.Builder builder = new AlertDialog.Builder(RegistroActivity.this);

                            builder.setTitle("REGISTRO DE USUARIO EXITOSO");

                            builder.setMessage("Se completo el registro exitosamente");
                                ttsManager.initQueue("REGISTRO DE USUARIO EXITOSO");

                            builder.setPositiveButton("Iniciar Sesiòn", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {


                                    Intent e = new Intent(getApplicationContext(), LoginActivity.class);
                                    startActivity(e);
                                }
                            });

                            builder.create().show();
                            }
                        });

            }
                catch (Exception e) {
                // TODO: handle exception
                e.printStackTrace();
            }

                }
            }).start();


        }
    }



}
