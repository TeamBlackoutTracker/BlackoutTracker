package com.example.myfirstapp;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import java.text.DateFormat;
import java.util.Date;
import android.app.AlertDialog;
import android.widget.Toast;

public class HistoryInformation extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        ((Globals) this.getApplication()).setDurationFin(false);

        String currentDateTimeString = DateFormat.getDateInstance().format(new Date());
        Integer hist_Dur = ((Globals) this.getApplication()).getHistory().getDuration();
        final Integer hist_Intv = ((Globals) this.getApplication()).getHistory().getInterval();

        // textView is the TextView view that should display it
        final TextView currDate = (TextView) findViewById(R.id.currentDate);
        TextView currDuration = (TextView) findViewById(R.id.history_duration);
        TextView currInterval = (TextView) findViewById(R.id.frequency);
        currDate.setText(currentDateTimeString);

        if(hist_Dur == 1) {
            currDuration.setText(String.valueOf(hist_Dur) + " min");
        }else{
            currDuration.setText(String.valueOf(hist_Dur) + " mins");
        }

        if(hist_Intv == 1) {
            currInterval.setText(String.valueOf(hist_Intv) + " min");
        }else {
            currInterval.setText(String.valueOf(hist_Intv) + " mins");
        }

        final TextView currTime = (TextView) findViewById(R.id.remainder);

        new CountDownTimer(hist_Dur*3600*1000,1000){
            @Override
            public void onTick(long millisUntilFinished) {
                long seconds = millisUntilFinished / 1000;
                currTime.setText(String.format("%02d:%02d", (seconds % 3600) / 60, (seconds % 60)));
            }

            @Override
            public void onFinish() {
                currTime.setText("Finished");
                ((Globals) getApplication()).setDurationFin(true);
                this.cancel();
            }
        }.start();

        final TextView currInt = (TextView) findViewById(R.id.nextint);

        new CountDownTimer(hist_Intv*60*1000,1000){
            @Override
            public void onTick(long millisUntilFinished) {
                long seconds = millisUntilFinished / 1000;
                currInt.setText(String.format("%02d:%02d", (seconds % 3600) / 60, (seconds % 60)));

                if(currTime.getText() == "Finished"){
                    finish();
                }
            }

            @Override
            public void onFinish() {
                if( ((Globals) getApplication()).getDurationFin() == false ) {
                    Intent intent = new Intent(HistoryInformation.this, CaptureMedia.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                    startActivityForResult(intent, 2);
                    this.cancel();
                    this.start();
                }
                else{
                    Intent intent = new Intent(HistoryInformation.this, HomeScreen.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                    startActivity(intent);
                    this.cancel();
                    finish();
                }
            }
        }.start();
    }

    @Override
    public void onBackPressed() {
    }

    public void end_history(View view){
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage("Are you sure, you want to end current history?");

        //alertDialogBuilder.setSingleChoiceItems()
        alertDialogBuilder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(HistoryInformation.this, HomeScreen.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                startActivity(intent);
                finish();
            }
        });

        alertDialogBuilder.setNegativeButton("No",new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface arg0, int arg1) {
                Toast.makeText(HistoryInformation.this,"Continuing History", Toast.LENGTH_LONG).show();
            }
        });

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

}
