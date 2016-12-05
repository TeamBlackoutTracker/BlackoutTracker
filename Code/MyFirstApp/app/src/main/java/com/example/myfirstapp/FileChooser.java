package com.example.myfirstapp;

/**
 * Created by robsh on 2016-12-03.
 */

import java.io.File;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.text.DateFormat;

import android.net.Uri;
import android.os.Bundle;
import android.app.ListActivity;
import android.content.Intent;
import android.os.Environment;
import android.view.View;
import android.widget.ListView;

import static android.os.Environment.getExternalStorageDirectory;

public class FileChooser extends ListActivity {

    private File currentDir;
    private FileArrayAdapter adapter;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        File currentDir = new File(Environment.getExternalStorageDirectory(),"BlackoutTracker");
        fill(currentDir);
    }
    private void fill(File f)
    {
        File[]dirs = f.listFiles();
        this.setTitle("Current Dir: "+f.getName());
        List<Item>dir = new ArrayList<Item>();
        List<Item>fls = new ArrayList<Item>();
        try{
            for(File ff: dirs)
            {
                Date lastModDate = new Date(ff.lastModified());
                DateFormat formater = DateFormat.getDateTimeInstance();
                String date_modify = formater.format(lastModDate);
                if(ff.isDirectory()){


                    File[] fbuf = ff.listFiles();
                    int buf = 0;
                    if(fbuf != null){
                        buf = fbuf.length;
                    }
                    else buf = 0;
                    String num_item = String.valueOf(buf);
                    if(buf == 0) num_item = num_item + " item";
                    else num_item = num_item + " items";

                    //String formated = lastModDate.toString();
                    dir.add(new Item(ff.getName(),num_item,date_modify,ff.getAbsolutePath(),"directory_icon"));
                }
                else
                {
                    if(ff.getName().toLowerCase().endsWith(".jpg")){
                        fls.add(new Item(ff.getName(),ff.length() + " Byte", date_modify, ff.getAbsolutePath(),"img_icon"));
                    }
                    else if(ff.getName().toLowerCase().endsWith(".mp4")){
                        fls.add(new Item(ff.getName(),ff.length() + " Byte", date_modify, ff.getAbsolutePath(),"vid_icon"));
                    }
                    else if(ff.getName().toLowerCase().endsWith(".txt")){
                        fls.add(new Item(ff.getName(),ff.length() + " Byte", date_modify, ff.getAbsolutePath(),"txt_icon"));
                    }
                    else if(ff.getName().toLowerCase().endsWith(".mp3")){
                        fls.add(new Item(ff.getName(),ff.length() + " Byte", date_modify, ff.getAbsolutePath(),"aud_icon"));
                    }
                    else {
                        fls.add(new Item(ff.getName(),ff.length() + " Byte", date_modify, ff.getAbsolutePath(),"file_icon"));
                    }
                }
            }
        }catch(Exception e)
        {

        }
        Collections.sort(dir);
        Collections.sort(fls);
        dir.addAll(fls);
        if(!f.getName().equalsIgnoreCase("sdcard"))
            dir.add(0,new Item("Browse Your Existing Histories",f.getName(),"",f.getParent(),"directory_up"));
        adapter = new FileArrayAdapter(FileChooser.this,R.layout.file_view,dir);
        this.setListAdapter(adapter);
    }
    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        // TODO Auto-generated method stub
        super.onListItemClick(l, v, position, id);
        Item o = adapter.getItem(position);
        if(o.getImage().equalsIgnoreCase("directory_icon")){
            currentDir = new File(o.getPath());
            fill(currentDir);
        }
        else if(o.getImage().equalsIgnoreCase("directory_up")){
            if(o.getPath().endsWith("/0")) {
                Intent intent = new Intent(this, HomeScreen.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                startActivity(intent);
            }
            else {
                currentDir = new File(o.getPath());
                fill(currentDir);
            }
        }
        else
        {
            onFileClick(o);
        }
    }
    private void onFileClick(Item o)
    {
        File url = new File(o.getPath());
        Uri uri = Uri.fromFile(url);

        Intent intent = new Intent(Intent.ACTION_VIEW);
        if (url.toString().contains(".jpg")) {
            // IMAGE
            intent.setDataAndType(uri, "image/jpeg");
        }
        else if (url.toString().contains(".mp4")) {
            // VIDEO
            intent.setDataAndType(uri, "video/*");
        }
        else if (url.toString().contains(".txt")) {
            // TEXT
            intent.setDataAndType(uri, "text/plain");
        }
        else if (url.toString().contains(".mp3")) {
            // VOICE
            intent.setDataAndType(uri, "audio/x-wav");
        }
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
}