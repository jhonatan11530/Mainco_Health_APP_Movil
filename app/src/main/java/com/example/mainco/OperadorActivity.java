package com.example.mainco;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.os.Bundle;
import android.os.PowerManager;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import Introducciones.REGISTRO_PRODUCIDO;
import Introducciones.SYSTEM;
import Introducciones.CREAR_USER;
import Introducciones.TERMINADO;
import Introducciones.PAUSE_ACTIVAS;
import Introducciones.CLOSE_OP;
import Introducciones.RECUPERAR_PASSWORD;
import Introducciones.INTRODUCCION;
import cz.msebera.android.httpclient.Header;

public class OperadorActivity extends AppCompatActivity {


    private EditText id, cantidad, paro, fallas,resuldato3;
    private String falla,error;
    private TextView motivo, MOSTRAR,texto,totalcan,tex,resultados;
    private Spinner resuldato,resuldato2,  resuldato4;

    private  Button go, stop, btnconfir,desbloquear,positivo,neutrar, registroTIME, salidaTIME,validarinfo,cantidadund,btnvalidar;

    private int minuto, i, hora,cantidadpro,volumencan,total,totals,datoverifica,volumen;

    private ArrayList<cantidadfallas> dato4 = new ArrayList<cantidadfallas>();
    EditText edit,digito;
    View tiempo1,adelanto;
    AlertDialog.Builder registros;
    Date date;
    SimpleDateFormat hourFormat;
    SimpleDateFormat dateFormat;
    private RadioGroup grupo;
    private AsyncHttpClient client;
    private AsyncHttpClient clientes;
    private AsyncHttpClient clientes2;
    private AsyncHttpClient clientes3;
    public Thread hilo,eliminaOK,MOP;
    private RadioButton botonSi,botonNo;
    private ListView componentes;

