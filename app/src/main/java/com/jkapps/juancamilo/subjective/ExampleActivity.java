package com.jkapps.juancamilo.subjective;

import android.content.Intent;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

public class ExampleActivity extends AppCompatActivity{

    public static final int MEDIA_RES_ID = R.raw._90arreglo;

    private SeekBar barAudio;
    private boolean barActiva;
    private boolean buttonState;

    ImageButton playButton;
    Button saveButton;
    TextView user,title,election;
    ImageView display;

    private int theAnswer;
    private PlayerAdapter pAdapter;
    int retryNumber = 0;
    boolean shouldAllowBack = false;

    private int questionNumber=1;

    String sessionId,usuario;
    Bundle extras;
    Button btn0,btn45,btn90,btn135,btn180,btn225,btn270,btn315;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_example);

        initializer();
        initializeSeekbar();
        initializePlaybackController();
        buttonState=true;

        //User ID
        user = findViewById(R.id.textView5);
        //Titulo
        title = findViewById(R.id.titu);
        //Imagen Cuadrante o Ángulos
        display = findViewById(R.id.imageView4);
        //Cambio Pregunta
        saveButton = findViewById(R.id.button_save);
        //Pregunta
        election = findViewById(R.id.textView2);

        //Botones Ángulos
        btn0    = findViewById(R.id.button_0);
        btn45   = findViewById(R.id.button_45);
        btn90   = findViewById(R.id.button_90);
        btn135  = findViewById(R.id.button_135);
        btn180  = findViewById(R.id.button_180);
        btn225  = findViewById(R.id.button_225);
        btn270  = findViewById(R.id.button_270);
        btn315  = findViewById(R.id.button_315);

        btn45.bringToFront();
        btn135.bringToFront();
        btn225.bringToFront();
        btn315.bringToFront();


        extras = getIntent().getExtras();
        if (extras != null) {
            sessionId = extras.getString("ID");
        }
        usuario = "Usuario: "+sessionId;
        user.setText(usuario);

        btn0.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                theAnswer = 0;
                Toast toast = Toast.makeText(getApplicationContext(),"Elegiste: "+theAnswer, Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.CENTER_VERTICAL,0,0);
                toast.show();
            }
        });

        btn45.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                theAnswer = 45;
                Toast toast = Toast.makeText(getApplicationContext(),"Elegiste: "+theAnswer, Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.CENTER_VERTICAL,0,0);
                toast.show();
            }
        });

        btn90.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                theAnswer = 90;
                Toast toast = Toast.makeText(getApplicationContext(),"Elegiste: "+theAnswer, Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.CENTER_VERTICAL,0,0);
                toast.show();
            }
        });

        btn135.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                theAnswer = 135;
                Toast toast = Toast.makeText(getApplicationContext(),"Elegiste: "+theAnswer, Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.CENTER_VERTICAL,0,0);
                toast.show();
            }
        });

        btn180.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                theAnswer = 180;
                Toast toast = Toast.makeText(getApplicationContext(),"Elegiste: "+theAnswer, Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.CENTER_VERTICAL,0,0);
                toast.show();
            }
        });

        btn225.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                theAnswer = 225;
                Toast toast = Toast.makeText(getApplicationContext(),"Elegiste: "+theAnswer, Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.CENTER_VERTICAL,0,0);
                toast.show();
            }
        });

        btn270.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                theAnswer = 270;
                Toast toast = Toast.makeText(getApplicationContext(),"Elegiste: "+theAnswer, Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.CENTER_VERTICAL,0,0);
                toast.show();
            }
        });

        btn315.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                theAnswer = 315;
                Toast toast = Toast.makeText(getApplicationContext(),"Elegiste: "+theAnswer, Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.CENTER_VERTICAL,0,0);
                toast.show();
            }
        });

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeQuestion();
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        pAdapter.loadMedia(MEDIA_RES_ID);

    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    public void onBackPressed() {
        if (!shouldAllowBack) {
            Toast toast = Toast.makeText(getApplicationContext(), "No se puede retroceder entre preguntas!", Toast.LENGTH_LONG);
            toast.setGravity(Gravity.CENTER_VERTICAL,0,0);
            toast.show();
        } else {
            super.onBackPressed();
        }
    }

    private void initializer() {

        playButton = findViewById(R.id.button_play);
        barAudio =  findViewById(R.id.seekBar);
        barAudio.setEnabled(false);

        playButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (buttonState){
                    pAdapter.play();
                    playButton.setEnabled(false);
                    playButton.setAlpha(0.5f);
                    buttonState=false;
                }
            }
        });
    }

    private void changeQuestion(){

        if (questionNumber<2){
            retryNumber=0;
            playButton.setEnabled(true);
            playButton.setAlpha(1.0f);
            buttonState=true;

            if (pAdapter.isPlaying()){
                pAdapter.pause();
            }
            questionNumber+=1;
            title.setText("EJEMPLO "+Integer.toString(questionNumber));
            pAdapter.reset();
            pAdapter.loadMedia(R.raw._270zoom);
        }
        else
        {
            Toast toast = Toast.makeText(getApplicationContext(), "Fin del Tutorial", Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER_VERTICAL,0,0);
            toast.show();
            Intent intent = new Intent(this, MainActivity.class);
            intent.putExtra("ID", sessionId);
            startActivity(intent);

        }


    }

    private void initializeSeekbar() {
        barAudio.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

                    int userSelectedPosition = 0;

                    @Override
                    public void onStartTrackingTouch(SeekBar seekBar) {
                        barActiva = true;
                    }

                    @Override
                    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                        if (fromUser) {
                            userSelectedPosition = progress;
                        }
                    }

                    @Override
                    public void onStopTrackingTouch(SeekBar seekBar) {
                        barActiva = false;
                        pAdapter.seekTo(userSelectedPosition);
                    }
                });
    }

    private void initializePlaybackController() {
        MediaPlayerHolder mMediaPlayerHolder = new MediaPlayerHolder(this);
        mMediaPlayerHolder.setPlaybackInfoListener(new PlaybackListener());
        pAdapter = mMediaPlayerHolder;

    }

    public class PlaybackListener extends PlaybackInfoListener {

        @Override
        public void onDurationChanged(int duration) {
            barAudio.setMax(duration);
        }

        @Override
        public void onPositionChanged(int position) {
            if (!barActiva) {
                if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.N) {
                    barAudio.setProgress(position, true);
                }else
                {
                    barAudio.setProgress(position);
                }
            }
        }

        @Override
        public void onStateChanged(@State int state) {
        }

        @Override
        public void onPlaybackCompleted() {
            playButton.setEnabled(true);
            playButton.setAlpha(1.0f);
            buttonState=true;
            retryNumber=retryNumber+1;
            Toast toast = Toast.makeText(getApplicationContext(),"Número de reproducciones permitidas: "+retryNumber, Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER_VERTICAL,0,0);
            toast.show();
            if(retryNumber==2){
                playButton.setEnabled(false);
                playButton.setAlpha(0.5f);
            }
        }
    }
}
