package com.jkapps.juancamilo.subjective;

import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.SparseIntArray;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class MainActivity extends AppCompatActivity{

    private SeekBar barAudio;
    private boolean barActiva;
    private boolean buttonState;

    ImageButton playButton;
    Button saveButton;
    TextView user,title,election,pregunta;
    ImageView display;
    Boolean testEnd=false;
    Button btn0,btn45,btn90,btn135,btn180,btn225,btn270,btn315;

    int retryNumber = 0;
    int cuad;

    private PlayerAdapter pAdapter;

    private int theAnswer = 0;
    private int questionNumber=1;

    private int counterShown=0;
    private int counterShownArreglo=0;

    boolean shouldAllowBack = false;

    String sessionId,usuario;
    Bundle extras;

    Random r = new Random();
    int i1;
    int[] alreadyShow           = {0,0,0,0,0,0,0,0};
    int[] alreadyShowArreglo    = {0,0,0,0,0,0,0,0};

    int boundLow;
    int boundHigh;

    SparseIntArray dictionary = new SparseIntArray();

    Toast toast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

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
        pregunta = findViewById(R.id.textView3);

        //Botones Ángulos
        btn0    = findViewById(R.id.button_0);
        btn45   = findViewById(R.id.button_45);
        btn90   = findViewById(R.id.button_90);
        btn135  = findViewById(R.id.button_135);
        btn180  = findViewById(R.id.button_180);
        btn225  = findViewById(R.id.button_225);
        btn270  = findViewById(R.id.button_270);
        btn315  = findViewById(R.id.button_315);

        extras = getIntent().getExtras();
        if (extras != null) {
            sessionId = extras.getString("ID");
        }
        usuario = "Usuario: "+sessionId;
        user.setText(usuario);

        btn45.bringToFront();
        btn135.bringToFront();
        btn225.bringToFront();
        btn315.bringToFront();

        btn0.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                theAnswer = 0;
                toast = Toast.makeText(getApplicationContext(),"Elegiste: "+theAnswer, Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.CENTER_VERTICAL,0,0);
                toast.show();
            }
        });

        btn45.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                theAnswer = 45;
                toast = Toast.makeText(getApplicationContext(),"Elegiste: "+theAnswer, Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.CENTER_VERTICAL,0,0);
                toast.show();
            }
        });

        btn90.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                theAnswer = 90;
                toast = Toast.makeText(getApplicationContext(),"Elegiste: "+theAnswer, Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.CENTER_VERTICAL,0,0);
                toast.show();
            }
        });

        btn135.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                theAnswer = 135;
                toast = Toast.makeText(getApplicationContext(),"Elegiste: "+theAnswer, Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.CENTER_VERTICAL,0,0);
                toast.show();
            }
        });

        btn180.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                theAnswer = 180;
                toast = Toast.makeText(getApplicationContext(),"Elegiste: "+theAnswer, Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.CENTER_VERTICAL,0,0);
                toast.show();
            }
        });

        btn225.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                theAnswer = 225;
                toast = Toast.makeText(getApplicationContext(),"Elegiste: "+theAnswer, Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.CENTER_VERTICAL,0,0);
                toast.show();
            }
        });

        btn270.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                theAnswer = 270;
                toast = Toast.makeText(getApplicationContext(),"Elegiste: "+theAnswer, Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.CENTER_VERTICAL,0,0);
                toast.show();
            }
        });

        btn315.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                theAnswer = 315;
                toast = Toast.makeText(getApplicationContext(),"Elegiste: "+theAnswer, Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.CENTER_VERTICAL,0,0);
                toast.show();
            }
        });

        dictionary.put(1, R.raw._0zoom);
        dictionary.put(2, R.raw._0arreglo);

        dictionary.put(3, R.raw._15zoom);
        dictionary.put(4, R.raw._15arreglo);

        dictionary.put(5, R.raw._30zoom);
        dictionary.put(6, R.raw._30zoom);

        dictionary.put(7, R.raw._45zoom);
        dictionary.put(8, R.raw._45arreglo);

        dictionary.put(9, R.raw._60arreglo);
        dictionary.put(10, R.raw._60arreglo);

        dictionary.put(11, R.raw._75zoom);
        dictionary.put(12, R.raw._75arreglo);

        dictionary.put(13, R.raw._90zoom);
        dictionary.put(14, R.raw._90arreglo);

        dictionary.put(15, R.raw._105zoom);
        dictionary.put(16, R.raw._105arreglo);

        dictionary.put(17, R.raw._120zoom);
        dictionary.put(18, R.raw._120arreglo);

        dictionary.put(19, R.raw._135zoom);
        dictionary.put(20, R.raw._135arreglo);

        dictionary.put(21, R.raw._150zoom);
        dictionary.put(22, R.raw._150arreglo);

        dictionary.put(23, R.raw._165zoom);
        dictionary.put(24, R.raw._165arreglo);

        dictionary.put(25, R.raw._180zoom);
        dictionary.put(26, R.raw._180arreglo);


        dictionary.put(27, R.raw._195zoom);
        dictionary.put(28, R.raw._195arreglo);

        dictionary.put(29, R.raw._210zoom);
        dictionary.put(30, R.raw._210arreglo);

        dictionary.put(31, R.raw._225zoom);
        dictionary.put(32, R.raw._225arreglo);

        dictionary.put(33, R.raw._240zoom);
        dictionary.put(34, R.raw._240arreglo);

        dictionary.put(35, R.raw._255zoom);
        dictionary.put(36, R.raw._255arreglo);

        dictionary.put(37, R.raw._270zoom);
        dictionary.put(38, R.raw._270arreglo);


        dictionary.put(39, R.raw._285zoom);
        dictionary.put(40, R.raw._285arreglo);

        dictionary.put(41, R.raw._300zoom);
        dictionary.put(42, R.raw._300arreglo);

        dictionary.put(43, R.raw._315arreglo);
        dictionary.put(44, R.raw._315arreglo);

        dictionary.put(45, R.raw._330arreglo);
        dictionary.put(46, R.raw._330arreglo);

        dictionary.put(47, R.raw._345zoom);
        dictionary.put(48, R.raw._345arreglo);


        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveButton.setEnabled(false);
                executeService("https://subjective.000webhostapp.com/insertar_resp.php");
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        i1=calculateTrack();
        Log.d("TAG","TrackActual: "+i1);
        pAdapter.loadMedia(dictionary.get(i1));
        pregunta.setText("Número:" +String.valueOf(i1));

    }

    @Override
    public void onBackPressed() {
        if (!shouldAllowBack) {
            toast = Toast.makeText(getApplicationContext(), "No se puede retroceder entre preguntas!", Toast.LENGTH_LONG);
            toast.setGravity(Gravity.CENTER_VERTICAL,0,0);
            toast.show();
        } else {
            super.onBackPressed();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        pAdapter.release();
    }

    boolean isOdd( int val ) { return (val & 0x01) != 0; }

    boolean check(int[] arr, int toCheckValue) {
        boolean test = false;
        for (int element : arr) {
            if (element == toCheckValue) {
                test = true;
                break;
            }
        }

        return test;
    }

    int calculateTrack() {

        //Cuadrante Random
        cuad = r.nextInt(8)+1;

        while(check(alreadyShow,cuad)){
            cuad = r.nextInt(8)+1;
        }

        alreadyShow[counterShown]=cuad;
        counterShown=counterShown+1;

        switch (cuad){
            case 1:
                boundLow = 1;
                boundHigh = 4;
                break;
            case 2:
                boundLow = 5;
                boundHigh = 10;
                break;
            case 3:
                boundLow = 11;
                boundHigh = 16;
                break;
            case 4:
                boundLow = 17;
                boundHigh = 22;
                break;
            case 5:
                boundLow = 23;
                boundHigh = 28;
                break;
            case 6:
                boundLow = 29;
                boundHigh = 34;
                break;
            case 7:
                boundLow = 35;
                boundHigh = 40;
                break;
            case 8:
                boundLow = 41;
                boundHigh = 46;
                break;
        }
        //
        Log.d("TAG","Cuadrante: "+cuad);
        int x = r.nextInt(boundHigh-boundLow)+boundLow;
        if(!isOdd(x)) {
            x = x - 1;
        }

        return x;
    }

    int calculateTrackArreglo() {

        //Cuadrante Random
        cuad = r.nextInt(8)+1;

        while(check(alreadyShowArreglo,cuad)){
            cuad = r.nextInt(8)+1;
        }

        alreadyShowArreglo[counterShownArreglo]=cuad;
        counterShownArreglo=counterShownArreglo+1;

        switch (cuad){
            case 1:
                boundLow = 1;
                boundHigh = 4;
                break;
            case 2:
                boundLow = 5;
                boundHigh = 10;
                break;
            case 3:
                boundLow = 11;
                boundHigh = 16;
                break;
            case 4:
                boundLow = 17;
                boundHigh = 22;
                break;
            case 5:
                boundLow = 23;
                boundHigh = 28;
                break;
            case 6:
                boundLow = 29;
                boundHigh = 34;
                break;
            case 7:
                boundLow = 35;
                boundHigh = 40;
                break;
            case 8:
                boundLow = 41;
                boundHigh = 46;
                break;
        }
        //
        Log.d("TAG","Cuadrante: "+cuad);
        int x = r.nextInt(boundHigh-boundLow)+boundLow;
        if(isOdd(x)) {
            x = x + 1;
        }

        return x;
    }

    private void changeQuestion(){

        if(questionNumber<=16){

            retryNumber=0;
            playButton.setEnabled(true);
            playButton.setAlpha(1.0f);
            buttonState = true;

            if (pAdapter.isPlaying()){
                pAdapter.pause();
            }

            pAdapter.reset();

            // NEXT AUDIO
            if(isOdd(questionNumber)){
                i1=calculateTrack();
            }
            else{
                i1=calculateTrackArreglo();
            }
            pAdapter.loadMedia(dictionary.get(i1));
            pregunta.setText("Número:" +String.valueOf(i1));
            Log.d("TAG","TrackActual: "+i1);

            //

            title.setText("PREGUNTA "+String.valueOf(questionNumber));

        }
        else{

            title.setText("GRACIAS");
            pregunta.setVisibility(View.INVISIBLE);
            election.setVisibility(View.INVISIBLE);
            display.setVisibility(View.INVISIBLE);
            user.setVisibility(View.INVISIBLE);
            barAudio.setVisibility(View.INVISIBLE);
            playButton.setVisibility(View.INVISIBLE);
            saveButton.setVisibility(View.INVISIBLE);


            btn0.setVisibility(View.INVISIBLE);
            btn45.setVisibility(View.INVISIBLE);
            btn90.setVisibility(View.INVISIBLE);
            btn135.setVisibility(View.INVISIBLE);
            btn180.setVisibility(View.INVISIBLE);
            btn225.setVisibility(View.INVISIBLE);
            btn270.setVisibility(View.INVISIBLE);
            btn315.setVisibility(View.INVISIBLE);


            testEnd = true;

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

    private void executeService(String URL){
        StringRequest stringRequest=new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //Toast.makeText(getApplicationContext(), "Res: "+response, Toast.LENGTH_LONG);
                questionNumber=questionNumber+1;
                changeQuestion();
                saveButton.setEnabled(true);


                            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                toast = Toast.makeText(getApplicationContext(), "Error: "+error, Toast.LENGTH_LONG);
                toast.setGravity(Gravity.CENTER_VERTICAL,0,0);
                toast.show();
                saveButton.setEnabled(true);
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> parameters = new HashMap<>();

                parameters.put("codigo",String.valueOf(sessionId)+String.valueOf(questionNumber));
                parameters.put("pregunta",String.valueOf(i1));
                parameters.put("respuesta",String.valueOf(theAnswer));

                return parameters;
            }
        };

        stringRequest.setRetryPolicy(new DefaultRetryPolicy(1000,DefaultRetryPolicy.DEFAULT_MAX_RETRIES,DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    private void initializeSeekbar() {
        barAudio.setOnSeekBarChangeListener(
                new SeekBar.OnSeekBarChangeListener() {

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
        public void onPlaybackCompleted() {
            playButton.setEnabled(true);
            playButton.setAlpha(1.0f);
            buttonState=true;
            retryNumber=retryNumber+1;
            toast = Toast.makeText(getApplicationContext(),"Reproducciones: "+retryNumber, Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER_VERTICAL,0,0);
            toast.show();
            if(retryNumber==2){
                playButton.setEnabled(false);
                playButton.setAlpha(0.5f);
            }
        }

    }

}
