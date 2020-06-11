package com.example.App;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private int progressStatus = 0;
    private ProgressBar pb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        
        pb = findViewById(R.id.progressBar);

        new MyTask().execute();

    }
    class MyTask extends AsyncTask<String,Integer,String>{

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

}
