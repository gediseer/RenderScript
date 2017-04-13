package com.example.administrator.rs;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.renderscript.Allocation;
import android.renderscript.RenderScript;
import android.renderscript.RenderScriptGL;

import android.os.Bundle;
import android.renderscript.ScriptIntrinsicBlur;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.TextView;

import java.util.Random;

public class MainActivity extends Activity {
    ImageView imageView;
    MagnifierRS magnifierRS;
    GrayRS grayRS;
    NostalgicRS nostalgicRS;
    InvertRS invertRS;
    LightRS lightRS;
    ReliefRS reliefRS;
    AtomizationRS atomizationRS;
    BlurRS blurRS;


    int type = 0;
    ImageView imageViewApi;
    SeekBar radius;
    SeekBar weight;
    TextView rs;
    TextView ws;
    public ImageView imageViewMopi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        imageView = ((ImageView) findViewById(R.id.imageView));
        imageViewApi = ((ImageView) findViewById(R.id.apiBlur));
        imageViewMopi = ((ImageView) findViewById(R.id.mopi));

        weight = ((SeekBar) findViewById(R.id.weight));
        radius = ((SeekBar) findViewById(R.id.radius));
        rs = ((TextView) findViewById(R.id.radiusShow));
        ws = ((TextView) findViewById(R.id.weightShow));

        weight.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                ws.setText(String.valueOf(weight.getProgress()));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        radius.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                rs.setText(String.valueOf(radius.getProgress()));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        magnifierRS = new MagnifierRS(new RenderScriptGL(this, new RenderScriptGL.SurfaceConfig()), getResources());
        grayRS = new GrayRS(new RenderScriptGL(this, new RenderScriptGL.SurfaceConfig()), getResources());
        nostalgicRS = new NostalgicRS(new RenderScriptGL(this, new RenderScriptGL.SurfaceConfig()), getResources());
        invertRS = new InvertRS(new RenderScriptGL(this, new RenderScriptGL.SurfaceConfig()), getResources());
        lightRS = new LightRS(new RenderScriptGL(this, new RenderScriptGL.SurfaceConfig()), getResources());
        reliefRS = new ReliefRS(new RenderScriptGL(this, new RenderScriptGL.SurfaceConfig()), getResources());
        atomizationRS = new AtomizationRS(new RenderScriptGL(this, new RenderScriptGL.SurfaceConfig()), getResources());
        blurRS = new BlurRS(new RenderScriptGL(this, new RenderScriptGL.SurfaceConfig()), getResources());

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
                    case 5:
                        reliefRS.setMagniferImage(imageView);
                        break;
                    case 6:
                        atomizationRS.setMagniferImage(imageView);
                        break;
                    case 7:
                        blurRS.setMagniferImage(imageViewApi, MainActivity.this);
                        break;
                    default:
                        imageView.setImageBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.scene));
                        break;

                }
                if (type >= 8) {
                    type = 0;
                } else {
                    type++;
                }

            }
        });
        imageViewApi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {//设置点击进行表面模糊算法
                Bitmap overlay = BitmapFactory.decodeResource(getResources(), R.drawable.g);
                blurRS.setRadius(radius.getProgress());
                blurRS.setWeight(weight.getProgress());
                blurRS.setMagniferImage(imageViewApi, MainActivity.this);
            }
        });
        imageViewMopi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {//设置点击进行模糊算法
                Bitmap overlay = BitmapFactory.decodeResource(getResources(), R.drawable.g);
                RenderScript rs = RenderScript.create(MainActivity.this);
                Allocation overlayAlloc = Allocation.createFromBitmap(rs, overlay);

                ScriptIntrinsicBlur blur = ScriptIntrinsicBlur.create(rs, overlayAlloc.getElement());
                blur.setInput(overlayAlloc);
                blur.setRadius(6);
                blur.forEach(overlayAlloc);
                overlayAlloc.copyTo(overlay);
                view.setBackground(new BitmapDrawable(getResources(), overlay));
                rs.destroy();
            }
        });
    }
}
