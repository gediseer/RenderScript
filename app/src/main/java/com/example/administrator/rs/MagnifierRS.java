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
public class MagnifierRS {

    RenderScriptGL renderScriptGL;
    ScriptC_magnifier scriptC_magnifier;
    private Allocation inputAllocation;
    private Allocation outputAllocation;
    private Bitmap sourceBitmap;
    private Bitmap destBitmap;

    public MagnifierRS(RenderScriptGL renderScriptGL, Resources resources) {
        this.renderScriptGL = renderScriptGL;
        scriptC_magnifier = new ScriptC_magnifier(renderScriptGL, resources, R.raw.magnifier);
        this.renderScriptGL.bindRootScript(scriptC_magnifier);

        sourceBitmap = BitmapFactory.decodeResource(resources, R.drawable.scene);
        destBitmap = Bitmap.createBitmap(sourceBitmap.getWidth(), sourceBitmap.getHeight(), Bitmap.Config.ARGB_8888);

        inputAllocation = Allocation.createFromBitmap(this.renderScriptGL, sourceBitmap);

        scriptC_magnifier.set_inputAllocation(inputAllocation);//这句代码必须的
        outputAllocation = Allocation.createFromBitmap(this.renderScriptGL, destBitmap);
    }


    /**
     * 应该在更新完位置等信息后再调用
     *
     * @param imageView
     */
    public void setMagniferImage(ImageView imageView, MotionEvent event) {
        if (event == null) {
            scriptC_magnifier.set_atX(100);
            scriptC_magnifier.set_atY(100);

        } else {
            scriptC_magnifier.set_atX(((int) event.getX()));
            scriptC_magnifier.set_atY(((int) event.getY()));
        }

        scriptC_magnifier.set_scale(2);
        scriptC_magnifier.set_radius(60);
        scriptC_magnifier.forEach_magnify(inputAllocation, outputAllocation);
        // l.e("输出到dest!");
        outputAllocation.copyTo(destBitmap);
        // l.e("添加iv!");
        imageView.setImageBitmap(destBitmap);
    }
}
