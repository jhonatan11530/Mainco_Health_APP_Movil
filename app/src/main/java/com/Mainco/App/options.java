package com.Mainco.App;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import java.util.ArrayList;

import GET_SET.HELPCOMP;
import IP.cambiarIP;
import Introducciones.CLOSE_OP;
import Introducciones.CREAR_USER;
import Introducciones.INTRODUCCION;
import Introducciones.PAUSE_ACTIVAS;
import Introducciones.RECUPERAR_PASSWORD;
import Introducciones.REGISTRO_PRODUCIDO;
import Introducciones.SYSTEM;
import Introducciones.TERMINADO;
import cz.msebera.android.httpclient.Header;

public class options extends AppCompatActivity {

    private final boolean activar = true;
    private ListView componentes;
    private Button button;
    private ProgressDialog builder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.options);

        button = findViewById(R.id.chequear);

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
            finish();
        }
        return super.onOptionsItemSelected(item);
    }


    public void dianostico(View v) {
        button.setEnabled(false);

        builder = new ProgressDialog(options.this);
        builder.setTitle("ESPERANDO CONEXION");
        builder.setMessage("PORFAVOR ESPERE");
        builder.show();
        builder.setCancelable(true);
        builder.setCanceledOnTouchOutside(false);

        AsyncHttpClient service = new AsyncHttpClient();
        service.setConnectTimeout(28800000);
        service.setResponseTimeout(10000);
        String url = "http://" + cambiarIP.ip + "/validar/ConectarServer/conexion_al_servidor.php";
        service.post(url, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(final int statusCode, Header[] headers, byte[] responseBody) {
                if (activar) {
                    builder.cancel();
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            AlertDialog.Builder builder = new AlertDialog.Builder(options.this);
                            builder.setTitle("Dianostico de la aplicacion");
                            builder.setIcon(R.drawable.dianostico);
                            builder.setMessage("ESTADO DE LA CONEXIÓN : CONECTADO \n\n CODIGO HTTP : " + statusCode + " \n\n TIEMPO DE RESPUESTA : 1MS");

                            builder.setPositiveButton("ACEPTAR", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    button.setEnabled(true);
                                }
                            });
                            AlertDialog alert = builder.create();
                            alert.show();
                            alert.setCanceledOnTouchOutside(false);
                        }
                    });
                }
            }

            @Override
            public void onFailure(final int statusCode, Header[] headers, final byte[] responseBody, Throwable error) {
                if (activar) {
                    builder.cancel();
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            AlertDialog.Builder builder = new AlertDialog.Builder(options.this);
                            builder.setTitle("Dianostico de la aplicacion");
                            builder.setIcon(R.drawable.dianostico);
                            builder.setMessage("ESTADO DE LA CONEXIÓN : FALLO LA CONEXIÓN CON EL SERVIDOR \n\n CODIGO HTTP : 500 \n\n TIEMPO DE RESPUESTA : 10000MS");

                            builder.setPositiveButton("ACEPTAR", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    button.setEnabled(true);

                                }
                            });
                            AlertDialog alert = builder.create();
                            alert.show();
                            alert.setCanceledOnTouchOutside(false);
                        }
                    });
                }
            }

            @Override
            public void onRetry(int retryNo) {
                System.out.println("ESTO PASO # INTENTOS " + retryNo);
            }
        });

    }


    public void masinfo(View v) {

        runOnUiThread(new Runnable() {
            @Override
            public void run() {


                View tutorial = getLayoutInflater().inflate(R.layout.tutorial, null);
                androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(options.this);
                builder.setTitle("TUTORIALES");
                componentes = tutorial.findViewById(R.id.tutorial);
                final ArrayList<HELPCOMP> comphelp = new ArrayList<>();
                comphelp.add(new HELPCOMP("INTRODUCCION A MAINCO APP"));
                comphelp.add(new HELPCOMP("COMO CREO UN USUARIO ?"));
                comphelp.add(new HELPCOMP("COMO RECUPERO MI CONTRASEÑA ?"));
                comphelp.add(new HELPCOMP("COMO INGRESO AL SISTEMA ?"));
                comphelp.add(new HELPCOMP("COMO EMPIEZO A REGISTRAR ?"));
                comphelp.add(new HELPCOMP("COMO REGISTRO MIS PAUSAS ?"));
                comphelp.add(new HELPCOMP("COMO SABER SI SE CERRO LA O.P ?"));
                comphelp.add(new HELPCOMP("COMO SABER SI YA TERMINE ?"));

                ArrayAdapter<HELPCOMP> adapdtador = new ArrayAdapter<>(options.this, android.R.layout.simple_dropdown_item_1line, comphelp);
                componentes.setAdapter(adapdtador);

                componentes.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {

                        HELPCOMP H = comphelp.get(position);

                        switch (H.getNombre()) {
                            case "INTRODUCCION A MAINCO APP": {

                                Intent e = new Intent(getApplicationContext(), INTRODUCCION.class);
                                startActivity(e);
                                break;
                            }
                            case "COMO CREO UN USUARIO ?": {

                                Intent e = new Intent(getApplicationContext(), CREAR_USER.class);
                                startActivity(e);
                                break;
                            }
                            case "COMO RECUPERO MI CONTRASEÑA ?": {

                                Intent e = new Intent(getApplicationContext(), RECUPERAR_PASSWORD.class);
                                startActivity(e);
                                break;
                            }
                            case "COMO INGRESO AL SISTEMA ?": {

                                Intent e = new Intent(getApplicationContext(), SYSTEM.class);
                                startActivity(e);
                                break;
                            }
                            case "COMO EMPIEZO A REGISTRAR ?": {

                                Intent e = new Intent(getApplicationContext(), REGISTRO_PRODUCIDO.class);
                                startActivity(e);
                                break;
                            }
                            case "COMO REGISTRO MIS PAUSAS ?": {

                                Intent e = new Intent(getApplicationContext(), PAUSE_ACTIVAS.class);
                                startActivity(e);
                                break;
                            }
                            case "COMO SABER SI SE CERRO LA O.P ?": {

                                Intent e = new Intent(getApplicationContext(), CLOSE_OP.class);
                                startActivity(e);
                                break;
                            }
                            case "COMO SABER SI YA TERMINE ?": {

                                Intent e = new Intent(getApplicationContext(), TERMINADO.class);
                                startActivity(e);
                                break;
                            }
                        }
                    }
                });

                builder.setPositiveButton("VOLVER", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
                builder.setView(tutorial);
                builder.create().show();


            }
        });


    }

    public void support(View v) {
        androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(options.this);
        builder.setTitle("CONTACTO");
        builder.setMessage("si desea soporte o comunicarte con el desarrollador de la aplicacion Mainco Health app.\n\n" +
                "INFORMACION DEL DESARROLLADOR\n\n" +
                "NOMBRE : JHONATAN FERNANDEZ MUÑOZ \n\n" +
                "TELEFONO : 3114360830\n\n" +
                "CORREO : jhonatan1153@hotmail.com");
        builder.setPositiveButton("ACEPTAR", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });

        builder.create().show();
    }

    public void info(View v) {

        androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(options.this);
        builder.setTitle("APLICACION MAINCO HEALTH CARE");
        builder.setMessage("USO : " +
                "La aplicacion Mainco Health app es una aplicacion de registro de informacion para el area de planta a travez de esta app se podra registrar la hora de entrada , salida , las cantidades produccidas las cantidades defectuosa , el registro de tiempo de descanso \n\n" +
                "EXCLUSIVIDAD : " +
                "La aplicacion Mainco Health app para dispositivos android es de uso exclusivo para la empresa MAINCO HEALTH CARE se prohibe el uso de esta app por personas (TERCEROS) sin previa autorizacion de MAINCO HEALTH CARE el uso no autorizado de esta app por terceros puede ser sancionado por la respectiva empresa o puede ser sansionado por las leyes del pais\n\n"
        );


        builder.setPositiveButton("ACEPTAR ", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        builder.setNegativeButton("CONOCER LICENCIA", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                String url = "http://192.168.20.9:8080/LICENSE.html";
                Uri uri = Uri.parse(url);
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
            }
        });
        builder.create().show();

    }


}
