package com.example.administrator.rs;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.renderscript.Allocation;
import android.renderscript.RenderScriptGL;
import android.view.MotionEvent;
import android.widget.ImageView;


/**
 * Created by mao on 17-3-14.
 */
public class LightRS {

    RenderScriptGL renderScriptGL;
    ScriptC_light scriptC_light;
    private Allocation inputAllocation;
    private Allocation outputAllocation;
    private Bitmap sourceBitmap;
    private Bitmap destBitmap;

    public LightRS(RenderScriptGL renderScriptGL, Resources resources) {
        this.renderScriptGL = renderScriptGL;
        scriptC_light = new ScriptC_light(renderScriptGL, resources, R.raw.light);
        this.renderScriptGL.bindRootScript(scriptC_light);

        sourceBitmap = BitmapFactory.decodeResource(resources, R.drawable.scene);
        destBitmap = Bitmap.createBitmap(sourceBitmap.getWidth(), sourceBitmap.getHeight(), Bitmap.Config.ARGB_8888);

        inputAllocation = Allocation.createFromBitmap(this.renderScriptGL, sourceBitmap);

        scriptC_light.set_inputAllocation(inputAllocation);//这句代码必须的
        outputAllocation = Allocation.createFromBitmap(this.renderScriptGL, destBitmap);
    }


    /**
     * 应该在更新完位置等信息后再调用
     *
     * @param imageView
     */
    public void setMagniferImage(ImageView imageView, MotionEvent event) {
        if (event == null) {
            scriptC_light.set_atX(100);//指定默认光源中心
            scriptC_light.set_atY(100);

        } else {
            scriptC_light.set_atX(((int) event.getX()));
            scriptC_light.set_atY(((int) event.getY()));
        }

        //scriptC_light.set_scale(2);
        scriptC_light.set_radius(60);
        scriptC_light.set_light(90);//设置光照强度
        scriptC_light.forEach_magnify(inputAllocation, outputAllocation);
        // l.e("输出到dest!");
        outputAllocation.copyTo(destBitmap);
        // l.e("添加iv!");
        imageView.setImageBitmap(destBitmap);
    }
}
