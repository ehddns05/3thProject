package com.example.user.a3thproject;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    final static int EDIT_VALUE = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    //맵 정보 게시판으로 넘어가는 메서드
    public void go_map_info(View view){
        Intent intent = new Intent(this, MapInfoListActivity.class);
        startActivityForResult(intent,EDIT_VALUE);
    }

    //공략 게시판으로 넘어가는 메서드
    public void go_map_tactics(View view){

    }

    //마이페이지로 넘어가는 메서드
    public void go_mypage(View view){

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode){
            case EDIT_VALUE :
                if (resultCode == RESULT_OK){
                    String message = data.getStringExtra("Textout");
                    Toast.makeText(this, message, Toast.LENGTH_LONG).show();
                }else if (resultCode == RESULT_CANCELED){
                    String message = "처리실패";
                    Toast.makeText(this, message, Toast.LENGTH_LONG).show();
                }
                break;
        }
    }
}
