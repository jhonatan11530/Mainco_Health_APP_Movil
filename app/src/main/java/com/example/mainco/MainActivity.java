package com.example.mainco;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;


public class MainActivity extends AppCompatActivity {

    private int progressStatus = 0;
    private ProgressBar pb;
    private int splash = 100;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_main);
        pb = (ProgressBar) findViewById(R.id.progressBar);

        splash();
        new MyTask().execute();

    }
    class MyTask extends AsyncTask<String,Integer,String>{

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate( values );
            pb.setProgress(values[0]);
        }


        protected void onPostExecute() {


            guardar();
                Intent e = new Intent(getApplicationContext(), Modulos.class);
                startActivity(e);
        }

        @Override
        protected String doInBackground(String... strings) {

            while (progressStatus <= 100){
                try {
                    Thread.sleep( 5 );

                    progressStatus +=1;


                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                publishProgress( progressStatus );

                if (progressStatus == 100){
                    onPostExecute();
                }
            }



            return "complete";
        }


    }

    public void guardar(){

        SharedPreferences preferences = getSharedPreferences("ARCHIVO_LOGIN", Context.MODE_PRIVATE );
        SharedPreferences shared = getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor usu = shared.edit();
        usu.putString("Splash", String.valueOf(splash));
        usu.commit();

    }
    public void splash(){

        SharedPreferences mostrardato = getPreferences( Context.MODE_PRIVATE );
        final String user = mostrardato.getString( "Splash","" );

        new Thread( new Runnable() {
            @Override
            public void run() {
                if(user == "100"){
                    Intent e = new Intent(getApplicationContext(), Modulos.class);
                    startActivity(e);
                }

            }
        } ).start();
    }
}
