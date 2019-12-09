package com.example.mainco;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;
import android.widget.ProgressBar;
import android.widget.ThemedSpinnerAdapter;

import androidx.appcompat.app.AppCompatActivity;


public class MainActivity extends AppCompatActivity {

    private int progressStatus = 0;
    Integer count =1;
    private Handler handler = new Handler();
    private ProgressBar pb;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setRequestedOrientation( ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_main);
        pb = (ProgressBar) findViewById(R.id.progressBar);

        LoginActivity loginActivity = new LoginActivity();
        loginActivity.Obtener_Firebase();

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


                Intent e = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(e);

        }

        @Override
        protected String doInBackground(String... strings) {

            while (progressStatus <= 100){
                try {
                    Thread.sleep( 20 );

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


}
