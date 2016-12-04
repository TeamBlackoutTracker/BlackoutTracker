package com.example.myfirstapp;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.CountDownTimer;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.io.File;
import java.text.DateFormat;
import java.util.Date;
import android.app.AlertDialog;
import android.widget.Toast;

import static android.os.Environment.getExternalStorageDirectory;

public class HistoryInformation extends AppCompatActivity {
    public static final int MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE = 1;
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

        File destination = new File(getExternalStorageDirectory() + "/BlackoutTracker/" + currentDateTimeString);
        destination.mkdirs();
        //mkFolder(currentDateTimeString);
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

    public int mkFolder(String folderName){ // make a folder under Environment.DIRECTORY_DCIM
        String state = Environment.getExternalStorageState();
        if (!Environment.MEDIA_MOUNTED.equals(state)){
            Log.d("myAppName", "Error: external storage is unavailable");
            return 0;
        }
        if (Environment.MEDIA_MOUNTED_READ_ONLY.equals(state)) {
            Log.d("myAppName", "Error: external storage is read only.");
            return 0;
        }
        Log.d("myAppName", "External storage is not read only or unavailable");

        if (ContextCompat.checkSelfPermission(this, // request permission when it is not granted.
                Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            Log.d("myAppName", "permission:WRITE_EXTERNAL_STORAGE: NOT granted!");
            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE)) {

                // Show an expanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.

            } else {

                // No explanation needed, we can request the permission.

                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE);

                // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                // app-defined int constant. The callback method gets the
                // result of the request.
            }
        }
        //File folder = new File(Environment.getExternalStoragePublicDirectory(Environment.DOCUMENTS),folderName);
        File folder = new File(Environment.getRootDirectory() + "/BlackoutTracker",folderName);
        int result = 0;
        if (folder.exists()) {
            Log.d("myAppName","folder exist:"+folder.toString());
            result = 2; // folder exist
        }else{
            try {
                if (folder.mkdir()) {
                    Log.d("myAppName", "folder created:" + folder.toString());
                    result = 1; // folder created
                } else {
                    Log.d("myAppName", "creat folder fails:" + folder.toString());
                    result = 0; // creat folder fails
                }
            }catch (Exception ecp){
                ecp.printStackTrace();
            }
        }
        return result;
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
