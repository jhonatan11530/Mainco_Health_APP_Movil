package com.example.App;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextWatcher;
import android.text.style.ForegroundColorSpan;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.snackbar.Snackbar;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import cz.msebera.android.httpclient.Header;

public class OperadorActivity extends AppCompatActivity {


    private EditText id, cantidad, paro, fallas, items;
    private String falla, error,VaribleTOTAL;
    private TextView motivo, MOSTRAR, texto, autorizadoxop, resultados;
    private Spinner resuldato, resuldato2, resuldato4, resuldato3;

    private Button go, stop, btnconfir, desbloquear, positivo, neutrar, registroTIME, salidaTIME, validarinfo, cantidadund;

    private int minuto, i, hora, cantidadpro, volumencan, total, volumen;

    private ArrayList<cantidadfallas> dato4 = new ArrayList<cantidadfallas>();
    private ArrayList<cantidades> dato3 = new ArrayList<cantidades>();
    private ArrayList<motivoparo> dato2 = new ArrayList<motivoparo>();
    private ArrayList<OPS> dato = new ArrayList<OPS>();

    EditText edit, digito;
    View tiempo1, adelanto;
    AlertDialog.Builder registros;
    Date date;
    SimpleDateFormat hourFormat;
    SimpleDateFormat dateFormat;
    RelativeLayout production;

