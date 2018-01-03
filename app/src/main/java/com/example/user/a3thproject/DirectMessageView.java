package com.example.user.a3thproject;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Created by SCITMASTER on 2018-01-02.
 */

public class DirectMessageView extends LinearLayout {

    TextView num;
    TextView title;
    TextView writer;
    TextView date;

    public DirectMessageView(Context context) {
        super(context);
    }

    public DirectMessageView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public void init(Context context){
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.list_layout, this, true);

        num = findViewById(R.id.list_number);
        title = findViewById(R.id.list_title);
        writer = findViewById(R.id.list_writer);
        date = findViewById(R.id.list_writeDate);
    }

    public void setNum(int number){
        num.setText(String.valueOf(number));
    }

    public void setTitle(String listTitle){
        title.setText(listTitle);
    }

    public void setWriter(String writer_name){
        writer.setText(writer_name);
    }

    public void setDate(String writeDate){
        date.setText(writeDate);
    }
}
