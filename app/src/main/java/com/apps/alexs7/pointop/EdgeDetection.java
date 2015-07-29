package com.apps.alexs7.pointop;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v8.renderscript.*;

/**
 * Created by alex on 29/07/15.
 */
public class EdgeDetection {

    private Allocation inAllocation;
    private Allocation outAllocation;
    private RenderScript mRS = null;
    private ScriptC_edgedetect mScript = null;

    public EdgeDetection(Context ctx) {
        mRS = RenderScript.create(ctx);
        mScript = new ScriptC_edgedetect(mRS, ctx.getResources(), R.raw.edgedetect);
    }

    public Bitmap apply(Bitmap origBmp) {
        int width = origBmp.getWidth()-1;
        int height = origBmp.getHeight()-1;

        Bitmap bmpCopy = origBmp.copy(origBmp.getConfig(), true);

        inAllocation = Allocation.createFromBitmap(mRS, origBmp);
        outAllocation = Allocation.createFromBitmap(mRS, bmpCopy);

//        mScript.set_width(width);
//        mScript.set_height(height);
//        mScript.set_inPixels(inAllocation);

        mScript.forEach_root(inAllocation,outAllocation);

        outAllocation.copyTo(bmpCopy);

        return bmpCopy;
    }
}
