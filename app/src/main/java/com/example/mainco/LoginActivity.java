package com.example.mainco;



import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONArray;


public class LoginActivity extends AppCompatActivity {

    EditText login,pass;
    Button validar,registre;
    JSONArray objecto;
    TSSManager ttsManager=null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        ttsManager=new TSSManager();
        ttsManager.init(this);


      login = (EditText)findViewById(R.id.estado);
         pass = (EditText)findViewById(R.id.ID);

        registre = (Button)findViewById(R.id.registre);
        validar = (Button)findViewById(R.id.login);



    }
    public void onBackPressed() {

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


            String response = HttpRequest.get("http://"+cambiarIP.ip+"/validar/validar.php?cedula="+login.getText().toString()+"&pass="+pass.getText().toString()).body();

            try {
                JSONArray objecto = new JSONArray(response);

                if(objecto.length()>0) {


                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Intent e = new Intent(getApplicationContext(), OperadorActivity.class);
                            startActivity(e);
                            ttsManager.initQueue("BIENVENIDO");
                            Toast.makeText(getApplicationContext(),"SESIÒN INICIADA", Toast.LENGTH_SHORT).show();
                        }
                    });

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
                // TODO: handle exception
                e.printStackTrace();
            }
                    }
               }).start();

        }
}


    public void registro (View v){
        Intent e = new Intent(getApplicationContext(), RegistroActivity.class);
        startActivity(e);


    }

    public void olvidoC (View v){
        Intent e = new Intent(getApplicationContext(), OlvidoActivity.class);
        startActivity(e);
    }

}
