package com.example.myfirstapp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ActivityManager;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.drawable.Drawable;
import android.hardware.Camera;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.OrientationEventListener;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.io.IOException;
import java.util.List;

public class ARPAFormActivity extends AppCompatActivity {

    TextView placeholder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_arpaform);
        getSiteName();

        //Some stuff to make the USACE logo always appear (makes it look a little nicer and shows where the photo will appear on the form
        if (imageHolder == null){
            ImageView imagePlaceHolder = findViewById(R.id.imageARPA_hidden);
            imagePlaceHolder.setVisibility(View.VISIBLE);
        }
    }
    private ImageView imageHolder;

    //HEY Y'ALL! Button here! Please don't delete me because if you do your code to take photos will crash after the first photo! THANKS PAST JOSH!
    Button btnStop;

    public void actionPhoto(View view) {
        //Hey it works! Please don't mess with this!!!! IT WORKS BUT JUST BARELY PLEASE HELP ME.
        btnStop = findViewById(R.id.btnDltPhoto);
        btnStop.setEnabled(false);
        final TextView textView = findViewById(R.id.textView2);
        textView.setVisibility(View.VISIBLE);
        final Button takePictureButton = findViewById(R.id.btnPhoto);
        imageHolder = findViewById(R.id.imageARPA);
        takePictureButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                if (takePictureIntent.resolveActivity(getPackageManager())!=null){
                    startActivityForResult(takePictureIntent, REQUEST_PERMISSIONS);
                    textView.setVisibility(View.GONE);
                }
            }
        });
    }

    //Displays the captured picture after it is taken
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        //Asks for Permissions
        super.onActivityResult(requestCode, resultCode, data);
        if(this.REQUEST_PERMISSIONS == requestCode && resultCode == RESULT_OK){
            Bitmap bitmap = (Bitmap)data.getExtras().get("data");
            int width = bitmap.getWidth();
            int height = bitmap.getHeight();


            //SOMETHING IN HERE CRASHES THE APP IF YOU TAKE PICTURES TOO MANY TIMES. BtnStop saves this method from crashing my app.
            Bitmap resizedBitmap = getResizedBitmap(bitmap, width, height);
            imageHolder.setImageBitmap(resizedBitmap);
            btnStop.setEnabled(true);
        }
    }

    //Takes the picture we took and makes it a bitmap and puts it in the .xml for us.

    private Bitmap getResizedBitmap(Bitmap bitmap, int width, int height) {
        float scaleWidth = ((float) 512) / width;
        float scaleHeight = ((float) 480) / height;
        // CREATE A MATRIX FOR THE MANIPULATION
        Matrix matrix = new Matrix();
        // RESIZE THE BIT MAP
        matrix.postScale(scaleWidth, scaleHeight);

        // "RECREATE" THE NEW BITMAP
        Bitmap resizedBitmap = Bitmap.createBitmap(
                bitmap, 0, 0, width, height, matrix, false);
        bitmap.recycle();
        return resizedBitmap;
    }



    public void actionClose(View view) {

        //Closes the form page activity.

        String actionCloseString2 = "Are you sure you want to close this form?";
        String actionCloseString = "Close Site Visit Form?";
        showAddItemDialog(ARPAFormActivity.this, actionCloseString, actionCloseString2);
    }

    public void actionDltPhoto(View view){
        if(imageHolder != null){
        String actionDeleteString2 = "Are you sure you want to delete this?      THIS ACTION CANNOT BE UNDONE!";
        String actionDeleteString = "PERMANENTLY Delete Photo from Form?";
        showDltDialog(ARPAFormActivity.this, actionDeleteString, actionDeleteString2);
        }else{
            String actionNullString = "Nothing to Delete!";
            String actionNullString2 = "You haven't taken a Picture yet!";
            showNullDialog(ARPAFormActivity.this, actionNullString, actionNullString2);
            /*CustomDialogClass cdd=new CustomDialogClass(ARPAFormActivity.this);
            cdd.show();
             */
        }
    }

    //TO DO : ...Actually I don't know what to do here.
    public void actionSubmit(View view) {

        String actionSubmitString = "Are you sure you want to submit this form?";
        String actionSubmitString2 = "Submit Site Visit Form?";
        showAddItemDialog(ARPAFormActivity.this, actionSubmitString, actionSubmitString2);
    }

    //Creates a Dialog box for.... I forget. I think for the ActionSubmit and ActionClose methods.
    public void showAddItemDialog(Context c, String x, String y) {
        AlertDialog dialog = new AlertDialog.Builder(c)
                .setTitle(x)
                .setMessage(y)
                .setPositiveButton("YES", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        setContentView(R.layout.activity_arpaform);
                        finish();
                        startActivity(new Intent(ARPAFormActivity.this, DisplayMessageActivity.class));
                    }
                })
                .setNegativeButton("NO", null)
                .create();
        dialog.show();
    }

    //Method to call a Dialog to do... NOTHING
    public void showNullDialog(Context c, String x, String y) {
        AlertDialog dialog = new AlertDialog.Builder(c)
                .setTitle(x)
                .setMessage(y)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //Does Nothing. It's null LUL
                    }
                })
                .create();
        dialog.show();
    }

    //Method to call a Dialog to check if imageHolder has an image, and if it does to KILL IT. (Also disable btnDlt because IT KEEPS CRASHING MY APP!!!)
    public void showDltDialog(Context c, String x, String y) {
        AlertDialog dialog = new AlertDialog.Builder(c)
                .setTitle(x)
                .setMessage(y)
                .setPositiveButton("DELETE", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (imageHolder != null) {
                            imageHolder.setImageBitmap(null);
                            getSiteName();
                            btnStop.setEnabled(false);
                        }
                    }
                })
                .setNegativeButton("CANCEL", null)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .create();
        dialog.show();
    }

    //Method to get site name from DisplayMessageActivity.java - this likes to get reset so I have this method repeat sometimes at random in the code so it doesn't disappear
    public void getSiteName(){
        placeholder=findViewById(R.id.siteName);
        placeholder.setText(getIntent().getStringExtra("message"));

        //Makes sure that the USACE logo is always displayed if no bitmap is visible
        ImageView imageVisible = findViewById(R.id.imageARPA);
        imageVisible.setImageResource(R.drawable.usace);
    }


    /*CUSTOM DIALOG CODE

    public class CustomDialogClass extends Dialog implements
            android.view.View.OnClickListener {

        public Activity c;
        public Dialog d;
        public Button yes, no;

        public CustomDialogClass(Activity a) {
            super(a);
            // TODO Auto-generated constructor stub
            this.c = a;
        }

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            requestWindowFeature(Window.FEATURE_NO_TITLE);
            setContentView(R.layout.custom_alert_dialog);
            yes = (Button) findViewById(R.id.btn_yes);
            yes.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                default:
                    break;
            }
            dismiss();
        }
    }
*/

    //START OF CAMERA CODE
    /*SOOOO I followed a tutorial and got really far with it! Before realizing that a LOT of the code
    here was useless for my project... BUT I'm not sure what code here DOES and doesn't break it the app so in it shall remain!
     */
    private static final String[] PERMISSIONS = {
            Manifest.permission.CAMERA
    };
    private static final int REQUEST_PERMISSIONS = 34;
    private static final int PERMISSIONS_COUNT = 1;

    //SuppressLint suppresses an error we'd get for using clearApplicationUserData because its not found below API 15 but
    //we're only checking for permissions on APIs 23 or above so it doesn't matter

    //***Jacob*** Review this
    @SuppressLint("NewApi")
    private boolean areaPermissionsDenied(){
        for(int i = 0; i < PERMISSIONS_COUNT; i++){
            if(checkSelfPermission(PERMISSIONS[i])!= PackageManager.PERMISSION_GRANTED){
                return true;
            }
        }
        return false;
    }

    //SuppressLint suppresses an error we'd get for using clearApplicationUserData because its not found below API 15 but
    //we're only checking for permissions on APIs 23 or above so it doesn't matter
    @SuppressLint("NewApi")
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults){
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode == REQUEST_PERMISSIONS && grantResults.length > 0){
            if(areaPermissionsDenied()) {
                ((ActivityManager)(this.getSystemService(ACTIVITY_SERVICE))).clearApplicationUserData();
                recreate();
            }else{

            }
        }
    }

    //SO a LOT of this code is useless but right now I'm afraid I'll break it if I remove anything so instead of trying to
    // make it prettier I'm just going to leave everything here and say it's fine
    private boolean isCameraInitialized;

    private Camera mCamera = null;

    private static SurfaceHolder myHolder;

    private static ARPAFormActivity.CameraPreview mPreview;

    private FrameLayout preview;

    private Button flashB;

    private static OrientationEventListener orientationEventListener = null;

    private static boolean fM;

    @Override
    protected void onResume(){
        super.onResume();
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && areaPermissionsDenied()){
            requestPermissions(PERMISSIONS, REQUEST_PERMISSIONS);
            return;
        }
        if(!isCameraInitialized){
            mCamera = Camera.open();
            mPreview = new ARPAFormActivity.CameraPreview(this, mCamera);
            preview = findViewById(R.id.camera_preview);
            preview.addView(mPreview);


            rotateCamera();
            flashB = findViewById(R.id.flash);
            if(hasFlash()){
                flashB.setVisibility(View.GONE);
            }else{
                flashB.setVisibility(View.GONE);
            }


            final Button switchCameraButton = findViewById(R.id.switchCamera);
            switchCameraButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mCamera.release();
                    switchCamera();
                    rotateCamera();
                    try{
                        mCamera.setPreviewDisplay(myHolder);
                    }catch(Exception e) {

                    }
                    mCamera.startPreview();
                    if(hasFlash()){
                        flashB.setVisibility(View.GONE);
                    }else{
                        flashB.setVisibility(View.GONE);
                    }
                }
            });
            orientationEventListener = new OrientationEventListener(this) {
                @Override
                public void onOrientationChanged(int i) {
                    rotateCamera();
                }
            };
            orientationEventListener.enable();
            preview.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    if(whichCamera){
                        if(fM){
                            p.setFocusMode(Camera.Parameters.FOCUS_MODE_CONTINUOUS_PICTURE);
                        }else{
                            p.setFocusMode(Camera.Parameters.FOCUS_MODE_AUTO);
                        }
                        try{
                            mCamera.setParameters(p);
                        }catch(Exception e){

                        }
                        fM = !fM;
                    }
                    return true;
                }
            });
        }
    }

    private void switchCamera(){
        if(whichCamera){
            mCamera = Camera.open(Camera.CameraInfo.CAMERA_FACING_FRONT);
        }else{
            mCamera = Camera.open();
        }
        whichCamera = !whichCamera;
    }

    @Override
    protected void onPause(){
        super.onPause();
        releaseCamera();
    }

    private void releaseCamera(){
        if(mCamera != null){
            preview.removeView(mPreview);
            mCamera.release();
            orientationEventListener.disable();
            mCamera = null;
            whichCamera = !whichCamera;
        }
    }

    private static List<String> camEffects;

    private static boolean hasFlash(){
        camEffects = p.getSupportedColorEffects();
        final List<String> flashModes = p.getSupportedFlashModes();
        if(flashModes == null){
            return false;
        }

        for(String flashMode:flashModes){
            if(Camera.Parameters.FLASH_MODE_ON.equals(flashMode)){
                return true;
            }
        }
        return false;
    }

    private static int rotation;

    private static boolean whichCamera = true;

    private static Camera.Parameters p;

    private void rotateCamera(){
        if(mCamera != null){
            rotation = this.getWindowManager().getDefaultDisplay().getRotation();
            if(rotation == 0){
                rotation = 90;
            }else if(rotation == 1){
                rotation = 0;
            }else if(rotation == 2){
                rotation = 270;
            }else if(rotation == 3){
                rotation = 180;
            }
            mCamera.setDisplayOrientation(rotation);
            if(!whichCamera){
                if(rotation == 90){
                    rotation = 270;
                }else if(rotation ==270){
                    rotation = 90;
                }
            }
            p = mCamera.getParameters();
            p.setRotation(rotation);
            mCamera.setParameters(p);
        }
    }
    private static class CameraPreview extends SurfaceView implements SurfaceHolder.Callback{
        private static SurfaceHolder mHolder;
        private static Camera mCamera;

        private CameraPreview(Context context, Camera camera){
            super(context);
            mCamera = camera;
            mHolder = getHolder();
            mHolder.addCallback(this);
            mHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);

        }
        public void surfaceCreated(SurfaceHolder holder){
            myHolder = holder;
            try{
                mCamera.setPreviewDisplay(holder);
                mCamera.startPreview();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        public void surfaceDestroyed(SurfaceHolder holder){

        }
        public void surfaceChanged(SurfaceHolder holder, int format, int w, int h){

        }
    }

}
