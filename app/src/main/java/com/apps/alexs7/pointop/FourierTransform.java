package com.apps.alexs7.pointop;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v8.renderscript.Allocation;
import android.support.v8.renderscript.RenderScript;

/**
 * Created by alex on 15/08/15.
 */
public class FourierTransform {

    private Allocation inAllocation;
    private Allocation outAllocation;
    private RenderScript mRS = null;
    private ScriptC_fouriertransform mScript = null;

    public FourierTransform(Context ctx) {
        mRS = RenderScript.create(ctx);
        mScript = new ScriptC_fouriertransform(mRS, ctx.getResources(), R.raw.fouriertransform);
    }

    public Bitmap apply(Bitmap origBmp) {
        int width = origBmp.getWidth();
        int height = origBmp.getHeight();

        inAllocation = Allocation.createFromBitmap(mRS, origBmp);
        outAllocation = Allocation.createFromBitmap(mRS, Bitmap.createBitmap(width,height,Bitmap.Config.ARGB_8888));

        mScript.set_width(width - 1);
        mScript.set_height(height - 1);
        mScript.set_inPixels(inAllocation);

//        mScript.invoke_test(width-1,height - 1);
        mScript.forEach_root(inAllocation,outAllocation);

        outAllocation.copyTo(origBmp);

        return origBmp;
    }
}
