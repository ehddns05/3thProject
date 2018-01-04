package com.example.user.a3thproject;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.TextView;

public class DMDetailActivity extends AppCompatActivity {

    TextView num, title, date, writer, content;
    EditText recentMessage;
    ScrollView scrollView;
    Button sendBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dmdetail);

        num = findViewById(R.id.dmDetail_num);
        title = findViewById(R.id.dmDetail_title);
        date = findViewById(R.id.dmDetail_date);
        writer = findViewById(R.id.dmDetail_writer);
        content = findViewById(R.id.dmDetail_content);
        scrollView = findViewById(R.id.dmDetail_scroll);
        recentMessage = findViewById(R.id.dmDetail_inputDM);
        sendBtn = findViewById(R.id.dmDetail_sendDM);

        scrollView.setVerticalScrollBarEnabled(true);
    }
}
