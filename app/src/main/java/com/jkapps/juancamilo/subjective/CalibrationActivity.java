package com.jkapps.juancamilo.subjective;

import android.content.Intent;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

public class CalibrationActivity extends AppCompatActivity {

    Button btn;
    WebView webView;
    Bundle extras;
    String sessionId;
    TextView tx;
    private String usuario;
    boolean calibrated = false;
    boolean shouldAllowBack = false;
    VideoView videoView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calibration);

        tx = findViewById(R.id.textView);
        btn = findViewById(R.id.button);
        webView = findViewById(R.id.webView);
        videoView = findViewById(R.id.videoView);

        videoView.setVideoPath("android.resource://" + getPackageName() + "/" + R.raw.intro3);
        MediaController mediaController = new MediaController(this);
        videoView.setMediaController(mediaController);
        videoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {

            @Override
            public void onCompletion(MediaPlayer mp) {
                calibrated=true;
                btn.setEnabled(true);
            }
        });

        final WebSettings webSettings = webView.getSettings();
        webSettings.setDefaultFontSize(18);
        webView.loadData(getString(R.string.calibration), "text/html; charset=utf-8", "utf-8");

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(calibrated){
                    btn.setEnabled(true);
                    Intent intent = new Intent(v.getContext(), ExampleActivity.class);
                    intent.putExtra("ID", sessionId);
                    startActivity(intent);
                }
                else{
                    videoView.start();
                    btn.setEnabled(false);
                }
            }
        });
    }

    protected void onStart() {
        super.onStart();
        extras = getIntent().getExtras();
        if (extras != null) {
            sessionId = extras.getString("ID");
        }
        usuario = "Usuario: "+sessionId;
        tx.setText(usuario);
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    public void onBackPressed() {
        if (!shouldAllowBack) {
            Toast toast = Toast.makeText(getApplicationContext(), "No se puede retroceder entre preguntas!", Toast.LENGTH_LONG);

            toast.show();
        } else {
            super.onBackPressed();
        }
    }

}
