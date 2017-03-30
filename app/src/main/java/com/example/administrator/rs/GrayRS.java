package com.example.administrator.rs;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.renderscript.Allocation;
import android.renderscript.RenderScriptGL;
import android.renderscript.ScriptC;
import android.widget.ImageView;


/**
 * Created by mao on 17-3-14.
 */
public class GrayRS {

    RenderScriptGL renderScriptGL;

    ScriptC_gray scriptC_gray;
    private Allocation inputAllocation;
    private Allocation outputAllocation;
    private Bitmap sourceBitmap;
    private Bitmap destBitmap;

    public GrayRS(RenderScriptGL renderScriptGL, Resources resources) {
        this.renderScriptGL = renderScriptGL;
        scriptC_gray = new ScriptC_gray(renderScriptGL, resources, R.raw.gray);
        this.renderScriptGL.bindRootScript(scriptC_gray);

        sourceBitmap = BitmapFactory.decodeResource(resources, R.drawable.scene);
        destBitmap = Bitmap.createBitmap(sourceBitmap.getWidth(), sourceBitmap.getHeight(), Bitmap.Config.ARGB_8888);

        inputAllocation = Allocation.createFromBitmap(this.renderScriptGL, sourceBitmap);

        scriptC_gray.set_inputAllocation(inputAllocation);//这句代码必须的
        outputAllocation = Allocation.createFromBitmap(this.renderScriptGL, destBitmap);
    }


    /**
     * 应该在更新完位置等信息后再调用
     *
     * @param imageView
     */
    public void setMagniferImage(ImageView imageView) {
        scriptC_gray.forEach_magnify(inputAllocation, outputAllocation);
        // l.e("输出到dest!");
        outputAllocation.copyTo(destBitmap);
        // l.e("添加iv!");
        imageView.setImageBitmap(destBitmap);
    }
}
