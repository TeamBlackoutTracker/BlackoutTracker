package com.example.myfirstapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import java.text.DateFormat;
import java.util.Date;

public class HistoryActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        String currentDateTimeString = DateFormat.getDateInstance().format(new Date());

        // textView is the TextView view that should display it
        TextView currDate = (TextView) findViewById(R.id.currentDate);
        currDate.setText(currentDateTimeString);

//        int receiveValue = getIntent().getIntExtra("duration", 0);
//        TextView duration = (TextView) findViewById(R.id.historyDuration);
//        duration.setText(receiveValue);
    }
}
