package com.example.preferencias;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.MediaController;

import java.io.IOException;

public class MusicaActivity extends AppCompatActivity implements MediaController.MediaPlayerControl {
    MediaPlayer mediaPlayer;
    MediaController mediaController;
    Handler handler;
    Button botonAtras;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_musica);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mediaPlayer = new MediaPlayer();
        mediaController = new MediaController(this);
        mediaController.setMediaPlayer(this);
        mediaController.setAnchorView(findViewById(R.id.linearLayout));
        handler = new Handler();
        botonAtras = findViewById(R.id.buttonMusicaAtras);
        botonAtras.setOnClickListener(v -> {
            mediaPlayer.stop();
            mediaPlayer.release();
            finish();
        });



        try {
            mediaPlayer.setDataSource(this, Uri.parse("android.resource://" + getPackageName()
                    + "/" + R.raw.cancion));
            mediaPlayer.prepare();
        } catch (IOException e) {
            e.printStackTrace();
        }
        mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                handler.post(new Runnable() {
                    public void run() {
                        // Muestra el control en pantalla. Tras 20 segundos de inactividad, se oculta
                        mediaController.show(20000);
//                        mediaPlayer.start();
                    }
                });
            }
        });

    }



    @Override
    public void start() {
        mediaPlayer.start();
    }

    @Override
    public void pause() {
        mediaPlayer.pause();
    }

    @Override
    public int getDuration() {
        return 0;
    }

    @Override
    public int getCurrentPosition() {
        return 0;
    }

    @Override
    public void seekTo(int pos) {

    }

    @Override
    public boolean isPlaying() {
        return false;
    }

    @Override
    public int getBufferPercentage() {
        return 0;
    }

    @Override
    public boolean canPause() {
        return true;
    }

    @Override
    public boolean canSeekBackward() {
        return false;
    }

    @Override
    public boolean canSeekForward() {
        return false;
    }

    @Override
    public int getAudioSessionId() {
        return 0;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mediaPlayer != null) {
            if (mediaPlayer.isPlaying()) {
                mediaPlayer.stop();
            }
            mediaPlayer.release();
            mediaPlayer = null;
        }

    }
}