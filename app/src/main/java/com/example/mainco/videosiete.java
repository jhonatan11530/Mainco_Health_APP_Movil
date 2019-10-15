package com.example.mainco;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.MediaController;
import android.widget.VideoView;

import androidx.appcompat.app.AppCompatActivity;

public class videosiete extends AppCompatActivity {

    private VideoView mVideoView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_videosiete );

        setRequestedOrientation( ActivityInfo.SCREEN_ORIENTATION_FULL_SENSOR);
        this.getWindow().setFlags( WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);


        mVideoView =(VideoView)findViewById(R.id.mp4);

        mVideoView.setVideoPath("android.resource://" + getPackageName() + "/" + R.raw.sample);

        MediaController media = new MediaController( this );

        media.setAnchorView( mVideoView );
        mVideoView.setMediaController( media );
        mVideoView.start();
    }
}
