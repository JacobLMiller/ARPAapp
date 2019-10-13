package com.example.myfirstapp;

import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.OrientationEventListener;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;

import java.io.IOException;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    public static final String EXTRA_MESSAGE = "com.example.myfirstapp.MESSAGE";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void chooseSite(View view){
        //Opens the site menu
        Intent intent = new Intent(this, DisplayMessageActivity.class);
        startActivity(intent);
    }

}
/*
WELCOME TO MY APP! There is a lot of... fun... in here. The Code WORKS. I'm sure it could be done better but it works!... for now.
Followed a lot of tutorials at the start that may or may not have been useful for the project so some code in here is OK but others
don't really need to be there but I'm afraid it will break the app if I remove them. DisplayMessageActivity is a great example of
a big fat mess so be sure to look at that one. ARPAFormActivity is a little cleaner and I at least started knowing what I was doing
again in that one.
 */
