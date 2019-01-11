package com.example.a2019vmurthy2.invisimaze;


import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;
import android.graphics.Color;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button b;
    MyNewView mnv_canvas;
    private TextView end;
    private TextView gj;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        b = findViewById(R.id.button);
        b.setOnClickListener(this);

        gj = findViewById(R.id.goodjob);

        mnv_canvas = findViewById(R.id.mnv_canvas);
        mnv_canvas.setOnClickListener(this);

        end = findViewById(R.id.end);
        end.setOnClickListener(this);


    }

    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.button:
                mnv_canvas.clearCircles();

                //case R.id.end:
                //gj.setText("Good Job!");


        }
    }
}
