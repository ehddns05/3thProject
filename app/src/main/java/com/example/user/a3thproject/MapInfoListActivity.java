package com.example.user.a3thproject;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

public class MapInfoListActivity extends AppCompatActivity {

    private Spinner map_info_searchType_spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map_info_list);

        map_info_searchType_spinner = (Spinner) findViewById(R.id.map_info_searchType);
        ArrayAdapter map_info_searchType_adapter = ArrayAdapter.createFromResource(this, R.array.map_info_searchType, android.R.layout.simple_spinner_item);
        map_info_searchType_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        map_info_searchType_spinner.setAdapter(map_info_searchType_adapter);
    }
}
