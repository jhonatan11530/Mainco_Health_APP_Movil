package com.example.mainco;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiManager;

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
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;


import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.List;



public class LoginActivity extends AppCompatActivity {

    EditText login, pass;
    Button validar;
    TextView registre;

    ArrayList com;
    CheckBox GUARDARUTO;
    ProgressDialog pd;
    String version_actual= "1.2";

    String version_firebase;
    String url_firebase;


    String networkSSID = "WIFIMainco";
    String networkPass = "A125277935";
    WifiConfiguration conf = new WifiConfiguration();

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        setRequestedOrientation( ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        mostrarguardado();
        Obtener_Firebase();
         GUARDARUTO = (CheckBox) findViewById(R.id.OK);

      login = (EditText)findViewById(R.id.estado);
         pass = (EditText)findViewById(R.id.ID);

        registre = (TextView)findViewById(R.id.registre);

        validar = (Button)findViewById(R.id.login);


        conf.SSID = "\"" + networkSSID + "\"";
        conf.preSharedKey = "\""+ networkPass +"\"";

        WifiManager wifiManager = (WifiManager)getApplicationContext().getSystemService(Context.WIFI_SERVICE);

        if ((wifiManager.isWifiEnabled() == false)) {
            Toast.makeText( LoginActivity.this, "Conectando a Mainco.", Toast.LENGTH_LONG ).show();
            wifiManager.setWifiEnabled( true );

        }

        wifiManager.addNetwork(conf);
        List<WifiConfiguration> list = wifiManager.getConfiguredNetworks();
        for( WifiConfiguration i : list ) {
            if(i.SSID != null && i.SSID.equals("\"" + networkSSID + "\"")) {

                wifiManager.enableNetwork(i.networkId, true);


                break;
            }
        }
    }



    public  void Obtener_Firebase(){
        FirebaseDatabase database=FirebaseDatabase.getInstance();
        DatabaseReference referencia_version,referencia_url;



        referencia_url=database.getReference("url");
        referencia_url.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                url_firebase=dataSnapshot.getValue().toString();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText( LoginActivity.this,"URL "+databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        referencia_version=database.getReference("version");
        referencia_version.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                version_firebase=dataSnapshot.getValue().toString();

                if(version_firebase.trim().equals(version_actual.trim())){


                }
                else{

                    Intent pantaActualizar=new Intent(getApplicationContext(),Pantalla_Actualizar.class);
                    pantaActualizar.putExtra("version",version_firebase);
                    pantaActualizar.putExtra("url",url_firebase);
                    finish();
                    startActivity(pantaActualizar);
                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
               Toast.makeText( LoginActivity.this,"Version "+databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });







    }

    class TASK extends AsyncTask<String,Void,String>{

        @Override
        protected void onPreExecute() {
             pd = new ProgressDialog( LoginActivity.this);
            pd.setTitle("INICIANDO SESION");
            pd.setMessage("Porfavor espere");

            pd.show();

        }

        @Override
        protected void onCancelled(String s) {
            try {
                Thread.sleep( 2000 );
                pd.cancel();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            runOnUiThread( new Runnable() {
                @Override
                public void run() {
                    String titleText = "ERROR AL INICIAR SESSION";
                    ForegroundColorSpan foregroundColorSpan = new ForegroundColorSpan( Color.parseColor("#E82F2E"));

                    SpannableStringBuilder ssBuilder = new SpannableStringBuilder(titleText);

                    ssBuilder.setSpan(
                            foregroundColorSpan,
                            0,
                            titleText.length(),
                            Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
                    );

                   final AlertDialog.Builder builder = new AlertDialog.Builder( LoginActivity.this );
                    builder.setTitle(ssBuilder);
                    builder.setIcon(R.drawable.peligro);

                    builder.setMessage( "verifique el usuario o contraseña " );
                    builder.setPositiveButton( "ACEPTAR", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            builder.setCancelable(true);
                        }
                    } );
                    builder.create().show();

                }
            } );

        }

        @Override
        protected String doInBackground(final String... strings) {

            new Thread( new Runnable() {
                @Override
                public void run() {
                    try {
                    String response = HttpRequest.get("http://"+cambiarIP.ip+"/validar/validar.php?cedula="+login.getText().toString()+"&pass="+pass.getText().toString()).body();
                        System.out.println( "COSITA "+response.toString());




                        if(response.length() >0) {

                            Intent e = new Intent(getApplicationContext(), OperadorActivity.class);
                            startActivity(e);

                            if(GUARDARUTO.isChecked()==true){

                                runOnUiThread( new Runnable() {
                                    @Override
                                    public void run() {
                                        Toast.makeText( getApplicationContext(),"SE GUARDO EL USUARIO Y CONTRASEÑA",Toast.LENGTH_SHORT).show();

                                        guardar();

                                    }
                                } );


                            }

                        }else{


                            onCancelled(null);


                        }



                    }catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            } ).start();

            return strings[0];
        }

    }

    public void onBackPressed() {

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
        switch (item.getItemId()) {


            case R.id.ayuda:
                Intent e = new Intent(getApplicationContext(), options.class);
                startActivity(e);

                break;


            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }


        public void validartodo(View view){
            if(pass.getText().toString().length() == 0 && login.getText().toString().length() == 0) {

                pass.setError("CONTRASEÑA ES REQUERIDO !");

                login.setError("ID ES REQUERIDO !");

            }else if (pass.getText().toString() != null && login.getText().toString() != null){


                ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

                if (networkInfo != null && networkInfo.isConnected()) {


                    new TASK().execute( login.getText().toString(),pass.getText().toString() );


                } else {
                    AlertDialog.Builder builder = new AlertDialog.Builder( LoginActivity.this );
                    builder.setTitle( "NO ESTAS CONECTADO A INTERNET" );
                    builder.setMessage( "Porfavor verifica la conexion a internet" );
                    builder.setPositiveButton( "ACEPTAR", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                        }
                    } );

                    builder.create().show();
                }

        }
}

public void guardar(){

    SharedPreferences preferences = getSharedPreferences("ARCHIVO_LOGIN", Context.MODE_PRIVATE );
    SharedPreferences shared = getPreferences(Context.MODE_PRIVATE);
    SharedPreferences.Editor usu = shared.edit();
    usu.putString("usuario",login.getText().toString());
    usu.commit();

}
public void mostrarguardado(){

    SharedPreferences mostrardato = getPreferences( Context.MODE_PRIVATE );
   final String user = mostrardato.getString( "usuario","" );

    new Thread( new Runnable() {
        @Override
        public void run() {
            try {
                Thread.sleep( 2000 );
            login.setText( user );
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    } ).start();
}



    public void registro (View v) {

        Intent e = new Intent(getApplicationContext(), RegistroActivity.class);
        startActivity(e);

    }


    public void olvidoC (View v){
        Intent e = new Intent(getApplicationContext(), OlvidoActivity.class);
        startActivity(e);
    }

}
