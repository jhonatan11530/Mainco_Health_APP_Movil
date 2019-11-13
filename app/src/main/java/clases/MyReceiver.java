package clases;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.net.Uri;
import android.os.Environment;
import android.util.Log;
import android.webkit.MimeTypeMap;
import android.webkit.URLUtil;

import com.example.mainco.OperadorActivity;

import java.io.File;

public class MyReceiver extends BroadcastReceiver {

    DownloadManager my_DownloadManager;
    long tamaño;

    MyReceiver oMyReceiver;
    IntentFilter my_IntentFilter;

    private Context my_context;
    private Activity my_activity;

    public MyReceiver(Activity activity_) {
        this.my_context=activity_;
        this.my_activity=activity_;

        my_IntentFilter=new IntentFilter();
        my_IntentFilter.addAction(DownloadManager.ACTION_DOWNLOAD_COMPLETE);



    }

    public void onReceive(Context context,Intent intent) {

        Log.e("Evento_Action",intent.getAction());

        String action=intent.getAction();

        if(DownloadManager.ACTION_DOWNLOAD_COMPLETE.equals(action)){
            intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID,0);
            DownloadManager.Query query=new DownloadManager.Query();
            query.setFilterById(tamaño);

            Cursor cursor=my_DownloadManager.query(query);

            if(cursor.moveToFirst()){
                int columnIndex=cursor.getColumnIndex(DownloadManager.COLUMN_STATUS);
                if(DownloadManager.STATUS_SUCCESSFUL==cursor.getInt(columnIndex)){
                    String uriString=cursor.getString(cursor.getColumnIndex(DownloadManager.COLUMN_LOCAL_URI));

                    File file=new File(uriString);

                    System.out.print(file);

                    Intent pantallaInstall=new Intent(android.content.Intent.ACTION_VIEW);
                    pantallaInstall.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    pantallaInstall.setDataAndType( Uri.parse(uriString),"application/vnd.android.package-archive");





                    my_context.startActivity(pantallaInstall);



                    Log.e("MsjDescarga","Se descargo sin problemas");

                }
            }


        }
    };

    public void Descargar(String url){
        url="";
        DownloadManager.Request my_Request;

        my_DownloadManager=(DownloadManager) my_context.getSystemService(Context.DOWNLOAD_SERVICE);

        my_Request=new DownloadManager.Request(Uri.parse(url));
        String fileExtension = MimeTypeMap.getFileExtensionFromUrl(url);
        String name= URLUtil.guessFileName(url,null,fileExtension);

        //crear la carpeta
        File miFile=new File( Environment.getExternalStorageDirectory(),"MAINCO");
        boolean isCreada=miFile.exists();

        if(isCreada==true){
            isCreada=miFile.mkdirs();
        }


        my_Request.setDestinationInExternalPublicDir("/MAINCO",name);

        String h=my_Request.setDestinationInExternalPublicDir("/MAINCO",name).toString();

        Log.e("ruta_MAINCO",h);

        Log.e("Descargar","Ok");

        tamaño=my_DownloadManager.enqueue(my_Request);






    }

    public void registrar(MyReceiver oMyReceiver){
        my_context.registerReceiver(oMyReceiver,my_IntentFilter);
    }

    public  void borrarRegistro(MyReceiver oMyReceiver){
        my_context.unregisterReceiver(oMyReceiver);
    }
}
