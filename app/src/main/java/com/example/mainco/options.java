package com.example.mainco;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;


import android.content.DialogInterface;
import android.content.Intent;

import android.os.Bundle;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

import Introducciones.CLOSE_OP;
import Introducciones.CREAR_USER;
import Introducciones.INTRODUCCION;
import Introducciones.PAUSE_ACTIVAS;
import Introducciones.RECUPERAR_PASSWORD;
import Introducciones.REGISTRO_PRODUCIDO;
import Introducciones.SYSTEM;
import Introducciones.TERMINADO;


public class options extends AppCompatActivity {

    private ListView componentes;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_options );



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
                Intent e = new Intent(getApplicationContext(), OperadorActivity.class);
                startActivity(e);
                break;

            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }
    public void masinfo(View v){

       runOnUiThread( new Runnable() {
           @Override
           public void run() {




                       View tutorial = getLayoutInflater().inflate(R.layout.tutorial,null);
                       androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(options.this);
               builder.setTitle( "TUTORIALES" );
                       componentes = tutorial.findViewById( R.id.tutorial );
                       final ArrayList<HELPCOMP> comphelp= new ArrayList<HELPCOMP>();
                       comphelp.add(new HELPCOMP( "INTRODUCCION A MAINCO APP" ));
                       comphelp.add(new HELPCOMP( "COMO CREO UN USUARIO ?" ));
                       comphelp.add(new HELPCOMP( "COMO RECUPERO MI CONTRASEÑA ?" ));
                       comphelp.add(new HELPCOMP( "COMO INGRESO AL SISTEMA ?" ));
                       comphelp.add(new HELPCOMP( "COMO EMPIEZO A REGISTRAR ?" ));
                       comphelp.add(new HELPCOMP( "COMO REGISTRO MIS PAUSAS ?" ));
                       comphelp.add(new HELPCOMP( "COMO SABER SI SE CERRO LA O.P ?" ));
                       comphelp.add(new HELPCOMP( "COMO SABER SI YA TERMINE ?" ));

                       ArrayAdapter<HELPCOMP> adapdtador = new ArrayAdapter<HELPCOMP>( options.this,android.R.layout.simple_dropdown_item_1line,comphelp );
                       componentes.setAdapter( adapdtador );

                       componentes.setOnItemClickListener( new AdapterView.OnItemClickListener() {
                           @Override
                           public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {

                               HELPCOMP H =comphelp.get( position );

                               if (H.getNombre() == "INTRODUCCION A MAINCO APP"){

                                   Intent e = new Intent(getApplicationContext(), INTRODUCCION.class);
                                   startActivity(e);
                               }

                               else if (H.getNombre() == "COMO CREO UN USUARIO ?"){

                                   Intent e = new Intent(getApplicationContext(), CREAR_USER.class);
                                   startActivity(e);
                               }

                               else if (H.getNombre() == "COMO RECUPERO MI CONTRASEÑA ?"){

                                   Intent e = new Intent(getApplicationContext(), RECUPERAR_PASSWORD.class);
                                   startActivity(e);
                               }

                               else if (H.getNombre() == "COMO INGRESO AL SISTEMA ?"){

                                   Intent e = new Intent(getApplicationContext(), SYSTEM.class);
                                   startActivity(e);
                               }

                               else if (H.getNombre() == "COMO EMPIEZO A REGISTRAR ?"){

                                   Intent e = new Intent(getApplicationContext(), REGISTRO_PRODUCIDO.class);
                                   startActivity(e);
                               }

                               else if (H.getNombre() == "COMO REGISTRO MIS PAUSAS ?"){

                                   Intent e = new Intent(getApplicationContext(), PAUSE_ACTIVAS.class);
                                   startActivity(e);
                               }

                               else if (H.getNombre() == "COMO SABER SI SE CERRO LA O.P ?"){

                                   Intent e = new Intent(getApplicationContext(), CLOSE_OP.class);
                                   startActivity(e);
                               }

                               else if (H.getNombre() == "COMO SABER SI YA TERMINE ?"){

                                   Intent e = new Intent(getApplicationContext(), TERMINADO.class);
                                   startActivity(e);
                               }
                           }
                       } );

                       builder.setPositiveButton( "VOLVER", new DialogInterface.OnClickListener() {
                           @Override
                           public void onClick(DialogInterface dialogInterface, int i) {

                           }
                       } );
                       builder.setView( tutorial );
                       builder.create().show();


           }
       } );


            }

     public void support(View v){
         androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(options.this);
         builder.setTitle( "CONTACTO" );
         builder.setMessage( "si desea soporte o comunicarte con el desarrollador de la aplicacion Mainco Health Care.\n\n" +
                 "INFORMACION DEL DESARROLLADOR\n\n" +
                 "NOMBRE : JHONATAN FERNANDEZ MUÑOZ \n\n" +
                 "TELEFONO : 3114360830\n\n" +
                 "CORREO : jhonatan1153@hotmail.com" );
         builder.setPositiveButton( "aceptar", new DialogInterface.OnClickListener() {
             @Override
             public void onClick(DialogInterface dialogInterface, int i) {

             }
         } );
         builder.create().show();
     }

     public void info(View v){

         androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder( options.this );
         builder.setTitle( "APLICACION MAINCO HEALTH CARE" );
         builder.setMessage( "USO : " +
                 "La aplicacion Mainco Health care es una aplicacion de registro de informacion en el area de planta atravez de esta app se podra registrar la hora de entrada , salida , las cantidades produccidas las cantidades defectuosa , el registro de tiempo de descanso \n\n" +
                 "EXCLUSIVIDAD : " +
                 "La aplicacion Mainco Health care para dispositivos android es de uso exclusivo para la empresa MAINCO HEALTH CARE se prohibe el uso de esta app por personas (TERCEROS) sin previa autorizacion de MAINCO HEALTH CARE el uso no autorizado de esta app por terceros puede ser sancionado por la respectiva empresa \n\n"
         );



          builder.setPositiveButton( "VOLVER", new DialogInterface.OnClickListener() {
                     @Override
                     public void onClick(DialogInterface dialogInterface, int i) {

                     }
                 } );
         builder.create().show();

     }

     public void detalles(View v){
         androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(options.this);
         builder.setTitle( "INFORMACION DETALLADA DE LA APP" );
         builder.setMessage( "si desea soporte o comunicarte con el desarrollador de la aplicacion Mainco Health Care.\n\n" +
                 "INFORMACION DEL DESARROLLADOR\n\n" +
                 "NOMBRE : JHONATAN FERNANDEZ MUÑOZ \n\n" +
                 "TELEFONO : 3114360830\n\n" +
                 "CORREO : jhonatan1153@hotmail.com" );
         builder.setPositiveButton( "aceptar", new DialogInterface.OnClickListener() {
             @Override
             public void onClick(DialogInterface dialogInterface, int i) {

             }
         } );
         builder.create().show();



     }
        }