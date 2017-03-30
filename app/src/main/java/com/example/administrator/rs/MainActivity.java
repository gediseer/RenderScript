package com.example.administrator.rs;

import android.app.Activity;
import android.graphics.BitmapFactory;
import android.renderscript.RenderScript;
import android.renderscript.RenderScriptGL;

import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;

public class MainActivity extends Activity {
    ImageView imageView;
    MagnifierRS magnifierRS;
    GrayRS grayRS;
    NostalgicRS nostalgicRS;
    InvertRS invertRS;
    LightRS lightRS;
    int type = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        imageView = ((ImageView) findViewById(R.id.imageView));

        magnifierRS = new MagnifierRS(new RenderScriptGL(this, new RenderScriptGL.SurfaceConfig()), getResources());
        grayRS = new GrayRS(new RenderScriptGL(this, new RenderScriptGL.SurfaceConfig()), getResources());
        nostalgicRS = new NostalgicRS(new RenderScriptGL(this, new RenderScriptGL.SurfaceConfig()), getResources());
        invertRS = new InvertRS(new RenderScriptGL(this, new RenderScriptGL.SurfaceConfig()), getResources());
        lightRS = new LightRS(new RenderScriptGL(this, new RenderScriptGL.SurfaceConfig()), getResources());

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                l.e("click 了");
                switch (type) {
                    case 0:
                        invertRS.setMagniferImage(imageView);
                        break;
                    case 1:
                        grayRS.setMagniferImage(imageView);
                        break;
                    case 2:
                        nostalgicRS.setMagniferImage(imageView);
                        break;
                    case 3:
                        magnifierRS.setMagniferImage(imageView, null);
                        break;
                    case 4:
                        lightRS.setMagniferImage(imageView, null);
                        break;
                    default:
                        imageView.setImageBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.scene));
                        break;


                }
                if (type >= 5) {
                    type = 0;
                } else {
                    type++;
                }

            }
        });
        imageView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                l.e("touch 了");
                switch (type) {
                    case 0:
                        //invertRS.setMagniferImage(imageView);
                        break;
                    case 1:
                        // grayRS.setMagniferImage(imageView);
                        break;
                    case 2:
                        //  nostalgicRS.setMagniferImage(imageView);
                        break;
                    case 3:
                        magnifierRS.setMagniferImage(imageView, motionEvent);
                        break;
                    case 4:
                        lightRS.setMagniferImage(imageView, motionEvent);
                        break;
                    default:
                        imageView.setImageBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.scene));
                        break;


                }


                return false;
            }
        });
    }
}
