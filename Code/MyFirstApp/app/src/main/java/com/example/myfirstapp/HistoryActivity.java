package com.example.myfirstapp;

import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import java.text.DateFormat;
import java.util.Date;

import javax.microedition.khronos.opengles.GL;

public class HistoryActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        String currentDateTimeString = DateFormat.getDateInstance().format(new Date());
        Integer hist_Dur = ((Globals) this.getApplication()).getHistory().getDuration();
        Integer hist_Intv = ((Globals) this.getApplication()).getHistory().getInterval();

        // textView is the TextView view that should display it
        final TextView currDate = (TextView) findViewById(R.id.currentDate);
        TextView currDuration = (TextView) findViewById(R.id.duration);
        TextView currInterval = (TextView) findViewById(R.id.frequency);
        currDate.setText(currentDateTimeString);
        currDuration.setText(String.valueOf(hist_Dur));
        currInterval.setText(String.valueOf(hist_Intv));

        final TextView currTime = (TextView) findViewById(R.id.remainder);

        new CountDownTimer(hist_Dur*3600*1000,1000){
            @Override
            public void onTick(long millisUntilFinished) {
                currTime.setText("" + millisUntilFinished / 1000);
            }

            @Override
            public void onFinish() {
                currTime.setText("done!");
            }
        }.start();

//        int receiveValue = getIntent().getIntExtra("duration", 0);
//        TextView duration = (TextView) findViewById(R.id.historyDuration);
//        duration.setText(receiveValue);
    }
}
