package Introducciones;

import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.MediaController;
import android.widget.VideoView;

import androidx.appcompat.app.AppCompatActivity;

import com.Mainco.App.R;

public class PAUSE_ACTIVAS extends AppCompatActivity {

    final Uri video = Uri.parse("");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_videoseix);

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_FULL_SENSOR);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);


        VideoView mVideoView = findViewById(R.id.mp4);

        mVideoView.setVideoURI(video);

        MediaController media = new MediaController(this);

        media.setAnchorView(mVideoView);
        mVideoView.setMediaController(media);
        mVideoView.start();

    }
}