    private AsyncHttpClient cliente, cliente1, cliente2, cliente3, cliente4, cliente5;
    public Thread hilo, eliminaOK, registrar, operador, cantidadhilo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_operador );

        cliente = new AsyncHttpClient();
        cliente1 = new AsyncHttpClient();
        cliente2 = new AsyncHttpClient();
        cliente3 = new AsyncHttpClient();
        cliente4 = new AsyncHttpClient();
        cliente5 = new AsyncHttpClient();

        llenarSpinner();
        llenarOps();

        production = (RelativeLayout) findViewById( R.id.principal );


        resuldato = (Spinner) findViewById( R.id.spinner1 );

        resuldato2 = (Spinner) findViewById( R.id.spinner2 );

        resuldato3 = (Spinner) findViewById( R.id.spinner );

        items = (EditText) findViewById( R.id.item );

        cantidadund = (Button) findViewById( R.id.aplazar );

        desbloquear = (Button) findViewById( R.id.tiempo );

        registroTIME = (Button) findViewById( R.id.insertar );

        salidaTIME = (Button) findViewById( R.id.salida );

        id = (EditText) findViewById( R.id.operador );

        resultados = (TextView) findViewById( R.id.listar_operador );


        desbloquear.setEnabled( false );
        registroTIME.setEnabled( false );
        salidaTIME.setEnabled( false );
        cantidadund.setEnabled( false );

        desbloquear.setBackgroundColor( Color.parseColor( "#919191" ) );
        registroTIME.setBackgroundColor( Color.parseColor( "#919191" ) );
        salidaTIME.setBackgroundColor( Color.parseColor( "#919191" ) );
        cantidadund.setBackgroundColor( Color.parseColor( "#919191" ) );


        items.addTextChangedListener( new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence limpio, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence escribio, int start, int before, int count) {
                if(items.getText().toString()!= null){
                    FiltrarOps();
                }

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if(editable.toString().length() <1 ){
                    items.setError( "EL CAMPO NO DEBE ESTAR VACIO !!" );
                    llenarSpinner();
                }
                    /*
                    FiltrarOps();
                    resuldato.setAdapter( null );
                    filtraritem();
                    */

            }
        } );

    }

    public void onBackPressed() {
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate( R.menu.global, menu );

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        switch (item.getItemId()) {

            case R.id.atras:

                AlertDialog.Builder builder = new AlertDialog.Builder( OperadorActivity.this );

                String titleText = "SALIR";

                ForegroundColorSpan foregroundColorSpan = new ForegroundColorSpan( Color.parseColor( "#58B4E8" ) );

                SpannableStringBuilder ssBuilder = new SpannableStringBuilder( titleText );

                ssBuilder.setSpan(
                        foregroundColorSpan,
                        0,
                        titleText.length(),
                        Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
                );

                builder.setTitle( ssBuilder );

                builder.setMessage( "¿ ESTAS SEGURO QUE DESEA CERRAR SESIÒN ?" );

                builder.setPositiveButton( "ACEPTAR", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {


                        ProgressDialog pd = new ProgressDialog( OperadorActivity.this );

                        pd.setTitle( "CERRANDO SESION" );

                        pd.setMessage( "Porfavor espere" );
                        pd.setCanceledOnTouchOutside( false );

                        pd.show();


                        Intent e = new Intent( getApplicationContext(), LoginActivity.class );
                        startActivity( e );
                        finish();


                    }
                } );

                builder.setNegativeButton( "CANCELAR", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Toast.makeText( getApplicationContext(), "LA SESIÒN SE MANTENDRA ACTIVA", Toast.LENGTH_SHORT ).show();
                    }
                } );

                builder.create().show();
                break;

            case R.id.ayuda:
                Intent e = new Intent( getApplicationContext(), options.class );
                startActivity( e );

                break;
            default:
                break;
        }
        return super.onOptionsItemSelected( item );
    }

    public void filtraritem() {
        String url = "http://" + cambiarIP.ip + "/validar/cantidadfiltre.php?op=" + resuldato3.getSelectedItem().toString(); // SE DEBE CAMBIAR
        cliente.post( url, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                if (statusCode == 200) {
                    itemfiltre( new String( responseBody ) );

                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                filtraritem();
            }
        } );
    }

    public void itemfiltre(String itemfiltre) {
        ArrayList<cantidades> dato3 = new ArrayList<cantidades>();
        try {
            JSONArray objecto = new JSONArray( itemfiltre );
            for (int i = 0; i < objecto.length(); i++) {
                cantidades a = new cantidades();
                a.setTarea( objecto.getJSONObject( i ).getString( "tarea" ) );
                dato3.add( a );
            }
            ArrayAdapter<cantidades> a = new ArrayAdapter<cantidades>( this, android.R.layout.simple_dropdown_item_1line, dato3 );
            resuldato.setAdapter( a );


        } catch (Exception e) {
            e.printStackTrace();

        }

    }

    public void llenarOps() {

        String url = "http://" + cambiarIP.ip + "/validar/OPS.php";
        cliente2.post( url, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                if (statusCode == 200) {
                    cargarops( new String( responseBody ) );

                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                llenarOps();
            }
        } );
    }

    public void cargarops(String cargarops) {
        ArrayList<OPS> dato = new ArrayList<OPS>();
        try {
            JSONArray objecto = new JSONArray( cargarops );
            for (int i = 0; i < objecto.length(); i++) {
                OPS a = new OPS();

                a.setOps( objecto.getJSONObject( i ).getString( "cod_producto" ) );
                dato.add( a );
            }
            ArrayAdapter<OPS> a = new ArrayAdapter<OPS>( this, android.R.layout.simple_dropdown_item_1line, dato );
            resuldato3.setAdapter( a );


        } catch (Exception e) {
            e.printStackTrace();

        }

    }

    public void FiltrarOps() {

        String url = "http://" + cambiarIP.ip + "/validar/FiltroOPS.php?op=" + items.getText().toString();
        cliente1.post( url, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                if (statusCode == 200) {
                    filtroOPS( new String( responseBody ) );

                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                llenarOps();
            }
        } );
    }

    public void filtroOPS(String filtroOPS) {
        ArrayList<OPS> dato = new ArrayList<OPS>();
        try {
            JSONArray objecto = new JSONArray( filtroOPS );
            for (int i = 0; i < objecto.length(); i++) {
                OPS a = new OPS();

                a.setOps( objecto.getJSONObject( i ).getString( "cod_producto" ) );
                dato.add( a );
            }
            ArrayAdapter<OPS> a = new ArrayAdapter<OPS>( this, android.R.layout.simple_dropdown_item_1line, dato );
            resuldato3.setAdapter( a );

            if(resuldato3.getSelectedItem().toString() != null){
                filtraritem();
            }

        } catch (Exception e) {
            e.printStackTrace();

        }

    }

    public void llenarSpinner() {

        String url = "http://" + cambiarIP.ip + "/validar/cantidad.php";
        cliente3.post( url, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                if (statusCode == 200) {
                    cargarSpinner( new String( responseBody ) );

                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                llenarSpinner();
            }
        } );
    }

    public void cargarSpinner(String cargarSpinner) {
        ArrayList<cantidades> dato3 = new ArrayList<cantidades>();
        try {
            JSONArray objecto = new JSONArray( cargarSpinner );
            for (int i = 0; i < objecto.length(); i++) {
                cantidades a = new cantidades();
                a.setTarea( objecto.getJSONObject( i ).getString( "tarea" ) );
                dato3.add( a );
            }
            ArrayAdapter<cantidades> a = new ArrayAdapter<cantidades>( this, android.R.layout.simple_dropdown_item_1line, dato3 );
            resuldato.setAdapter( a );


        } catch (Exception e) {
            e.printStackTrace();

        }

    }

    public void llenardescanso() {
        String url = "http://" + cambiarIP.ip + "/validar/motivo.php";
        cliente4.post( url, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                if (statusCode == 200) {
                    filtrardescanso( new String( responseBody ) );
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                llenardescanso();
            }
        } );
    }

    public void filtrardescanso(String filtrardescanso) {
        ArrayList<motivoparo> dato2 = new ArrayList<motivoparo>();
        try {
            JSONArray objecto = new JSONArray( filtrardescanso );
            for (int i = 0; i < objecto.length(); i++) {
                motivoparo b = new motivoparo();
                b.setParo( objecto.getJSONObject( i ).getString( "paro" ) );
                dato2.add( b );
            }
            ArrayAdapter<motivoparo> a = new ArrayAdapter<motivoparo>( this, android.R.layout.simple_dropdown_item_1line, dato2 );
            resuldato2.setAdapter( a );

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void motivofalla() {
        String url = "http://" + cambiarIP.ip + "/validar/motivocantidad.php";
        cliente5.post( url, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                if (statusCode == 200) {
                    cargarmotivo( new String( responseBody ) );
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                motivofalla();
            }
        } );
    }

    public void cargarmotivo(String cargarmotivo) {
        ArrayList<cantidadfallas> dato4 = new ArrayList<cantidadfallas>();
        try {
            JSONArray objecto = new JSONArray( cargarmotivo );
            for (int i = 0; i < objecto.length(); i++) {
                cantidadfallas c = new cantidadfallas();
                c.setFallas( objecto.getJSONObject( i ).getString( "fallas" ) );
                dato4.add( c );
            }
            ArrayAdapter<cantidadfallas> a = new ArrayAdapter<cantidadfallas>( this, android.R.layout.simple_dropdown_item_1line, dato4 );
            resuldato4.setAdapter( a );

        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    public void operador(View v) {

        if (id.getText().toString().length() == 0) {

            id.setError( "ID ES REQUERIDO !" );

        }else {

            operador = new Thread( new Runnable() {
                @Override
                public void run() {

                    try {
                        String response = HttpRequest.get( "http://" + cambiarIP.ip + "/validar/operador.php?id=" + id.getText().toString() ).body();

                        JSONArray objecto = new JSONArray( response );
                        if (response.length()>0) {
                            System.out.println( "EL USER NO EXISTE"+response.length() );
                            cantidad();
                            runOnUiThread( new Runnable() {
                                @Override
                                public void run() {

                                    registroTIME.setEnabled( true );
                                    salidaTIME.setEnabled( true );
                                    cantidadund.setEnabled( true );
                                    desbloquear.setEnabled( true );

                                    desbloquear.setBackgroundColor( Color.parseColor( "#B2C900" ) );
                                    registroTIME.setBackgroundColor( Color.parseColor( "#B2C900" ) );
                                    cantidadund.setBackgroundColor( Color.parseColor( "#2196F3" ) );
                                    salidaTIME.setBackgroundColor( Color.parseColor( "#2196F3" ) );


                                }
                            } );


                            resultados.setText( objecto.getString( 0 ) );


                        }else{
                            System.out.println( "FALLO" );
                        }

                    } catch (Exception e) {
                        // TODO: handle exception

                    }
                }
            } );
            operador.start();


        }

    }


    public void verificar() {

        final String nombretarea = resuldato.getSelectedItem().toString();
        eliminaOK = new Thread( new Runnable() {
            @Override
            public void run() {
                String cero = HttpRequest.get( "http://" + cambiarIP.ip + "/validar/eliminarcanok.php?id=" + nombretarea ).body();

                try {
                    JSONArray nada = new JSONArray( cero );
                    int vacio = Integer.parseInt( nada.getString( 0 ) );
                    if (vacio != 0) {


                        runOnUiThread( new Runnable() {
                            @Override
                            public void run() {
                                ((TextView) resuldato.getSelectedView()).setTextColor( Color.BLACK );

                            }
                        } );


                    } else if (vacio == 0) {

                        runOnUiThread( new Runnable() {
                            @Override
                            public void run() {

                                desbloquear.setBackgroundColor( Color.parseColor( "#919191" ) );
                                registroTIME.setBackgroundColor( Color.parseColor( "#919191" ) );
                                cantidadund.setBackgroundColor( Color.parseColor( "#919191" ) );
                                salidaTIME.setBackgroundColor( Color.parseColor( "#919191" ) );

                                desbloquear.setEnabled( false );
                                registroTIME.setEnabled( false );
                                salidaTIME.setEnabled( false );
                                cantidadund.setEnabled( false );

                                ((TextView) resuldato.getSelectedView()).setTextColor( Color.RED );


                                AlertDialog.Builder builder = new AlertDialog.Builder( OperadorActivity.this );
                                builder.setTitle( "LA OP FINALIZO " );
                                builder.setMessage( "DEBE SELECCIONAR OTRA OP " );

                                builder.setPositiveButton( "REGISTRAR OTRA OP", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        new Thread( new Runnable() {
                                            @Override
                                            public void run() {
                                                String cero = HttpRequest.get( "http://" + cambiarIP.ip + "/validar/nuevoRegistro.php?id=" + id.getText().toString() ).body();
                                            }
                                        } ).start();
                                    }
                                } );
                                builder.create().show();


                            }
                        } );


                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        } );
        eliminaOK.start();
        Thread.interrupted();

    }

    public void hora(View v) {

        registros = new AlertDialog.Builder( OperadorActivity.this );
        tiempo1 = getLayoutInflater().inflate( R.layout.dialog_spinner, null );

        llenardescanso();
        id = (EditText) findViewById( R.id.operador );

        paro = (EditText) tiempo1.findViewById( R.id.paro );
        MOSTRAR = (TextView) tiempo1.findViewById( R.id.MOSTRAR );
        texto = (TextView) tiempo1.findViewById( R.id.textos );
        stop = (Button) tiempo1.findViewById( R.id.stop );
        go = (Button) tiempo1.findViewById( R.id.go );
        validarinfo = (Button) tiempo1.findViewById( R.id.validarinfo );


        motivo = (TextView) tiempo1.findViewById( R.id.MOTIVO );
        resuldato2 = (Spinner) tiempo1.findViewById( R.id.spinner2 );


        //botones

        MOSTRAR.setVisibility( View.INVISIBLE );
        paro.setVisibility( View.INVISIBLE );
        stop.setVisibility( View.INVISIBLE );
        go.setVisibility( View.INVISIBLE );
        texto.setVisibility( View.INVISIBLE );
        resuldato2.setVisibility( View.INVISIBLE );


        //TEXTVIEW Y SPINNER
        motivo.setVisibility( View.VISIBLE );
        resuldato2.setVisibility( View.VISIBLE );

        validarinfo.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                validarinfo.setVisibility( View.INVISIBLE );
                resuldato2.setClickable( false );
                resuldato2.setEnabled( false );


                MOSTRAR.setVisibility( View.VISIBLE );
                paro.setVisibility( View.VISIBLE );
                stop.setVisibility( View.VISIBLE );
                go.setVisibility( View.VISIBLE );
                texto.setVisibility( View.VISIBLE );
                resuldato2.setVisibility( View.VISIBLE );


                runOnUiThread( new Runnable() {
                    @Override
                    public void run() {
                        String mostrardatos = resuldato2.getSelectedItem().toString();
                        Toast.makeText( getApplicationContext(), "USTED SELECCIÓNO " + mostrardatos, Toast.LENGTH_SHORT ).show();
                    }
                } );

            }
        } );


        registros.setPositiveButton( "FINALIZAR", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Toast.makeText( getApplicationContext(), "LOS DATOS FUERON GUARDADOS CORRECTAMENTE", Toast.LENGTH_SHORT ).show();
            }
        } );


        registros.setNegativeButton( "REGISTRAR", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {


                Toast.makeText( getApplicationContext(), "DATOS VERIFICADOS", Toast.LENGTH_SHORT ).show();


            }
        } );
        registros.setNeutralButton( "REGISTRAR OTRO MOTIVO DE PARO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        } );


        stop.setEnabled( false );

        registros.setView( tiempo1 );
        registros.create();
        AlertDialog alert = registros.create();
        alert.show();

        alert.setCanceledOnTouchOutside( false );

        desbloquear = alert.getButton( AlertDialog.BUTTON_NEGATIVE );
        neutrar = alert.getButton( AlertDialog.BUTTON_NEUTRAL );
        positivo = alert.getButton( AlertDialog.BUTTON_POSITIVE );

        neutrar.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                positivo.setEnabled( false );
                neutrar.setEnabled( false );
                desbloquear.setEnabled( false );
                go.setEnabled( true );

                MOSTRAR.setVisibility( View.INVISIBLE );
                paro.setVisibility( View.INVISIBLE );
                stop.setVisibility( View.INVISIBLE );
                go.setVisibility( View.INVISIBLE );
                texto.setVisibility( View.INVISIBLE );


                validarinfo.setVisibility( View.VISIBLE );
                motivo.setVisibility( View.VISIBLE );
                resuldato2.setClickable( true );
                resuldato2.setEnabled( true );
                resuldato2.setVisibility( View.VISIBLE );
                paro.setText( "" );
                MOSTRAR.setText( "0:0:0" );
            }
        } );


        desbloquear.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                desbloquear.setEnabled( false );
                neutrar.setEnabled( true );
                positivo.setEnabled( true );

                final String prueba = resuldato2.getSelectedItem().toString();

                // imprime fecha
                dateFormat = new SimpleDateFormat( "dd/MM/yyyy", Locale.getDefault() );
                date = new Date();

                //imprime hora
                hourFormat = new SimpleDateFormat( "H:mm:ss" );

                final String horas = hourFormat.format( date );
                final String fechas = dateFormat.format( date );

                new Thread( new Runnable() {

                    @Override
                    public void run() {

                        String response = HttpRequest.get( "http://" + cambiarIP.ip + "/validar/RegistrarMotivo.php?op=" + items.getText().toString() + "&id=" + id.getText().toString() + "&paro=" + paro.getText().toString() + "&motivo=" + prueba + "&fecha=" + fechas + "&hora=" + horas ).body();

                    }
                } ).start();
                Thread.interrupted();
                minuto = 0;
                hora = 0;

            }
        } );

        positivo.setEnabled( false );
        neutrar.setEnabled( false );
        desbloquear.setEnabled( false );
    }

    public void go(View v) {


        go.setEnabled( false );

        hilo = new Thread( new Runnable() {

            @Override
            public void run() {

                try {

                    i = 0;
                    minuto = 0;
                    hora = 0;

                    for (i = 0; i <= 60; i++) {
                        System.out.println( i );

                        Thread.sleep( 1000 );

                        if (i == 60) {
                            i = 0;
                            minuto++;

                        }

                        if (minuto == 59) {
                            minuto = 0;
                            hora++;
                        }

                        if (hora == 12) {
                            hora = 0;

                        }

                        final String segundo = Integer.toString( i );
                        final String minutos = Integer.toString( minuto );
                        final String horas = Integer.toString( hora );

                        runOnUiThread( new Runnable() {
                            @Override
                            public void run() {

                                MOSTRAR.setText( horas + ":" + minuto + ":" + segundo );

                            }
                        } );

                    }

                } catch (Exception e) {
                    // TODO: handle exception
                    e.printStackTrace();


                }


            }


        } );
        hilo.start();
        hilo.setPriority( Thread.NORM_PRIORITY );
        if (hilo != null) {
            stop.setEnabled( true );


        }

    }

    public void stop(View v) {
        stop.setEnabled( false );
        go.setEnabled( false );
        desbloquear.setEnabled( true );
        if (paro != null) {

            motivo.setVisibility( View.VISIBLE );
            resuldato2.setVisibility( View.VISIBLE );
            hilo.interrupt();

        }

        String d = Integer.toString( minuto );
        paro.setText( d );

    }

    public void registrar(View v) {

        id = (EditText) findViewById( R.id.operador );


        // imprime fecha
        dateFormat = new SimpleDateFormat( "dd/MM/yyyy", Locale.getDefault() );
        date = new Date();

        //imprime hora
        hourFormat = new SimpleDateFormat( "H:mm:ss" );

        //almacena los datos en una cadena
        final String hora = hourFormat.format( date );

        final String fecha = dateFormat.format( date );

        AlertDialog.Builder builder = new AlertDialog.Builder( OperadorActivity.this );
        builder.setTitle( "DATOS INSERTADOS" );
        builder.setMessage( "PORFAVOR EMPIECE SU LABOR" );
        edit = new EditText( this );
        edit.setEnabled( false );
        edit.setText( fecha );

        final String fechas = edit.getText().toString();
        final String Nop = resuldato3.getSelectedItem().toString();
        final String tarea = resuldato.getSelectedItem().toString();
        builder.setPositiveButton( "Aceptar", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {

                // TODO Auto-generated method stub
                registrar = new Thread( new Runnable() {
                    @Override
                    public void run() {
                        cantidad();
                        try {
                            final String nombretarea = resuldato.getSelectedItem().toString();
                            String response = HttpRequest.get( "http://" + cambiarIP.ip + "/validar/Sobrante.php?op=" + resuldato3.getSelectedItem().toString() + "&tarea=" + nombretarea ).body();
                            JSONArray validar = new JSONArray( response );
                            int validator = Integer.parseInt( validar.getString( 0 ) );
                            System.out.println( "EL VALOR " + validator );

                            if (validator == 0) {

                                System.out.println( "ESTO SUCEDIO" );
                                String responses = HttpRequest.get( "http://" + cambiarIP.ip + "/validar/cantidadedits.php?op=" + resuldato3.getSelectedItem().toString() ).body();

                                JSONArray RESTARCANTIDAD = new JSONArray( responses );

                                HttpRequest.get( "http://" + cambiarIP.ip + "/validar/cantidadmodifi.php?op=" + resuldato3.getSelectedItem().toString() + "&tarea=" + tarea + "&totales=" + RESTARCANTIDAD.getString( 0 ) ).body();
                                cantidad();
                            }
                            if (validator > 0) {
                                cantidad();
                                runOnUiThread( new Runnable() {
                                    @Override
                                    public void run() {
                                        AlertDialog.Builder builder = new AlertDialog.Builder( OperadorActivity.this );
                                        builder.setTitle( "HAY CANTIDADES PENDIENTES" );
                                        builder.setIcon( R.drawable.informacion );
                                        builder.setMessage( "DEBE TERMINAR LAS CANTIDADES PENDIENTES" );

                                        builder.setPositiveButton( "ACEPTAR", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialogInterface, int i) {

                                            }
                                        } );
                                        builder.create().show();
                                    }
                                } );

                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        HttpRequest.get( "http://" + cambiarIP.ip + "/validar/actualizaEntrada.php?id=" + id.getText().toString() + "&Finicial=" + fechas + "&Hinicial=" + hora + "&op=" + items.getText().toString() ).body();

                        runOnUiThread( new Runnable() {
                            @Override
                            public void run() {
                                registroTIME.setEnabled( false );
                            }
                        } );


                    }
                } );
                registrar.start();
                registrar.setPriority( Thread.MIN_PRIORITY );
                Thread.interrupted();
            }
        } );

        builder.create().show();

    }
