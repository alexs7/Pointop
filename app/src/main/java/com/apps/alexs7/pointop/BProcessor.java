package com.apps.alexs7.pointop;

import android.content.Context;
import android.graphics.Bitmap;

/**
 * Created by alex on 29/07/15.
 */
public class BProcessor {

    private int function;
    private EdgeDetection edgeDetection;

    public BProcessor(Context ctx) {
        function = 0;
        edgeDetection = new EdgeDetection(ctx);
    }

    public void setFunction(int function) {
        this.function = function;
    }

    public Bitmap processBitmap(Bitmap bmp) {
        Bitmap modifiedBitmap;

        switch(function) {
            case 2:
                modifiedBitmap = bmp; //do nothing
                break;
            case 8:
                modifiedBitmap = edgeDetection.apply(bmp);
                break;
            default:
                modifiedBitmap = bmp;
                break;
        }

        return modifiedBitmap;
    }

}
