package com.example.wisebridge;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.webkit.WebView;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.util.Timer;
import java.util.TimerTask;
import java.util.Timer;
import java.util.TimerTask;
public class MainActivity extends AppCompatActivity {
    Timer timer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //WebView webView = findViewById(R.id.webView);
        //webView.loadUrl("file:///android_res/drawable/logo4.gif");

       // webView.getSettings().setUserInteractionEnabled(false);
        //webView.setVerticalScrollBarEnabled(false);
        //webView.setHorizontalScrollBarEnabled(false);

        ImageView imageView = findViewById(R.id.imageView);
        Glide.with(this).asGif().load(R.drawable.logo4).into(imageView);

        timer=new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                Intent intent=new Intent(MainActivity.this,Login.class);
                startActivity(intent);
                finish();

            }
        },3200);
    }
}