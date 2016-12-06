package com.example.myfirstapp;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.actions.NoteIntents;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import static android.os.Environment.getExternalStorageDirectory;

public class CaptureMedia extends AppCompatActivity {
    String currentDateTimeString = DateFormat.getDateInstance().format(new Date());
    private MediaRecorder mRecorder = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_capture_media);
    }

    @Override
    public void onBackPressed() {
    }

    public void mediaImage(View view) {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if(intent.resolveActivity(getPackageManager()) != null) {
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                //
            }
            if (photoFile != null) {
                File destination = new File(Environment.getExternalStorageDirectory() + "/BlackoutTracker/" + currentDateTimeString , photoFile.getName());
                intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(destination));
                startActivityForResult(intent, 1);
                intent = new Intent(this, HistoryInformation.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT);
                super.onBackPressed();
            }
        }
    }

    public void mediaVideo(View view) {
        Intent intent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
        if(intent.resolveActivity(getPackageManager()) != null) {
            File videoFile = null;
            try {
                videoFile = createVideoFile();
            } catch (IOException ex) {
                //
            }
            if (videoFile != null) {
                File destination = new File(Environment.getExternalStorageDirectory() + "/BlackoutTracker/" + currentDateTimeString, videoFile.getName());
                intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(destination));
                startActivityForResult(intent, 1);
                intent = new Intent(this, HistoryInformation.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT);
                super.onBackPressed();
            }
        }
    }

    public void mediaTextView(View view){
        setContentView(R.layout.activity_text);
    }

public void mediaText(View view) {
    File textFile = null;

    try {
        textFile = createTextFile();
    } catch (IOException ex) {
        //
    }
    if (textFile != null) {
        File destination = new File(Environment.getExternalStorageDirectory() + "/BlackoutTracker/" + currentDateTimeString, textFile.getName());
        EditText mEdit   = (EditText)findViewById(R.id.editText2);
        //Data is user inputted string
        String data = mEdit.getText().toString();
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(destination);
            fos.write(data.getBytes());
            fos.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        catch (Exception e) {
            //
        }
        super.onBackPressed();
    }
}

    public void mediaVoiceView(View view){
        setContentView(R.layout.activity_voice);
        Button rec = (Button) findViewById(R.id.recordButton);
        Button stop = (Button) findViewById(R.id.stopButton);
        rec.setEnabled(true);
        stop.setEnabled(false);
        int permission = ActivityCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO);

// If we don't have permissions, ask user for permissions
        if (permission != PackageManager.PERMISSION_GRANTED) {
            String[] PERMISSIONS_STORAGE = {
                    Manifest.permission.RECORD_AUDIO
            };
            int REQUEST_EXTERNAL_STORAGE = 1;

            ActivityCompat.requestPermissions(
                    this,
                    PERMISSIONS_STORAGE,
                    REQUEST_EXTERNAL_STORAGE
            );
        }
    }

    public void stopRecording(View view) {
        if(mRecorder != null){
            mRecorder.stop();
        }
        super.onBackPressed();
    }

    public void beginRecording(View view) {
        File voiceFile = null;

        try {
            voiceFile = createVoiceFile();
        } catch (IOException ex) {
            //
        }
        if (voiceFile != null) {
            File destination = new File(getExternalStorageDirectory() + "/BlackoutTracker/" + currentDateTimeString, voiceFile.getName());
            startRecording(destination);
        }
    }

    private void startRecording(File file){
        Log.d("DEST:ABS: ", file.getAbsolutePath());

        Button rec = (Button) findViewById(R.id.recordButton);
        Button stop = (Button) findViewById(R.id.stopButton);
        rec.setEnabled(false);
        stop.setEnabled(true);

        if(mRecorder != null){
            mRecorder.release();
        }
        Log.d("AUDIO: ","MediaRecorder");
        mRecorder = new MediaRecorder();
        Log.d("AUDIO: ","setAudioSource");
        mRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        Log.d("AUDIO: ","setOutputFormat");
        mRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        Log.d("AUDIO: ","setAudioEncoder");
        mRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_WB);
        Log.d("AUDIO: ","setOutputFile");
        mRecorder.setOutputFile(file.getAbsolutePath());
        try {
            mRecorder.prepare();
            mRecorder.start();
        } catch (IOException e) {
            //
        }
    }

    public File createImageFile() throws IOException {
        String mCurrentPhotoPath;
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmm").format(new Date());
        String imageFileName = timeStamp;
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        mCurrentPhotoPath = "file:" + image.getAbsolutePath();
        return image;
    }

    public File createVideoFile() throws IOException {
        String mCurrentVideoPath;
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmm").format(new Date());
        String videoFileName = timeStamp;
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File video = File.createTempFile(
                videoFileName,  /* prefix */
                ".mp4",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        mCurrentVideoPath = "file:" + video.getAbsolutePath();
        return video;
    }

    public File createTextFile() throws IOException {
        String mCurrentPhotoPath;
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmm").format(new Date());
        String textFileName = timeStamp;
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File text = File.createTempFile(
                textFileName,  /* prefix */
                ".txt",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        mCurrentPhotoPath = "file:" + text.getAbsolutePath();
        return text;
    }

    public File createVoiceFile() throws IOException {
        String mCurrentPhotoPath;
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmm").format(new Date());
        String voiceFileName = timeStamp;
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File voice = File.createTempFile(
                voiceFileName,  /* prefix */
                ".mp3",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        mCurrentPhotoPath = "file:" + voice.getAbsolutePath();
        return voice;
    }

}