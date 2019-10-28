package com.gamecodeschool.medihub;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class Result extends AppCompatActivity {

    TextView tvo,tvt,tvth,tvf,tvfi;
    private Button backButton;
    private TextView resultTextView;
    private String resultText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        tvo=findViewById(R.id.tvo);
        tvt=findViewById(R.id.tvt);
        tvth=findViewById(R.id.tvth);
        tvf=findViewById(R.id.tvf);
        tvfi=findViewById(R.id.tvfi);

        tvo.setVisibility(View.GONE);
        tvt.setVisibility(View.GONE);
        tvth.setVisibility(View.GONE);
        tvf.setVisibility(View.GONE);
        tvfi.setVisibility(View.GONE);

        resultTextView = findViewById(R.id.result_textview);
        backButton = findViewById(R.id.back_button);
        resultText = getIntent().getStringExtra(TextRecognization.RESULT_TEXT);

        resultTextView.setText(resultText);

        String at="CIPROFLOXACIN";
        String bt="ciprofloxacin";
        String ct="ASPIRIN";
        String dt="aspirin";
        String et="DEXAMETHASONE";
        String ft="dexamethasone";
        String gt="LIBRAX";
        String ht="librax";
        String it="CROCIN";
        String jt="crocin";

        if(resultText.equals(at))
            tvo.setVisibility(View.VISIBLE);
         else if(resultText.equals(bt))
             tvo.setVisibility(View.VISIBLE);
        else if(resultText.equals(ct))
            tvt.setVisibility(View.VISIBLE);
        else if(resultText.equals(dt))
            tvt.setVisibility(View.VISIBLE);
        else if(resultText.equals(et))
            tvth.setVisibility(View.VISIBLE);
        else if(resultText.equals(ft))
            tvth.setVisibility(View.VISIBLE);
        else if(resultText.equals(gt))
            tvf.setVisibility(View.VISIBLE);
        else if(resultText.equals(ht))
            tvf.setVisibility(View.VISIBLE);
        else if(resultText.equals(it))
            tvfi.setVisibility(View.VISIBLE);
        else if(resultText.equals(jt))
            tvfi.setVisibility(View.VISIBLE);
        else
            Toast.makeText(Result.this,"No such medicine found",Toast.LENGTH_LONG).show();


        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


    }
}
