package com.apps.alexs7.pointop;

import android.content.Context;
import android.graphics.Bitmap;

/**
 * Created by alex on 29/07/15.
 */
public class BProcessor {

    private int function;
    private EdgeDetection edgeDetection;
    private SimpleFilters simplefilters;

    public BProcessor(Context ctx) {
        function = 0;
        edgeDetection = new EdgeDetection(ctx);
        simplefilters = new SimpleFilters(ctx);
    }

    public void setFunction(int function) {
        this.function = function;
    }

    public Bitmap processBitmap(Bitmap bmp) {
        Bitmap modifiedBitmap;

        switch(function) {
            case 0:
                modifiedBitmap = simplefilters.inversePixels(bmp);
            case 5:
                modifiedBitmap = edgeDetection.apply(bmp);
                break;
            default:
                modifiedBitmap = bmp;
                break;
        }

        return modifiedBitmap;
    }

}