class Task extends AsyncTask<String, Void, String> {

    @Override
    protected String doInBackground(String... strings) {
        String nombretarea = resuldato.getSelectedItem().toString();
        String response = HttpRequest.get("http://"+cambiarIP.ip+"/validar/Sobrante.php?op="+resuldato3.getSelectedItem().toString()+"&tarea="+ nombretarea ).body();

        try {
            JSONArray array = new JSONArray(response);
            VaribleTOTAL = array.getString( 0 );
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return VaribleTOTAL;
    }

    @Override
    protected void onPostExecute(final String VaribleTOTAL) {
        super.onPostExecute( VaribleTOTAL );

       /* Toast toast = Toast.makeText( getApplicationContext(),"CANTIDAD EN O.P : "+VaribleTOTAL,100000 );
        toast.setGravity( Gravity.BOTTOM,0,0);
        toast.show();*/
       runOnUiThread( new Runnable() {
           @Override
           public void run() {
               Snackbar.make(findViewById(R.id.principal ),"CANTIDAD EN O.P : "+VaribleTOTAL,Snackbar.LENGTH_INDEFINITE).show();

           }
       } );

    }
}
    public void cantidad() {
       new Task().execute();
    }

    public void salida (View v) {

        motivofalla();


        id=(EditText)findViewById(R.id.operador);
        View view = getLayoutInflater().inflate(R.layout.cantidad_produccidas,null);
        resuldato4 =(Spinner)view.findViewById(R.id.spinner2);
        fallas = (EditText)view.findViewById(R.id.fallas);
        cantidad = (EditText)view.findViewById(R.id.digicantidad);


         final String tarea = resuldato.getSelectedItem().toString(); //**

        // imprime fecha
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        Date date = new Date();

        //imprime hora
        SimpleDateFormat hourFormat = new SimpleDateFormat("H:mm:ss");

        //almacena los datos en una cadena
        final	String horafinal = hourFormat.format(date);
        final	String fechafinal = dateFormat.format(date);

        final AlertDialog.Builder builder = new AlertDialog.Builder(OperadorActivity.this);


        ArrayAdapter <cantidadfallas> a = new ArrayAdapter<cantidadfallas> (this, android.R.layout.simple_dropdown_item_1line, dato4 );
        resuldato4.setAdapter(a);

        EditText edit = new EditText(this);
        edit.setEnabled(false);
        edit.setText(fechafinal);

        EditText editt = new EditText(this);
        editt.setEnabled(false);
        editt.setText(horafinal);

        final   String fechas =edit.getText().toString();
        final   String horas =editt.getText().toString();
        builder.setPositiveButton("VERIFICAR", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                if(fallas.getText().toString().length() == 0 && cantidad.getText().toString().length() == 0){
                    fallas.setError("DEBE LLENARSE");
                    cantidad.setError("DEBE LLENARSE");
                }
                else{



            volumen = Integer.parseInt(cantidad.getText().toString());
             falla = fallas.getText().toString();
            error = resuldato4.getSelectedItem().toString();


                final String nombretarea = resuldato.getSelectedItem().toString();

                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        String response = HttpRequest.get("http://"+cambiarIP.ip+"/validar/Sobrante.php?op="+resuldato3.getSelectedItem().toString()+"&tarea="+ nombretarea ).body();

                        try {

                            JSONArray RESTARCANTIDAD = new JSONArray(response);

                            total = Integer.parseInt( falla );
                            volumencan = Integer.parseInt(cantidad.getText().toString());
                             cantidadpro = Integer.parseInt(RESTARCANTIDAD.getString(0));
                                int tool = volumencan + total;
                                int end = cantidadpro - tool;

                            System.out.println( "LA CANTIDAD DIGITADA ES "+volumencan );
                            System.out.println( "LA CANTIDAD MALAS ES "+total );
                            System.out.println( "LA CANTIDAD EN MYSQL "+cantidadpro );
                            System.out.println( "LA CANTIDAD EN MYSQL RESTADA "+end );


                            int valorCantOP = cantidadpro - total;

                            if(end >= 0){

                                 HttpRequest.get("http://"+cambiarIP.ip+"/validar/canbiarAucOP.php?op="+items.getText().toString()+"&item="+resuldato3.getSelectedItem().toString()+"&cantidad="+valorCantOP).body();

                                HttpRequest.get("http://"+cambiarIP.ip+"/validar/cantidadmodifi.php?op="+resuldato3.getSelectedItem().toString()+"&tarea="+ nombretarea +"&totales="+end).body();

                                HttpRequest.get("http://"+cambiarIP.ip+"/validar/actualizaSalida.php?id="+id.getText().toString()+"&cantidad="+volumen+"&Ffinal="+fechas+"&Hfinal="+horas+"&motivo="+error+"&conforme="+falla+"&tarea="+nombretarea+"&op="+items.getText().toString()).body();

                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {


                                        Toast.makeText(getApplicationContext(),"DATOS VERIFICADOS", Toast.LENGTH_SHORT).show();

                                        verificar();
                                        cantidad();
                                    }
                                });


                            }else if (end < 0){

                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {

                                        String titleText = "EXEDIO LA CANTIDAD DE PRODUCCION AUTORIZADA";
                                        ForegroundColorSpan foregroundColorSpan = new ForegroundColorSpan( Color.parseColor("#E82F2E"));
                                        SpannableStringBuilder ssBuilder = new SpannableStringBuilder(titleText);
                                        ssBuilder.setSpan(
                                                foregroundColorSpan,
                                                0,
                                                titleText.length(),
                                                Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
                                        );
                                        AlertDialog.Builder builder = new AlertDialog.Builder(OperadorActivity.this);
                                        builder.setTitle(ssBuilder);
                                        builder.setIcon(R.drawable.peligro);
                                        builder.setMessage("USTED EXEDIO LA CANTIDAD PERMITIDA POR LA O.P");
                                        builder.setNegativeButton("VOLVER A REGISTRAR", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialogInterface, int i) {

                                            }
                                        });


                                        builder.create().show();


                                    }
                                });

                            }


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }



                    }
                }).start();
                Thread.interrupted();

                }
            }
        });


        builder.setView(view);
        builder.create();
        AlertDialog alert = builder.create();
        alert.show();
        alert.setCanceledOnTouchOutside(false);

    }

    public void aplazo(View v){

        AlertDialog.Builder aplazarproduccion = new AlertDialog.Builder(OperadorActivity.this);
       adelanto = getLayoutInflater().inflate(R.layout.aplazar_produccion,null);
        autorizadoxop = (TextView)adelanto.findViewById(R.id.Cantidadops);
        resuldato4 =(Spinner)adelanto.findViewById(R.id.spinner2);
        fallas = (EditText)adelanto.findViewById(R.id.fallas);
         btnconfir = (Button)adelanto.findViewById(R.id.CONFIRMARADE);
        digito = (EditText)adelanto.findViewById(R.id.digicantidad);

        digito.setVisibility( View.VISIBLE );
        btnconfir.setVisibility( View.VISIBLE );

        motivofalla();

        ArrayAdapter <cantidadfallas> a = new ArrayAdapter<cantidadfallas> (this, android.R.layout.simple_dropdown_item_1line, dato4 );
        resuldato4.setAdapter(a);

        btnconfir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(digito.getText().toString().length() == 0){
                    digito.setError("DEBE LLENARSE");
                }if(digito.getText().toString().length() != 0){

                // imprime fecha
                SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
                Date date = new Date();

                //imprime hora
                SimpleDateFormat hourFormat = new SimpleDateFormat("H:mm:ss");

                //almacena los datos en una cadena
                final	String horafinal = hourFormat.format(date);
                final	String fechafinal = dateFormat.format(date);

               final EditText edit = new EditText(OperadorActivity.this);
                edit.setEnabled(false);
                edit.setText(fechafinal);

                final EditText editt = new EditText(OperadorActivity.this);
                editt.setEnabled(false);
                editt.setText(horafinal);



                        final   String fechas =edit.getText().toString();
                        final   String horas =editt.getText().toString();

                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        final String tarea = resuldato.getSelectedItem().toString();
                        final String Nop = items.getText().toString();
                        volumencan = Integer.parseInt(digito.getText().toString());
                        final int cantmalas = Integer.parseInt(fallas.getText().toString());
                        error = resuldato4.getSelectedItem().toString();


                        HttpRequest.get("http://"+cambiarIP.ip+"/validar/actualizarcantidad.php?numero="+ Nop +"&id="+id.getText().toString()+"&canpen="+volumencan+"&malo="+cantmalas+"&motivo="+error+"&Ffinal="+fechas+"&Hfinal="+horas+"&tarea="+ tarea ).body();

                        final String nombretarea = resuldato.getSelectedItem().toString();
                        try {
                            Thread.sleep(1000);

                            String responses = HttpRequest.get("http://"+cambiarIP.ip+"/validar/Sobrante.php?op="+resuldato3.getSelectedItem().toString()+"&tarea="+ nombretarea ).body();


                            try {
                                JSONArray RESTARCANTIDAD = new JSONArray(responses);
                                cantidadpro = Integer.parseInt(RESTARCANTIDAD.getString(0));

                                final int BuenasMalas = volumencan + cantmalas;
                                final int totalade = cantidadpro - BuenasMalas;

                                int valorCantOP = cantidadpro - cantmalas;

                                if(totalade >= 0){
                                    HttpRequest.get("http://"+cambiarIP.ip+"/validar/canbiarAucOP.php?op="+items.getText().toString()+"&item="+resuldato3.getSelectedItem().toString()+"&cantidad="+valorCantOP).body();

                                  HttpRequest.get("http://"+cambiarIP.ip+"/validar/cantidadmodifi.php?op="+resuldato3.getSelectedItem().toString()+"&tarea="+ nombretarea +"&totales="+totalade).body();


                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            autorizadoxop.setText( "CANTIDAD RESTANTE : "+totalade );
                                            Toast.makeText(getApplicationContext(),"SE REGISTRO EL ADELANTO PRODUCCIDO ", Toast.LENGTH_SHORT).show();

                                            verificar();
                                            cantidad();

                                        }
                                    });
                                }
                                else if (totalade < 0){

                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            try {
                                                Thread.sleep(500);
                                                String titleText = "EXEDIO LA CANTIDAD DE PRODUCCION AUTORIZADA";
                                                ForegroundColorSpan foregroundColorSpan = new ForegroundColorSpan( Color.parseColor("#E82F2E"));
                                                SpannableStringBuilder ssBuilder = new SpannableStringBuilder(titleText);
                                                ssBuilder.setSpan(
                                                        foregroundColorSpan,
                                                        0,
                                                        titleText.length(),
                                                        Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
                                                );
                                                AlertDialog.Builder builder = new AlertDialog.Builder(OperadorActivity.this);
                                                builder.setTitle(ssBuilder);
                                                builder.setIcon(R.drawable.peligro);
                                                builder.setMessage("USTED EXEDIO LA CANTIDAD PERMITIDA POR LA O.P");
                                                builder.setNegativeButton("VOLVER A REGISTRAR", new DialogInterface.OnClickListener() {
                                                    @Override
                                                    public void onClick(DialogInterface dialogInterface, int i) {

                                                    }
                                                });


                                                builder.create().show();
                                            } catch (InterruptedException e) {
                                                e.printStackTrace();
                                            }

                                        }
                                    });

                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }


                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }

                    }
                }).start();
                Thread.interrupted();

                }
           }
        });
        aplazarproduccion.setPositiveButton("SALIR", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        aplazarproduccion.setView(adelanto);
        aplazarproduccion.create();
        AlertDialog alert = aplazarproduccion.create();
        alert.show();
        alert.setCanceledOnTouchOutside(false);


    }
}

