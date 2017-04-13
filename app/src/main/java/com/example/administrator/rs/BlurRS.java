package com.example.administrator.rs;

import android.app.Application;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.renderscript.Allocation;
import android.renderscript.RenderScriptGL;
import android.widget.ImageView;
import android.widget.Toast;


/**
 * Created by mao on 17-3-14.
 */
public class BlurRS {

    RenderScriptGL renderScriptGL;
    ScriptC_blur scriptC_blur;
    private Allocation inputAllocation;
    private Allocation outputAllocation;
    private Bitmap sourceBitmap;
    private Bitmap destBitmap;

    public BlurRS(RenderScriptGL renderScriptGL, Resources resources) {
        this.renderScriptGL = renderScriptGL;
        scriptC_blur = new ScriptC_blur(renderScriptGL, resources, R.raw.blur);

        this.renderScriptGL.bindRootScript(scriptC_blur);

        sourceBitmap = BitmapFactory.decodeResource(resources, R.drawable.g);
        destBitmap = Bitmap.createBitmap(sourceBitmap.getWidth(), sourceBitmap.getHeight(), Bitmap.Config.ARGB_8888);

        inputAllocation = Allocation.createFromBitmap(this.renderScriptGL, sourceBitmap);

        scriptC_blur.set_inputAllocation(inputAllocation);//这句代码必须的
        outputAllocation = Allocation.createFromBitmap(this.renderScriptGL, destBitmap);
    }

    void setWeight(float weight) {

        scriptC_blur.set_weight(weight);

    }

    void setRadius(int radius) {

        scriptC_blur.set_radius(radius);

    }

    /**
     * 应该在更新完位置等信息后再调用
     *
     * @param imageView
     */
    public void setMagniferImage(ImageView imageView, Context context) {
        scriptC_blur.forEach_magnify(inputAllocation, outputAllocation);
        l.e("输出到dest! 输出大小为" + outputAllocation.getBytesSize() + "接收大小为" + destBitmap.getByteCount());

        outputAllocation.copyTo(destBitmap);
//        l.e("添加iv!");
        Toast.makeText(context, "计算完毕", Toast.LENGTH_SHORT).show();
        imageView.setImageBitmap(destBitmap);
    }
}
