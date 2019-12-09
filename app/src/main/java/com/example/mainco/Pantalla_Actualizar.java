package com.example.mainco;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import clases.MyReceiver;

public class Pantalla_Actualizar extends AppCompatActivity {

    MyReceiver oMyReceiver;
    Button btn_descargar,btn_install;
    String url,version;
    private final int REQUEST_ACCESS_READ = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_pantalla__actualizar );

        version=getIntent().getStringExtra("version");
        url=getIntent().getStringExtra("url");

        btn_install=(Button) findViewById(R.id.btn_Install);
        btn_descargar=(Button) findViewById(R.id.btn_Actualizar);

        btn_install.setVisibility(View.INVISIBLE);

        btn_descargar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                oMyReceiver.Descargar(url);
                btn_descargar.setVisibility(View.VISIBLE);

            }
        });
        btn_install.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                oMyReceiver.Install();
            }
        } );

        Init();

        if(ActivityCompat.checkSelfPermission( Pantalla_Actualizar.this, Manifest.permission.READ_EXTERNAL_STORAGE) !=  PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions( Pantalla_Actualizar.this,new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},REQUEST_ACCESS_READ);
            ActivityCompat.requestPermissions( Pantalla_Actualizar.this,new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},REQUEST_ACCESS_READ);
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String permissions[], @NonNull int[] grantResults) {

        switch (requestCode) {
            case REQUEST_ACCESS_READ: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    Toast.makeText (Pantalla_Actualizar.this,"Permiso concedido",Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText (Pantalla_Actualizar.this,"Permiso no concedido",Toast.LENGTH_SHORT).show();
                }
                return;

            }
        }
    }

    private  void Init(){
        oMyReceiver=new MyReceiver(Pantalla_Actualizar.this);
        oMyReceiver.registrar(oMyReceiver);


    }

    @Override
    protected void onPause() {
        super.onPause();
        oMyReceiver.borrarRegistro(oMyReceiver);
    }


    @Override
    protected void onResume() {
        super.onResume();
        oMyReceiver.registrar(oMyReceiver);
    }
}
