package com.example.administrator.rs;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.renderscript.Allocation;
import android.renderscript.RenderScriptGL;
import android.widget.ImageView;


/**
 * Created by mao on 17-3-14.
 */
public class InvertRS {

    RenderScriptGL renderScriptGL;
    ScriptC_invert scriptC_invert;
    private Allocation inputAllocation;
    private Allocation outputAllocation;
    private Bitmap sourceBitmap;
    private Bitmap destBitmap;

    public InvertRS(RenderScriptGL renderScriptGL, Resources resources) {
        this.renderScriptGL = renderScriptGL;
        scriptC_invert = new ScriptC_invert(renderScriptGL, resources, R.raw.invert);

        this.renderScriptGL.bindRootScript(scriptC_invert);

        sourceBitmap = BitmapFactory.decodeResource(resources, R.drawable.scene);
        destBitmap = Bitmap.createBitmap(sourceBitmap.getWidth(), sourceBitmap.getHeight(), Bitmap.Config.ARGB_8888);

        inputAllocation = Allocation.createFromBitmap(this.renderScriptGL, sourceBitmap);

        scriptC_invert.set_inputAllocation(inputAllocation);//这句代码必须的
        outputAllocation = Allocation.createFromBitmap(this.renderScriptGL, destBitmap);
    }


    /**
     * 应该在更新完位置等信息后再调用
     *
     * @param imageView
     */
    public void setMagniferImage(ImageView imageView) {
        scriptC_invert.forEach_magnify(inputAllocation, outputAllocation);
        // l.e("输出到dest!");
        outputAllocation.copyTo(destBitmap);
        // l.e("添加iv!");
        imageView.setImageBitmap(destBitmap);
    }
}