    protected PowerManager.WakeLock wakelock;
    @SuppressLint("InvalidWakeLockTag")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_operador);

        client = new AsyncHttpClient();
        clientes = new AsyncHttpClient();
        clientes2 = new AsyncHttpClient();
        clientes3 = new AsyncHttpClient();

        setRequestedOrientation( ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);




        final PowerManager pm=(PowerManager)getSystemService(Context.POWER_SERVICE);
        this.wakelock=pm.newWakeLock(PowerManager.SCREEN_DIM_WAKE_LOCK, "etiqueta");
        wakelock.acquire();

        resuldato = (Spinner) findViewById(R.id.spinner1);

        totalcan = (TextView)findViewById(R.id.CANTIDADID);

        resuldato2 = (Spinner) findViewById(R.id.spinner2);

        resuldato3 = (EditText) findViewById(R.id.spinner);

        cantidadund = (Button) findViewById(R.id.aplazar);

        desbloquear = (Button) findViewById(R.id.tiempo);

        registroTIME = (Button) findViewById(R.id.insertar);

        salidaTIME = (Button) findViewById(R.id.salida);

        id = (EditText) findViewById(R.id.operador);

        resultados = (TextView) findViewById(R.id.listar_operador);




        llenarSpinner();


        desbloquear.setEnabled(false);
        registroTIME.setEnabled(false);
        salidaTIME.setEnabled(false);
        cantidadund.setEnabled(false);



    }
    protected void onDestroy(){
        super.onDestroy();

        this.wakelock.release();
    }
    protected void onResume(){
        super.onResume();
        wakelock.acquire();
    }
    public void onSaveInstanceState(Bundle icicle) {
        super.onSaveInstanceState(icicle);
        this.wakelock.release();
    }
    public void onBackPressed() {

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.global, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        switch (item.getItemId()) {

            case R.id.atras:

                AlertDialog.Builder builder = new AlertDialog.Builder(OperadorActivity.this);

                String titleText = "SALIR";

                ForegroundColorSpan foregroundColorSpan = new ForegroundColorSpan(Color.parseColor("#58B4E8"));

                SpannableStringBuilder ssBuilder = new SpannableStringBuilder(titleText);

                ssBuilder.setSpan(
                        foregroundColorSpan,
                        0,
                        titleText.length(),
                        Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
                );

                builder.setTitle(ssBuilder);

                builder.setMessage("¿ ESTAS SEGURO QUE DESEA CERRAR SESIÒN ?");

                builder.setPositiveButton("ACEPTAR", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {


                        ProgressDialog pd = new ProgressDialog(OperadorActivity.this);

                        pd.setTitle("CERRANDO SESION");

                        pd.setMessage("Porfavor espere");
                        pd.setCanceledOnTouchOutside(false);

                        pd.show();



                        Intent e = new Intent(getApplicationContext(), LoginActivity.class);
                        startActivity(e);



                    }
                });

                builder.setNegativeButton("CANCELAR", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Toast.makeText(getApplicationContext(),"LA SESIÒN SE MANTENDRA ACTIVA", Toast.LENGTH_SHORT).show();
                    }
                });

                builder.create().show();
                break;

            case R.id.ayuda:

                runOnUiThread( new Runnable() {
                    @Override
                    public void run() {
                        View help = getLayoutInflater().inflate(R.layout.ayuda,null);

                        androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder( OperadorActivity.this );
                        builder.setTitle("ASISTENCIA O INFORMACION ");
                        Button acercaapp = (Button)help.findViewById( R.id.ACERCA );
                        Button ayuda = (Button)help.findViewById( R.id.HELP);
                        Button soport = (Button)help.findViewById( R.id.SOPORTE );


                        ayuda.setOnClickListener( new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {

                                View tutorial = getLayoutInflater().inflate(R.layout.tutorial,null);
                                androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(OperadorActivity.this);
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

                                ArrayAdapter<HELPCOMP> adapdtador = new ArrayAdapter<HELPCOMP>( OperadorActivity.this,android.R.layout.simple_dropdown_item_1line,comphelp );
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

                                builder.setPositiveButton( "salir", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {

                                    }
                                } );
                                builder.setView( tutorial );
                                builder.create().show();


                            }
                        } );
                        soport.setOnClickListener( new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(OperadorActivity.this);
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
                        } );
                        acercaapp.setOnClickListener( new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {

                                androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder( OperadorActivity.this );
                                builder.setTitle( "APLICACION MAINCO HEALTH CARE" );
                                builder.setMessage( "USO : " +
                                        "La aplicacion Mainco Health care es una aplicacion de registro de informacion en el area de planta atravez de esta app se podra registrar la hora de entrada , salida , las cantidades produccidas las cantidades defectuosa , el registro de tiempo de descanso \n\n" +
                                        "EXCLUSIVIDAD : " +
                                        "La aplicacion Mainco Health care para dispositivos android es de uso exclusivo para la empresa MAINCO HEALTH CARE se prohibe el uso de esta app por personas (TERCEROS) sin previa autorizacion de MAINCO HEALTH CARE el uso no autorizado de esta app por terceros puede ser sancionado por la respectiva empresa \n\n"
                                );

                                builder.setNegativeButton( "ACUERDO DE LICENCIA", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {

                                        androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder( OperadorActivity.this );
                                        builder.setTitle( "ACUERDO DE LICENCIA" );
                                        builder.setMessage( "\n" +
                                                "Licencia Apache\n" +
                                                "Versión 2.0, enero de 2004\n" +
                                                "http://www.apache.org/licenses/\n" +
                                                "\n" +
                                                "TÉRMINOS Y CONDICIONES DE USO, REPRODUCCIÓN Y DISTRIBUCIÓN\n" +
                                                "\n" +
                                                "1. Definiciones.\n" +
                                                "\n" +
                                                "\"Licencia\" significará los términos y condiciones de uso, reproducción,\n" +
                                                "y distribución como se define en las Secciones 1 a 9 de este documento.\n" +
                                                "\n" +
                                                "\"Licenciante\" se refiere al propietario de los derechos de autor o entidad autorizada por\n" +
                                                "el propietario de los derechos de autor que otorga la Licencia.\n" +
                                                "\n" +
                                                "\"Entidad legal\" significará la unión de la entidad actuante y todos\n" +
                                                "otras entidades que controlan, son controladas o están en común\n" +
                                                "control con esa entidad. A los fines de esta definición,\n" +
                                                "\"control\" significa (i) el poder, directo o indirecto, para causar la\n" +
                                                "dirección o gestión de dicha entidad, ya sea por contrato o\n" +
                                                "de lo contrario, o (ii) propiedad del cincuenta por ciento (50%) o más del\n" +
                                                "acciones en circulación, o (iii) titularidad de dicha entidad.\n" +
                                                "\n" +
                                                "\"Usted\" (o \"Su\") significará una persona física o jurídica\n" +
                                                "ejercer los permisos otorgados por esta Licencia.\n" +
                                                "\n" +
                                                "El formulario \"fuente\" significará el formulario preferido para realizar modificaciones,\n" +
                                                "incluidos, entre otros, el código fuente del software, la documentación\n" +
                                                "fuente y archivos de configuración.\n" +
                                                "\n" +
                                                "La forma de \"objeto\" significará cualquier forma resultante de la mecánica\n" +
                                                "transformación o traducción de un formulario fuente, que incluye pero\n" +
                                                "no limitado al código objeto compilado, documentación generada,\n" +
                                                "y conversiones a otros tipos de medios.\n" +
                                                "\n" +
                                                "\"Trabajo\" significará el trabajo de autoría, ya sea en Fuente o\n" +
                                                "Formulario de objeto, disponible bajo la Licencia, como lo indica un\n" +
                                                "aviso de copyright incluido o adjunto a la obra\n" +
                                                "(se proporciona un ejemplo en el apéndice a continuación).\n" +
                                                "\n" +
                                                "\"Trabajos derivados\" significará cualquier trabajo, ya sea en Fuente u Objeto\n" +
                                                "forma, que se basa en (o deriva de) el Trabajo y para el cual\n" +
                                                "revisiones editoriales, anotaciones, elaboraciones u otras modificaciones\n" +
                                                "representan, en su conjunto, una obra original de autoría. Para los fines\n" +
                                                "de esta Licencia, los Trabajos derivados no incluirán trabajos que permanezcan\n" +
                                                "separable de, o simplemente enlace (o enlace por nombre) a las interfaces de,\n" +
                                                "el trabajo y trabajos derivados del mismo.\n" +
                                                "\n" +
                                                "\"Contribución\" significará cualquier trabajo de autoría, incluyendo\n" +
                                                "La versión original de la Obra y cualquier modificación o adición.\n" +
                                                "a ese trabajo o trabajos derivados del mismo, que es intencionalmente\n" +
                                                "enviado al Licenciante para su inclusión en el Trabajo por el propietario de los derechos de autor\n" +
                                                "o por una persona física o jurídica autorizada a presentar en nombre de\n" +
                                                "El propietario de los derechos de autor. A los efectos de esta definición, \"presentado\"\n" +
                                                "significa cualquier forma de comunicación electrónica, verbal o escrita enviada\n" +
                                                "al Licenciante o sus representantes, incluidos, entre otros,\n" +
                                                "comunicación en listas de correo electrónico, sistemas de control de código fuente,\n" +
                                                "y sistemas de seguimiento de problemas gestionados por, o en nombre de,\n" +
                                                "Licenciante con el propósito de discutir y mejorar el Trabajo, pero\n" +
                                                "excluyendo la comunicación que está marcada de manera notoria o de otra manera\n" +
                                                "designado por escrito por el propietario de los derechos de autor como \"No es una contribución\".\n" +
                                                "\n" +
                                                "\"Colaborador\" significa el Licenciante y cualquier persona física o jurídica.\n" +
                                                "en nombre de quién ha recibido una Contribución el Licenciante y\n" +
                                                "posteriormente incorporado dentro del Trabajo.\n" +
                                                "\n" +
                                                "2. Concesión de licencia de copyright. Sujeto a los términos y condiciones de\n" +
                                                "esta Licencia, cada Colaborador por la presente le otorga un perpetuo,\n" +
                                                "mundial, no exclusivo, sin cargo, libre de regalías, irrevocable\n" +
                                                "licencia de copyright para reproducir, preparar trabajos derivados de,\n" +
                                                "mostrar públicamente, realizar públicamente, sublicenciar y distribuir el\n" +
                                                "Trabajo y tales trabajos derivados en forma de fuente u objeto.\n" +
                                                "\n" +
                                                "3. Concesión de licencia de patente. Sujeto a los términos y condiciones de\n" +
                                                "esta Licencia, cada Colaborador por la presente le otorga un perpetuo,\n" +
                                                "mundial, no exclusivo, sin cargo, libre de regalías, irrevocable\n" +
                                                "(excepto como se indica en esta sección) licencia de patente para hacer, haber hecho,\n" +
                                                "usar, ofrecer vender, vender, importar y transferir el Trabajo,\n" +
                                                "donde dicha licencia se aplica solo a aquellas solicitudes de patente con licencia\n" +
                                                "por dicho Colaborador que necesariamente son infringidos por su\n" +
                                                "Contribuciones solo o en combinación de sus Contribuciones\n" +
                                                "con el trabajo al que se enviaron tales contribuciones. Si tu\n" +
                                                "instituir litigios de patentes contra cualquier entidad (incluida una\n" +
                                                "reclamación cruzada o contrademanda en una demanda) alegando que el Trabajo\n" +
                                                "o una Contribución incorporada dentro del Trabajo constituye directa\n" +
                                                "o infracción de patente contributiva, entonces cualquier licencia de patente\n" +
                                                "otorgado a Usted bajo esta Licencia para que el Trabajo terminará\n" +
                                                "a partir de la fecha en que se presenta dicho litigio.\n" +
                                                "\n" +
                                                "4. Redistribución. Puede reproducir y distribuir copias de\n" +
                                                "Trabajo o trabajos derivados del mismo en cualquier medio, con o sin\n" +
                                                "modificaciones, y en forma de Fuente u Objeto, siempre que Usted\n" +
                                                "cumplir las siguientes condiciones:\n" +
                                                "\n" +
                                                "(a) Debe entregar a cualquier otro destinatario del Trabajo o\n" +
                                                "Derivado trabaja una copia de esta licencia; y\n" +
                                                "\n" +
                                                "(b) Debe hacer que los archivos modificados lleven avisos destacados\n" +
                                                "declarando que ha cambiado los archivos; y\n" +
                                                "\n" +
                                                "(c) Debe retener, en la forma Fuente de cualquier Obra Derivada\n" +
                                                "que usted distribuye, todos los derechos de autor, patentes, marcas registradas y\n" +
                                                "avisos de atribución de la forma Fuente del Trabajo,\n" +
                                                "excluyendo aquellos avisos que no pertenecen a ninguna parte de\n" +
                                                "las obras derivadas; y\n" +
                                                "\n" +
                                                "(d) Si el Trabajo incluye un archivo de texto \"AVISO\" como parte de su\n" +
                                                "distribución, entonces cualquier obra derivada que distribuya debe\n" +
                                                "incluir una copia legible de los avisos de atribución contenidos\n" +
                                                "dentro de dicho archivo de AVISO, excluyendo aquellos avisos que no\n" +
                                                "pertenecer a cualquier parte de las Obras Derivadas, en al menos una\n" +
                                                "de los siguientes lugares: dentro de un archivo de texto AVISO distribuido\n" +
                                                "como parte de los trabajos derivados; dentro del formulario de origen o\n" +
                                                "documentación, si se proporciona junto con los Trabajos derivados; o,\n" +
                                                "dentro de una pantalla generada por los trabajos derivados, si y\n" +
                                                "donde quiera que aparezcan dichos avisos de terceros. Los contenidos\n" +
                                                "del archivo AVISO son solo para fines informativos y\n" +
                                                "No modifique la licencia. Puede agregar su propia atribución\n" +
                                                "avisos dentro de obras derivadas que distribuye, junto con\n" +
                                                "o como una adición al texto de AVISO del Trabajo, siempre que\n" +
                                                "que dichos avisos de atribución adicionales no pueden interpretarse\n" +
                                                "como modificar la licencia.\n" +
                                                "\n" +
                                                "Puede agregar su propia declaración de derechos de autor a sus modificaciones y\n" +
                                                "puede proporcionar términos y condiciones de licencia adicionales o diferentes\n" +
                                                "para uso, reproducción o distribución de sus modificaciones, o\n" +
                                                "para tales trabajos derivados en su conjunto, siempre que su uso,\n" +
                                                "la reproducción y distribución de la Obra cumple con\n" +
                                                "las condiciones establecidas en esta Licencia.\n" +
                                                "\n" +
                                                "5. Presentación de contribuciones. A menos que usted indique explícitamente lo contrario,\n" +
                                                "cualquier Contribución enviada intencionalmente para su inclusión en el Trabajo\n" +
                                                "por Usted al Licenciante estará bajo los términos y condiciones de\n" +
                                                "esta Licencia, sin términos ni condiciones adicionales.\n" +
                                                "Sin perjuicio de lo anterior, nada de lo aquí contenido sustituirá o modificará\n" +
                                                "los términos de cualquier acuerdo de licencia por separado que pueda haber ejecutado\n" +
                                                "con el Licenciante con respecto a tales Contribuciones.\n" +
                                                "\n" +
                                                "6. Marcas registradas. Esta licencia no otorga permiso para usar el comercio\n" +
                                                "nombres, marcas comerciales, marcas de servicio o nombres de productos del Licenciante,\n" +
                                                "excepto según sea necesario para un uso razonable y habitual al describir el\n" +
                                                "origen del Trabajo y reproducción del contenido del archivo AVISO.\n" +
                                                "\n" +
                                                "7. Descargo de responsabilidad de la garantía. A menos que lo exija la ley aplicable o\n" +
                                                "acordado por escrito, el Licenciante proporciona el Trabajo (y cada\n" +
                                                "El Colaborador proporciona sus Contribuciones) \"TAL CUAL\",\n" +
                                                "SIN GARANTÍAS O CONDICIONES DE NINGÚN TIPO, ya sea expreso o\n" +
                                                "implícito, incluidas, entre otras, garantías o condiciones\n" +
                                                "de TÍTULO, NO INFRACCIÓN, COMERCIABILIDAD O APTITUD PARA UN\n" +
                                                "PROPÓSITO PARTICULAR. Usted es el único responsable de determinar el\n" +
                                                "conveniencia de usar o redistribuir el Trabajo y asumir cualquier\n" +
                                                "riesgos asociados con su ejercicio de permisos bajo esta Licencia.\n" +
                                                "\n" +
                                                "8. Limitación de responsabilidad. En ningún caso y bajo ninguna teoría legal,\n" +
                                                "ya sea en agravio (incluyendo negligencia), contrato o de otra manera,\n" +
                                                "a menos que lo exija la ley aplicable (como deliberada y groseramente\n" +
                                                "actos negligentes) o acordado por escrito, cualquier contribuyente será\n" +
                                                "responsable ante usted por daños, incluidos los directos, indirectos, especiales,\n" +
                                                "daños incidentales o consecuentes de cualquier carácter que surjan como\n" +
                                                "resultado de esta Licencia o fuera del uso o incapacidad para usar el\n" +
                                                "Trabajo (incluidos, entre otros, daños por pérdida de buena voluntad,\n" +
                                                "paro laboral, falla de la computadora o mal funcionamiento, o cualquiera\n" +
                                                "otros daños o pérdidas comerciales), incluso si dicho Colaborador\n" +
                                                "ha sido advertido de la posibilidad de tales daños.\n" +
                                                "\n" +
                                                "9. Aceptación de la garantía o responsabilidad adicional. Mientras se redistribuye\n" +
                                                "el trabajo o los trabajos derivados del mismo, puede optar por ofrecer,\n" +
                                                "y cobrar una tarifa por la aceptación del soporte, la garantía, la indemnización,\n" +
                                                "u otras obligaciones de responsabilidad y / o derechos consistentes con esto\n" +
                                                "Licencia. Sin embargo, al aceptar tales obligaciones, solo puede actuar\n" +
                                                "en su propio nombre y bajo su exclusiva responsabilidad, no en nombre de\n" +
                                                "de cualquier otro colaborador, y solo si acepta indemnizar,\n" +
                                                "defender y eximir de responsabilidad a cada colaborador\n" +
                                                "incurrido por, o reclamaciones afirmadas en contra, de dicho Colaborador por razón\n" +
                                                "de su aceptación de dicha garantía o responsabilidad adicional.\n" +
                                                "\n" +
                                                "FIN DE TÉRMINOS Y CONDICIONES\n" +
                                                "\n" +
                                                "Copyright 2019 jhonatan fernandez muñoz \n" +
                                                "\n" +
                                                "Licenciado bajo la Licencia Apache, Versión 2.0 (la \"Licencia\");\n" +
                                                "no puede usar este archivo excepto en cumplimiento con la Licencia.\n" +
                                                "Puede obtener una copia de la Licencia en\n" +
                                                "\n" +
                                                "http://www.apache.org/licenses/LICENSE-2.0\n" +
                                                "\n" +
                                                "A menos que lo exija la ley aplicable o se acuerde por escrito, el software\n" +
                                                "distribuido bajo la Licencia se distribuye \"TAL CUAL\",\n" +
                                                "SIN GARANTÍAS O CONDICIONES DE NINGÚN TIPO, ya sea expresa o implícita.\n" +
                                                "Consulte la Licencia para ver los permisos de idioma específicos y\n" +
                                                "limitaciones bajo la Licencia." );


                                        builder.setPositiveButton( "ACEPTAR", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialogInterface, int i) {

                                            }
                                        } );
                                        builder.create().show();
                                    }
                                } );
                                builder.setPositiveButton( "ACEPTAR", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {

                                    }
                                } );
                                builder.create().show();
                            }
                        } );

                        builder.setView( help );
                        builder.create().show();


                    }
                } );

                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }
    public void filtro(View V){
        llenarSpinners();
    }
    public void llenarSpinners() {

        String url = "http://" + cambiarIP.ip + "/validar/cantidadfiltre.php?op="+resuldato3.getText().toString();
        client.post(url, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                if (statusCode == 200) {
                    cargarSpinner(new String(responseBody));
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

            }
        });
    }


    public void llenarSpinner() {

        String url = "http://" + cambiarIP.ip + "/validar/cantidad.php";
        client.post(url, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                if (statusCode == 200) {
                    cargarSpinner(new String(responseBody));
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

            }
        });
    }


    public void cargarSpinner(String cargarSpinner) {
        ArrayList<cantidades> dato = new ArrayList<cantidades>();
        try {
            JSONArray objecto = new JSONArray(cargarSpinner);
            for (int i = 0; i < objecto.length(); i++) {
                cantidades a = new cantidades();
                a.setTarea(objecto.getJSONObject(i).getString("tarea"));
                dato.add(a);
            }
            ArrayAdapter<cantidades> a = new ArrayAdapter<cantidades>(this, android.R.layout.simple_dropdown_item_1line, dato);
            resuldato.setAdapter(a);

        } catch (Exception e) {
            e.printStackTrace();

        }

    }

    public void datos() {
        String url = "http://" + cambiarIP.ip + "/validar/motivo.php";
        clientes.post(url, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                if (statusCode == 200) {
                    listoSpinner(new String(responseBody));
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

            }
        });
    }

    public void listoSpinner(String listoSpinner) {
        ArrayList<motivoparo> dato2 = new ArrayList<motivoparo>();
        try {
            JSONArray objecto = new JSONArray(listoSpinner);
            for (int i = 0; i < objecto.length(); i++) {
                motivoparo b = new motivoparo();
                b.setParo(objecto.getJSONObject(i).getString("paro"));
                dato2.add(b);
            }
            ArrayAdapter<motivoparo> a = new ArrayAdapter<motivoparo>(this, android.R.layout.simple_dropdown_item_1line, dato2);
            resuldato2.setAdapter(a);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }



    public void llenarSpinner3() {
        String url = "http://" + cambiarIP.ip + "/validar/motivocantidad.php";
        clientes3.post(url, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                if (statusCode == 200) {
                    cargar3Spinner(new String(responseBody));
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

            }
        });
    }

    public void cargar3Spinner(String cargar3Spinner) {
        ArrayList<cantidadfallas> dato4 = new ArrayList<cantidadfallas>();
        try {
            JSONArray objecto = new JSONArray(cargar3Spinner);
            for (int i = 0; i < objecto.length(); i++) {
                cantidadfallas c = new cantidadfallas();
                c.setFallas(objecto.getJSONObject(i).getString("fallas"));
                dato4.add(c);
            }
            ArrayAdapter<cantidadfallas> a = new ArrayAdapter<cantidadfallas>(this, android.R.layout.simple_dropdown_item_1line, dato4);
            resuldato4.setAdapter(a);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    public void operador(View v) {

        if (id.getText().toString() == null) {

            id.setError("ID ES REQUERIDO !");

        }if (id.getText().toString() != null) {

            final String nombretarea = resuldato.getSelectedItem().toString();

            final String Nop = resuldato3.getText().toString();

            new Thread(new Runnable() {
                @Override
                public void run() {

                    try {
                        String response = HttpRequest.get( "http://" + cambiarIP.ip + "/validar/operador.php?id=" + id.getText().toString() ).body();

                        JSONArray objecto = new JSONArray( response );

                        if (objecto.length() != 0) {

                            runOnUiThread( new Runnable() {
                                @Override
                                public void run() {
                                    desbloquear.setEnabled( true );
                                    registroTIME.setEnabled( true );
                                    salidaTIME.setEnabled( true );
                                    cantidadund.setEnabled( true );
                                }
                            } );

                            resultados.setText( objecto.getString( 0 ).toString() );


                            String rest = HttpRequest.get( "http://" + cambiarIP.ip + "/validar/validarcantidad.php?numero="+ Nop.toString()  ).body();
                            try {
                                JSONArray RESTARCANTIDAD = new JSONArray(rest);

                                String acz = HttpRequest.get( "http://" + cambiarIP.ip + "/validar/cantidadpendiente.php?id=" + nombretarea.toString()).body();

                                JSONArray tareita = new JSONArray(acz);

                                datoverifica = Integer.parseInt( tareita.getString( 0 ) );

                                totalcan.setText( "CANTIDAD OP : " + RESTARCANTIDAD.getString( 0 ) + " CANTIDAD PENDIENTE : " +tareita.getString( 0 ) );

                                MOSTRAROP ops =new MOSTRAROP();
                                ops.start();
                                if(datoverifica == 0){
                                    restaurarACAN();

                                }



                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                       }


                        } catch (Exception e) {
                        // TODO: handle exception

                    }
                }


            }).start();
            Thread.interrupted();


                    }
                    else{
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(getApplicationContext(),"EL CODIGO DEL USUARIO NO EXISTE", Toast.LENGTH_SHORT).show();
                                }
                            });
                        }

    }
    public void restaurarACAN(){

        final String nombretarea = resuldato.getSelectedItem().toString();

        final String Nop = resuldato3.getText().toString();
        new Thread( new Runnable() {
            @Override
            public void run() {
                String responsesx = HttpRequest.get( "http://" + cambiarIP.ip + "/validar/validarcantidad.php?numero="+ Nop.toString()  ).body();

                try {
                    JSONArray RESTARCANTIDADES = new JSONArray(responsesx);
                    int datico = Integer.parseInt(RESTARCANTIDADES.getString(0));

                    String asd = HttpRequest.get("http://"+cambiarIP.ip+"/validar/actualizartarea.php?tarea="+nombretarea.toString()+"&canpen="+datico).body();


                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        } ).start();
    }
    class MOSTRAROP extends Thread{
        public void run() {
            final String nombretarea = resuldato.getSelectedItem().toString();

            final String Nop = resuldato3.getText().toString();

            MOP =  new Thread(new Runnable() {
                @Override
                public void run() {


                        try {
                            Thread.sleep( 500 );

                            String response = HttpRequest.get( "http://" + cambiarIP.ip + "/validar/validarcantidad.php?numero="+ Nop.toString()  ).body();


                            try {
                                JSONArray RESTARCANTIDAD = new JSONArray(response);
                                String acz = HttpRequest.get( "http://" + cambiarIP.ip + "/validar/cantidadpendiente.php?id=" + nombretarea.toString()).body();

                                JSONArray tareita = new JSONArray(acz);
                                datoverifica = Integer.parseInt(tareita.getString( 0 ));

                                totalcan.setText("CANTIDAD OP : "+RESTARCANTIDAD.getString(0)+" CANTIDAD PENDIENTE : "+tareita.getString(0));



                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }


                }
            });
            MOP.start();
        }
    }






  public void verificar(){

      final String nombretarea = resuldato.getSelectedItem().toString();
            eliminaOK = new Thread(new Runnable() {
                @Override
                public void run() {
                     String cero = HttpRequest.get("http://"+cambiarIP.ip+"/validar/eliminarcanok.php?id=" + nombretarea.toString()).body();

                            try {
                                JSONArray nada = new JSONArray(cero);
                                int vacio = Integer.parseInt(nada.getString(0));
                                if(vacio != 0){


                                runOnUiThread( new Runnable() {
                                    @Override
                                    public void run() {
                                        ((TextView) resuldato.getSelectedView()).setTextColor(Color.BLACK);

                                    }
                                } );


                                }
                                else if(vacio == 0){

                                    runOnUiThread( new Runnable() {
                                        @Override
                                        public void run() {
                                            desbloquear.setEnabled(false);
                                            registroTIME.setEnabled(false);
                                            salidaTIME.setEnabled(false);
                                            cantidadund.setEnabled(false);
                                            ((TextView) resuldato.getSelectedView()).setTextColor(Color.RED);


                                            AlertDialog.Builder builder = new AlertDialog.Builder( OperadorActivity.this );
                                            builder.setTitle( "LA OP FINALIZO " );
                                            builder.setMessage( "DEBE SELECCIONAR OTRA OP " );

                                            builder.setPositiveButton( "ACEPTAR", new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialogInterface, int i) {

                                                }
                                            } );
                                            builder.setNegativeButton( "REGISTRAR OTRA OP", new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialogInterface, int i) {
                                                       new Thread( new Runnable() {
                                                           @Override
                                                           public void run() {

                                                             String cero = HttpRequest.get("http://"+cambiarIP.ip+"/validar/nuevoRegistro.php?ID="+ id.getText().toString() ).body();

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
            });
            eliminaOK.start();
            eliminaOK.interrupted();

  }

    public void hora(View v) {




         registros = new AlertDialog.Builder(OperadorActivity.this);
        tiempo1 = getLayoutInflater().inflate(R.layout.dialog_spinner,null);

        datos();
        id=(EditText)findViewById(R.id.operador);

        paro = (EditText)tiempo1.findViewById(R.id.paro);
        MOSTRAR = (TextView) tiempo1.findViewById(R.id.MOSTRAR);
       texto = (TextView) tiempo1.findViewById(R.id.textos);
        stop = (Button)tiempo1.findViewById(R.id.stop);
        go = (Button)tiempo1.findViewById(R.id.go);
        validarinfo = (Button)tiempo1.findViewById(R.id.validarinfo);



        motivo = (TextView) tiempo1.findViewById(R.id.MOTIVO);
        resuldato2 = (Spinner)tiempo1.findViewById(R.id.spinner2);


        //botones

        MOSTRAR.setVisibility(View.INVISIBLE);
        paro.setVisibility(View.INVISIBLE);
        stop.setVisibility(View.INVISIBLE);
        go.setVisibility(View.INVISIBLE);
        texto.setVisibility(View.INVISIBLE);
        resuldato2.setVisibility(View.INVISIBLE);


        //TEXTVIEW Y SPINNER
        motivo.setVisibility(View.VISIBLE);
        resuldato2.setVisibility(View.VISIBLE);

        validarinfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                validarinfo.setVisibility(View.INVISIBLE);
                resuldato2.setClickable(false);
                resuldato2.setEnabled(false);



                MOSTRAR.setVisibility(View.VISIBLE);
                paro.setVisibility(View.VISIBLE);
                stop.setVisibility(View.VISIBLE);
                go.setVisibility(View.VISIBLE);
                texto.setVisibility(View.VISIBLE);
                resuldato2.setVisibility(View.VISIBLE);


                 runOnUiThread( new Runnable() {
                     @Override
                     public void run() {
                         String mostrardatos = resuldato2.getSelectedItem().toString();
                         Toast.makeText(getApplicationContext(),"USTED SELECCIÓNO "+mostrardatos, Toast.LENGTH_SHORT).show();
                     }
                 } );

            }
        });


        registros.setPositiveButton("FINALIZAR", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Toast.makeText(getApplicationContext(),"LOS DATOS FUERON GUARDADOS CORRECTAMENTE", Toast.LENGTH_SHORT).show();
            }
        });


        registros.setNegativeButton("REGISTRAR", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {


                        Toast.makeText(getApplicationContext(),"DATOS VERIFICADOS", Toast.LENGTH_SHORT).show();


            }
        });
        registros.setNeutralButton("REGISTRAR OTRO MOTIVO DE PARO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });



        stop.setEnabled(false);

        registros.setView(tiempo1);
        registros.create();
      AlertDialog alert = registros.create();
        alert.show();

        alert.setCanceledOnTouchOutside(false);

        desbloquear = alert.getButton(AlertDialog.BUTTON_NEGATIVE);
        neutrar = alert.getButton(AlertDialog.BUTTON_NEUTRAL);
        positivo = alert.getButton(AlertDialog.BUTTON_POSITIVE);

        neutrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                positivo.setEnabled(false);
                neutrar.setEnabled(false);
                desbloquear.setEnabled(false);
                go.setEnabled(true);

                MOSTRAR.setVisibility(View.INVISIBLE);
                paro.setVisibility(View.INVISIBLE);
                stop.setVisibility(View.INVISIBLE);
                go.setVisibility(View.INVISIBLE);
                texto.setVisibility(View.INVISIBLE);

                
                validarinfo.setVisibility(View.VISIBLE);
                motivo.setVisibility(View.VISIBLE);
                resuldato2.setClickable(true);
                resuldato2.setEnabled(true);
                resuldato2.setVisibility(View.VISIBLE);

                MOSTRAR.setText("0:0:0");
            }
        });


        desbloquear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                desbloquear.setEnabled(false);
                neutrar.setEnabled(true);
                positivo.setEnabled(true);

                final String prueba = resuldato2.getSelectedItem().toString();


                final String Nop = resuldato3.getText().toString();

                // imprime fecha
                dateFormat = new SimpleDateFormat("yyyy/MM/dd", Locale.getDefault());
                date = new Date();

                //imprime hora
                hourFormat = new SimpleDateFormat("HH:mm", Locale.getDefault());

                final String horas = hourFormat.format(date);
                final String fechas = dateFormat.format(date);

                new Thread(new Runnable() {

                    @Override
                    public void run() {

                            String response = HttpRequest.get("http://"+cambiarIP.ip+"/validar/RegistrarMotivo.php?op="+Nop.toString()+"&id="+id.getText().toString()+"&paro="+paro.getText().toString()+"&motivo="+prueba.toString()+"&fecha="+fechas+"&hora="+horas.toString()).body();

                    }
                }).start();
                Thread.interrupted();
                minuto=0;
                hora=0;

            }
        });

        positivo.setEnabled(false);
        neutrar.setEnabled(false);
        desbloquear.setEnabled(false);
    }


    public void go (View v)  {

        go.setEnabled(false);

        hilo = new Thread(new Runnable() {

            @Override
            public void run() {

                try {

                    i=0;
                    minuto =0;
                    hora=0;

                    for ( i= 0; i <= 60; i++){
                        System.out.println(i);

                        Thread.sleep(1000);

                        if (i==60){
                            i=0;
                            minuto++;

                        }

                        if (minuto == 59){
                            minuto =0;
                            hora++;
                        }

                        if(hora==12){
                            hora=0;

                        }

                        final  String segundo = Integer.toString(i);
                        final  String minutos = Integer.toString(minuto);
                        final String horas = Integer.toString(hora);

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {

                                MOSTRAR.setText(horas+":"+minuto+":"+segundo);

                            }
                        });

                    }

                }
                catch (Exception e) {
                    // TODO: handle exception
                    e.printStackTrace();


                }


            }



        });
        hilo.start();

        if(hilo!=null){
            stop.setEnabled(true);


        }

    }


    public void  stop (View v) {
        stop.setEnabled(false);
        go.setEnabled(false);
        desbloquear.setEnabled(true);
        if(paro!=null){

            motivo.setVisibility(View.VISIBLE);
            resuldato2.setVisibility(View.VISIBLE);
            hilo.interrupt();

        }

       String d = Integer.toString(minuto);
       paro.setText(d);

    }

    public void registrar (View v) {

        id = (EditText)findViewById(R.id.operador);

        // imprime fecha
         dateFormat = new SimpleDateFormat("yyyy/MM/dd", Locale.getDefault());
         date = new Date();

        //imprime hora
         hourFormat = new SimpleDateFormat("HH:mm", Locale.getDefault());

        //almacena los datos en una cadena
        final	String hora = hourFormat.format(date);

        final	String fecha = dateFormat.format(date);

        AlertDialog.Builder builder = new AlertDialog.Builder(OperadorActivity.this);
        builder.setTitle("DATOS INSERTADOS");
        builder.setMessage( "PORFAVOR EMPIECE SU LABOR" );
         edit = new EditText(this);
        edit.setEnabled(false);
        edit.setText(fecha);

        final   String fechas =edit.getText().toString();
        final String Nop = resuldato3.getText().toString();
        builder.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                // TODO Auto-generated method stub
                new Thread(new Runnable() {
                     @Override
                    public void run() {

                        String response = HttpRequest.get("http://"+cambiarIP.ip+"/validar/actualizaEntrada.php?id="+id.getText().toString()+"&Finicial="+fechas+"&Hinicial="+hora.toString()+"&op="+Nop.toString()).body();

                         runOnUiThread(new Runnable() {
                             @Override
                             public void run() {
                                 registroTIME.setEnabled(false);
                             }
                         });



                    }
                }).start();
                Thread.interrupted();
            }
        });

        builder.create().show();

    }

    public void salida (View v) {

        llenarSpinner3();


        id=(EditText)findViewById(R.id.operador);
        View view = getLayoutInflater().inflate(R.layout.cantidad_produccidas,null);
        resuldato4 =(Spinner)view.findViewById(R.id.spinner2);
        fallas = (EditText)view.findViewById(R.id.fallas);
        cantidad = (EditText)view.findViewById(R.id.digicantidad);

         final String op = resuldato3.getText().toString(); //**
         final String tarea = resuldato.getSelectedItem().toString(); //**

        // imprime fecha
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd", Locale.getDefault());
        Date date = new Date();

        //imprime hora
        SimpleDateFormat hourFormat = new SimpleDateFormat("HH:mm", Locale.getDefault());

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

            volumen = Integer.parseInt(cantidad.getText().toString());
             falla = fallas.getText().toString();
            error = resuldato4.getSelectedItem().toString();

                final String Nop = resuldato3.getText().toString();
                final String nombretarea = resuldato.getSelectedItem().toString();



                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        String cantiok = HttpRequest.get("http://"+cambiarIP.ip+"/validar/consulcantidad.php?numero="+id.getText().toString()).body();

                        try {

                            JSONArray objecto = new JSONArray(cantiok);


                            final int cantidadreg = Integer.parseInt(objecto.getString(0));
                             volumen = Integer.parseInt(cantidad.getText().toString());


                            total = cantidadreg + volumen;


                            if(cantidadreg == 0){

                                String response = HttpRequest.get("http://"+cambiarIP.ip+"/validar/actualizaSalida.php?id="+id.getText().toString()+"&Ffinal="+fechas+"&Hfinal="+horas+"&cantidad="+volumen+"&fallas="+falla.toString()+"&cantidaderror="+error.toString()+"&tarea="+tarea.toString()+"&op="+op.toString()).body();

                                new Thread( new Runnable() {
                                    @Override
                                    public void run() {
                                        try {
                                            Thread.sleep( 10000 );
                                            String response = HttpRequest.get("http://"+cambiarIP.ip+"/validar/actualizaSalida.php?id="+id.getText().toString()+"&Ffinal="+fechas+"&Hfinal="+horas+"&cantidad="+volumen+"&fallas="+falla.toString()+"&cantidaderror="+error.toString()+"&tarea="+tarea.toString()+"&op="+op.toString()).body();

                                        } catch (InterruptedException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                } ).start();
                            }
                            else if(cantidadreg > 0){

                                String response = HttpRequest.get("http://"+cambiarIP.ip+"/validar/actualizaSalida.php?id="+id.getText().toString()+"&Ffinal="+fechas+"&Hfinal="+horas+"&cantidad="+total+"&fallas="+falla.toString()+"&cantidaderror="+error.toString()+"&tarea="+tarea.toString()+"&op="+op.toString()).body();

                                new Thread( new Runnable() {
                                    @Override
                                    public void run() {
                                        try {
                                            Thread.sleep( 10000 );
                                            String response = HttpRequest.get("http://"+cambiarIP.ip+"/validar/actualizaSalida.php?id="+id.getText().toString()+"&Ffinal="+fechas+"&Hfinal="+horas+"&cantidad="+total+"&fallas="+falla.toString()+"&cantidaderror="+error.toString()+"&tarea="+tarea.toString()+"&op="+op.toString()).body();

                                        } catch (InterruptedException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                } ).start();

                            }



                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                        String response = HttpRequest.get("http://"+cambiarIP.ip+"/validar/cantidadedits.php?numero="+nombretarea.toString()).body();

                        try {
                            JSONArray RESTARCANTIDAD = new JSONArray(response);

                            volumencan = Integer.parseInt(cantidad.getText().toString());
                            cantidadpro = Integer.parseInt(RESTARCANTIDAD.getString(0));

                            int totalade = cantidadpro - volumencan;

                            if(totalade >= 0){
                                String responses = HttpRequest.get("http://"+cambiarIP.ip+"/validar/cantidadmodifi.php?numero="+nombretarea.toString()+"&totales="+totalade).body();

                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {

                                        Toast.makeText(getApplicationContext(),"DATOS VERIFICADOS", Toast.LENGTH_SHORT).show();
                                        MOSTRAROP ops =new MOSTRAROP();
                                        ops.start();
                                        verificar();
                                    }
                                });

                            }else if (totalade < 0){

                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {

                                        AlertDialog.Builder builder = new AlertDialog.Builder(OperadorActivity.this);
                                        builder.setTitle("EXEDIO LA CANTIDAD DE PRODUCCION AUTORIZADA");
                                        builder.setMessage("usted exedio la cantidad permitida por la op ");
                                        builder.setNegativeButton("VOLVER AL REGISTRO", new DialogInterface.OnClickListener() {
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
         tex = (TextView)adelanto.findViewById(R.id.textos);
         grupo = (RadioGroup)adelanto.findViewById(R.id.GRUPORADIO);
         btnconfir = (Button)adelanto.findViewById(R.id.CONFIRMARADE);
         btnvalidar = (Button)adelanto.findViewById(R.id.VALIDARADE);
         botonSi = (RadioButton)adelanto.findViewById(R.id.SI);
        botonNo = (RadioButton)adelanto.findViewById(R.id.NO);
        digito = (EditText)adelanto.findViewById(R.id.digicantidad);

        digito.setVisibility(adelanto.INVISIBLE);
        btnconfir.setVisibility(adelanto.INVISIBLE);

        btnvalidar.setVisibility(adelanto.VISIBLE);
        grupo.setVisibility(adelanto.VISIBLE);
        botonSi.setVisibility(adelanto.VISIBLE);
        botonNo.setVisibility(adelanto.VISIBLE);




        btnvalidar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if(grupo.getCheckedRadioButtonId() == -1){



                    Toast.makeText(getApplicationContext(),"NO SE PUEDE CONTINUAR DEBE SELECCIONAR UNA OPCION", Toast.LENGTH_SHORT).show();
                }
                else if(botonSi.isChecked()){
                    Toast.makeText(getApplicationContext(),"SI DESEO ADELANTAR LO PRODUCCIDO", Toast.LENGTH_SHORT).show();

                    digito.setVisibility(adelanto.VISIBLE);
                    btnconfir.setVisibility(adelanto.VISIBLE);

                    btnvalidar.setVisibility(adelanto.INVISIBLE);
                    grupo.setVisibility(adelanto.INVISIBLE);
                    botonSi.setVisibility(adelanto.INVISIBLE);
                    botonNo.setVisibility(adelanto.INVISIBLE);
                    tex.setVisibility(adelanto.INVISIBLE);

                }
                else if(botonNo.isChecked()){

                    Toast.makeText(getApplicationContext(),"CUANTAS CANTIDADES DESEAS ADELANTAR ", Toast.LENGTH_SHORT).show();

                    digito.setVisibility(adelanto.INVISIBLE);
                }
            }
        });

        btnconfir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                // imprime fecha
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd", Locale.getDefault());
                Date date = new Date();

                //imprime hora
                SimpleDateFormat hourFormat = new SimpleDateFormat("HH:mm", Locale.getDefault());

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
                        final String Nop = resuldato3.getText().toString();
                        volumencan = Integer.parseInt(digito.getText().toString());
                       String response = HttpRequest.get("http://"+cambiarIP.ip+"/validar/actualizarcantidad.php?numero="+Nop.toString()+"&id="+id.getText().toString()+"&canpen="+volumencan+"&Ffinal="+fechas+"&Hfinal="+horas+"&tarea="+tarea.toString()).body();


                        final String nombretarea = resuldato.getSelectedItem().toString();
                        try {
                            Thread.sleep(1000);


                            String responses = HttpRequest.get("http://"+cambiarIP.ip+"/validar/cantidadedits.php?numero="+nombretarea.toString()).body();

                            try {
                                JSONArray RESTARCANTIDAD = new JSONArray(responses);

                                volumencan = Integer.parseInt(digito.getText().toString());
                                cantidadpro = Integer.parseInt(RESTARCANTIDAD.getString(0));

                                int totalade = cantidadpro - volumencan;

                                System.out.println("LAS CANTIDADES SON : "+totalade);

                                if(totalade >= 0){
                                    String responsesx = HttpRequest.get("http://"+cambiarIP.ip+"/validar/cantidadmodifi.php?numero="+nombretarea.toString()+"&totales="+totalade).body();

                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            Toast.makeText(getApplicationContext(),"SE REGISTRO EL ADELANTO PRODUCCIDO ", Toast.LENGTH_SHORT).show();
                                            MOSTRAROP ops =new MOSTRAROP();
                                            ops.start();
                                            verificar();
                                        }
                                    });
                                }
                                else if (totalade < 0){

                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            try {
                                                Thread.sleep(500);
                                                AlertDialog.Builder builder = new AlertDialog.Builder(OperadorActivity.this);
                                                builder.setTitle("EXEDIO LA CANTIDAD DE PRODUCCION AUTORIZADA");
                                                builder.setMessage("usted exedio la cantidad permitida por la op ");
                                                builder.setNegativeButton("VOLVER AL REGISTRO", new DialogInterface.OnClickListener() {
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
        });
        aplazarproduccion.setPositiveButton("CANCELAR", new DialogInterface.OnClickListener() {
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

