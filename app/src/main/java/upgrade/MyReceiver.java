package upgrade;

import android.app.Activity;
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

import java.io.File;


public class MyReceiver extends BroadcastReceiver {

    DownloadManager my_DownloadManager;
    long tamaño;
    IntentFilter my_IntentFilter;
    private Context my_Context;
    private Activity my_Activity;

    public MyReceiver(Activity activity_) {
        this.my_Context=activity_;
        this.my_Activity=activity_;

        my_IntentFilter = new IntentFilter(  );
        my_IntentFilter.addAction( DownloadManager.ACTION_DOWNLOAD_COMPLETE );
    }

    @Override
    public void onReceive(Context context, Intent intent) {

        Log.e( "EVENTO DESCARGAR",intent.getAction() );

        String action=intent.getAction();

        if(DownloadManager.ACTION_DOWNLOAD_COMPLETE.equals( action )){
            intent.getLongExtra( DownloadManager.EXTRA_DOWNLOAD_ID,0 );
            DownloadManager.Query query = new DownloadManager.Query();
            query.setFilterById( tamaño );

            Cursor cursor=my_DownloadManager.query( query );

            if(cursor.moveToFirst()){
                int columnINDEX=cursor.getColumnIndex( DownloadManager.COLUMN_STATUS );
                if(DownloadManager.STATUS_SUCCESSFUL==cursor.getInt( columnINDEX )){
                    String uriString =cursor.getString( cursor.getColumnIndex( DownloadManager.COLUMN_LOCAL_URI ) );

                    File file = new File(uriString);

                    Intent pantallainstall = new Intent(Intent.ACTION_VIEW );
                    pantallainstall.setFlags( Intent.FLAG_ACTIVITY_NEW_TASK );
                    pantallainstall.setDataAndType(Uri.parse( uriString ),"application/vnd.android.package-archive" );
                    my_Activity.startActivity(  pantallainstall);

                    Log.e( "SE DESCARGO","NO GENERO PROBLEMA AL DESCARGAR" );
                }
            }
        }

    }

    public void descargar(){

        String URL ="https://firebasestorage.googleapis.com/v0/b/mainco-healt-app.appspot.com/o/app-release.apk?alt=media&token=f6e027a8-04e7-4917-935a-97f81ad91003";
        DownloadManager.Request my_Request;

        my_DownloadManager=(DownloadManager) my_Context.getSystemService( Context.DOWNLOAD_SERVICE );
        my_Request= new DownloadManager.Request( Uri.parse( URL ) );

        String fileextencion = MimeTypeMap.getFileExtensionFromUrl( URL );
        String name = URLUtil.guessFileName( URL,null,fileextencion );

        File myfile = new File( Environment.getExternalStorageState(),"MAINCO" );
        boolean iscreada=myfile.exists();

        if(iscreada==true){
            iscreada=myfile.mkdirs();

        my_Request.setDestinationInExternalPublicDir( "/MAINCO",name );

        String h =my_Request.setDestinationInExternalPublicDir( "/MAINCO",name ).toString();

        Log.e("RUTA_APK",h);
        Log.e("DESCARGAR","OK");

        tamaño=my_DownloadManager.enqueue( my_Request );
        }
    }

    public void REGISTRAR(MyReceiver oMyReceiver){
        my_Context.registerReceiver( oMyReceiver,my_IntentFilter );
    }
}
