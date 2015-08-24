package com.apps.alexs7.pointop;

import android.content.Context;
import android.graphics.Bitmap;

import org.opencv.android.Utils;
import org.opencv.core.Mat;
import org.opencv.imgproc.Imgproc;

/**
 * Created by alex on 29/07/15.
 */
public class BProcessor {

    private int function;
    private EdgeDetection edgeDetection;
    private FourierTransform fourierTransform;
    private GreyScale greyScale;
    private Threshold threshold;
    private Mat mCurrentFrameMat;
    private Mat mNextFrameMat;

    public BProcessor(Context ctx) {
        function = 0;
        edgeDetection = new EdgeDetection(ctx);
        greyScale = new GreyScale(ctx);
        threshold = new Threshold(ctx);
        fourierTransform = new FourierTransform(ctx);
        mCurrentFrameMat = new Mat();
        mNextFrameMat = new Mat();
    }

    public void setFunction(int function) {
        this.function = function;
    }

    public Bitmap processBitmap(Bitmap bmp) {
        Bitmap modifiedBitmap = Bitmap.createBitmap(bmp.getWidth(),bmp.getHeight(),Bitmap.Config.ARGB_8888);

        switch(function) {
            case 2:
                modifiedBitmap = bmp; //do nothing
                break;
            case 3:
                modifiedBitmap = greyScale.apply(bmp);
                break;
            case 4:
                modifiedBitmap = threshold.apply(bmp);
                break;
            case 7:
                modifiedBitmap = edgeDetection.apply(bmp);
                break;
            case 9:
                modifiedBitmap = fourierTransform.apply(bmp);
                break;
            case 10:
                Utils.bitmapToMat(bmp, mCurrentFrameMat);
                //Imgproc.equalizeHist(mCurrentFrame, mNextFrame);
                Imgproc.cvtColor(mCurrentFrameMat,mNextFrameMat,Imgproc.COLOR_RGBA2GRAY);
                Utils.matToBitmap(mNextFrameMat, modifiedBitmap);
                break;
            default:
                modifiedBitmap = bmp;
                break;
        }

        return modifiedBitmap;
    }

}
