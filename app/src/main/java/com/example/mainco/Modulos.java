package com.example.mainco;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class Modulos extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_modulos );
    }
    public void Produccion(View view){
        Intent e = new Intent(getApplicationContext(), LoginActivity.class);
        startActivity(e);
    }
    public void Logistica(View view){
        Intent e = new Intent(getApplicationContext(), ScanActivity.class);
        startActivity(e);
    }
}
