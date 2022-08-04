package com.jkapps.juancamilo.subjective;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;

public class IntroActivity extends AppCompatActivity {

    EditText editName;
    Button nameButton;
    WebView webView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);


        nameButton = findViewById(R.id.button_name);
        editName = findViewById(R.id.etext_name);
        webView = findViewById(R.id.webViewx);

        final WebSettings webSettings = webView.getSettings();
        webSettings.setDefaultFontSize(18);
        webView.loadData(getString(R.string.hello), "text/html; charset=utf-8", "utf-8");

        nameButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), CalibrationActivity.class);
                intent.putExtra("ID", editName.getText().toString());
                startActivity(intent);

            }
        });

    }
}
