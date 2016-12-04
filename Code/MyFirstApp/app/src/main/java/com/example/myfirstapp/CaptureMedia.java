package com.example.myfirstapp;

import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.google.android.gms.actions.NoteIntents;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static android.os.Environment.getExternalStorageDirectory;

public class CaptureMedia extends AppCompatActivity {
    public static final int ACTIVITY_RECORD_SOUND = 0;

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
                File destination = new File(getExternalStorageDirectory() + "/BlackoutTracker", photoFile.getName());
                //Uri photoURI = FileProvider.getUriForFile(this, "com.example.android.fileprovider", photoFile);
                if (!destination.exists()) {
                    destination.mkdirs();
                    destination.mkdirs();
                }
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
                File destination = new File(getExternalStorageDirectory() + "/BlackoutTracker", videoFile.getName());
                //Uri photoURI = FileProvider.getUriForFile(this, "com.example.android.fileprovider", photoFile);
                if (!destination.exists()) {
                    destination.mkdirs();
                    destination.mkdirs();
                }
                intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(destination));
                startActivityForResult(intent, 1);
                intent = new Intent(this, HistoryInformation.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT);
                super.onBackPressed();
            }
        }
    }

    public void mediaText(String subject, String text) {
        Intent intent = new Intent(NoteIntents.ACTION_CREATE_NOTE)
                .putExtra(NoteIntents.EXTRA_NAME, subject)
                .putExtra(NoteIntents.EXTRA_NAME, text);
        if(intent.resolveActivity(getPackageManager()) != null) {
            File textFile = null;
            try {
                textFile = createTextFile();
            } catch (IOException ex) {
                //
            }
            if (textFile != null) {
                File destination = new File(getExternalStorageDirectory() + "/BlackoutTracker", textFile.getName());
                //Uri photoURI = FileProvider.getUriForFile(this, "com.example.android.fileprovider", photoFile);
                if (!destination.exists()) {
                    destination.mkdirs();
                    destination.mkdirs();
                }
                intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(destination));
                startActivityForResult(intent, 1);
                intent = new Intent(this, HistoryInformation.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT);
                super.onBackPressed();
            }
        }
    }

    public void mediaVoice(View view) {
        Intent intent = new Intent(MediaStore.Audio.Media.RECORD_SOUND_ACTION);
        if(intent.resolveActivity(getPackageManager()) != null) {
            File voiceFile = null;
            try {
                voiceFile = createVoiceFile();
            } catch (IOException ex) {
                //
            }
            if (voiceFile != null) {
                File destination = new File(getExternalStorageDirectory() + "/BlackoutTracker", voiceFile.getName());
                //Uri photoURI = FileProvider.getUriForFile(this, "com.example.android.fileprovider", photoFile);
                if (!destination.exists()) {
                    destination.mkdirs();
                    destination.mkdirs();
                }
                intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(destination));
                startActivityForResult(intent, ACTIVITY_RECORD_SOUND);
                intent = new Intent(this, HistoryInformation.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT);
                super.onBackPressed();
            }
        }
    }

    public File createImageFile() throws IOException {
        String mCurrentPhotoPath;
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmm").format(new Date());
        String imageFileName = timeStamp + "_JPEG";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS);
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
        String videoFileName = timeStamp + "_MP4";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS);
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
        String textFileName = timeStamp + "_TXT";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS);
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
        String voiceFileName = timeStamp + "_MP3";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS);
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