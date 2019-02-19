package com.FriendlyUser.flappyPlane;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.opengl.GLSurfaceView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

   private GLSurfaceView glSurfaceView() {
        return (GLSurfaceView)this.findViewById(R.id.glview);
   }

    @Override
    protected void onPause() {
        super.onPause();
        this.glSurfaceView().onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        this.glSurfaceView().onResume();
    }
}