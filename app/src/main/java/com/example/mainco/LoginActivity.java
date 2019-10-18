package com.example.mainco;


import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONArray;

import java.util.ArrayList;


public class LoginActivity extends AppCompatActivity {

    EditText login, pass;
    Button validar, registre;
    TSSManager ttsManager = null;
    ArrayList com;
    final String user ="";
    private ListView componentes;
    CheckBox GUARDARUTO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        setRequestedOrientation( ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        mostrarguardado();

         GUARDARUTO = (CheckBox) findViewById(R.id.OK);

        ttsManager = new TSSManager();
        ttsManager.init( this );

      login = (EditText)findViewById(R.id.estado);
         pass = (EditText)findViewById(R.id.ID);

        registre = (Button)findViewById(R.id.registre);
        validar = (Button)findViewById(R.id.login);


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

               runOnUiThread( new Runnable() {
                   @Override
                   public void run() {
                       View help = getLayoutInflater().inflate(R.layout.ayuda,null);

                       AlertDialog.Builder builder = new AlertDialog.Builder( LoginActivity.this );
                       builder.setTitle("ASISTENCIA O INFORMACION ");
                       Button acercaapp = (Button)help.findViewById( R.id.ACERCA );
                       Button ayuda = (Button)help.findViewById( R.id.HELP);
                       Button soport = (Button)help.findViewById( R.id.SOPORTE );


                       ayuda.setOnClickListener( new View.OnClickListener() {
                           @Override
                           public void onClick(View view) {

                               View tutorial = getLayoutInflater().inflate(R.layout.tutorial,null);
                               AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
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

                               ArrayAdapter<HELPCOMP> adapdtador = new ArrayAdapter<HELPCOMP>( LoginActivity.this,android.R.layout.simple_dropdown_item_1line,comphelp );
                               componentes.setAdapter( adapdtador );

                               componentes.setOnItemClickListener( new AdapterView.OnItemClickListener() {
                                   @Override
                                   public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {

                                       HELPCOMP H =comphelp.get( position );

                                       if (H.getNombre() == "INTRODUCCION A MAINCO APP"){

                                           Intent e = new Intent(getApplicationContext(), videouno.class);
                                           startActivity(e);
                                       }

                                       else if (H.getNombre() == "COMO CREO UN USUARIO ?"){

                                           Intent e = new Intent(getApplicationContext(), videodos.class);
                                           startActivity(e);
                                       }

                                       else if (H.getNombre() == "COMO RECUPERO MI CONTRASEÑA ?"){

                                           Intent e = new Intent(getApplicationContext(), videotres.class);
                                           startActivity(e);
                                       }

                                       else if (H.getNombre() == "COMO INGRESO AL SISTEMA ?"){

                                           Intent e = new Intent(getApplicationContext(), videocuatro.class);
                                           startActivity(e);
                                       }

                                       else if (H.getNombre() == "COMO EMPIEZO A REGISTRAR ?"){

                                           Intent e = new Intent(getApplicationContext(), videocinco.class);
                                           startActivity(e);
                                       }

                                       else if (H.getNombre() == "COMO REGISTRO MIS PAUSAS ?"){

                                           Intent e = new Intent(getApplicationContext(), videoseix.class);
                                           startActivity(e);
                                       }

                                       else if (H.getNombre() == "COMO SABER SI SE CERRO LA O.P ?"){

                                           Intent e = new Intent(getApplicationContext(), videosiete.class);
                                           startActivity(e);
                                       }

                                       else if (H.getNombre() == "COMO SABER SI YA TERMINE ?"){

                                           Intent e = new Intent(getApplicationContext(), videoocho.class);
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
                               AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
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

                               AlertDialog.Builder builder = new AlertDialog.Builder( LoginActivity.this );
                               builder.setTitle( "APLICACION MAINCO HEALTH CARE" );
                               builder.setMessage( "USO : " +
                                       "La aplicacion Mainco Health care es una aplicacion de registro de informacion en el area de planta atravez de esta app se podra registrar la hora de entrada , salida , las cantidades produccidas las cantidades defectuosa , el registro de tiempo de descanso \n\n" +
                                       "EXCLUSIVIDAD : " +
                                       "La aplicacion Mainco Health care para dispositivos android es de uso exclusivo para la empresa MAINCO HEALTH CARE se prohibe el uso de esta app por personas (TERCEROS) sin previa autorizacion de MAINCO HEALTH CARE es uso no autorizado de esta app por terceros puede ser sancionado por la respectiva empresa \n\n"
                                       );

                               builder.setNegativeButton( "ACUERDO DE LICENCIA", new DialogInterface.OnClickListener() {
                                   @Override
                                   public void onClick(DialogInterface dialogInterface, int i) {

                                       AlertDialog.Builder builder = new AlertDialog.Builder( LoginActivity.this );
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

        public void validartodo(View view){
            if(pass.getText().toString().length() == 0 && login.getText().toString().length() == 0) {

                pass.setError("CONTRASEÑA ES REQUERIDO !");
                ttsManager.initQueue("los campos de usuario y contraseña deben llenarse");
                login.setError("ID ES REQUERIDO !");

            }else{

                 new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            Thread.sleep( 500 );



                        String response = HttpRequest.get("http://"+cambiarIP.ip+"/validar/validar.php?cedula="+login.getText().toString()+"&pass="+pass.getText().toString()).body();

                        try {
                            JSONArray objecto = new JSONArray(response);

                            if(objecto.length()>0) {


                                if(GUARDARUTO.isChecked()==true){
                                    runOnUiThread( new Runnable() {
                                        @Override
                                        public void run() {
                                            Toast.makeText( getApplicationContext(),"SE GUARDO EL USUARIO Y CONTRASEÑA",Toast.LENGTH_SHORT).show();

                                            guardar();
                                        }
                                    } );

                                }
                                    runOnUiThread( new Runnable() {
                                        @Override
                                        public void run() {
                                            ProgressDialog pd = new ProgressDialog(LoginActivity.this);

                                            pd.setTitle("INICIANDO SESION");

                                            pd.setMessage("Porfavor espere");
                                            pd.setCanceledOnTouchOutside(false);

                                            pd.show();

                                            startService(new Intent(LoginActivity.this, ServerConnect.class));
                                        }
                                    } );


                            }

                            else{
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                       
                                        ttsManager.initQueue("USUARIO O CONTRASEÑA INCORRECTO");
                                        Toast.makeText(getApplicationContext(),"USUARIO O CONTRASEÑA INCORRECTO", Toast.LENGTH_SHORT).show();

                                    }
                                });
                            }



                        }catch (Exception e) {
                            e.printStackTrace();
                        }
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }

                    }
                }).start();


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
