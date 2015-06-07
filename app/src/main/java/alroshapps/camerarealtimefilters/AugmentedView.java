package alroshapps.camerarealtimefilters;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.hardware.Camera;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.graphics.PixelFormat;

import java.io.IOException;
import java.util.List;
import java.util.Random;

/**
 * Created by alex on 31/05/15.
 */
public class AugmentedView extends ViewGroup implements SurfaceHolder.Callback{
    private final String TAG = "Preview";

    SurfaceView mSurfaceView;
    SurfaceHolder mHolder;
    Camera.Size mPreviewSize;
    Paint mPaint;


    AugmentedView(Context context, SurfaceView sv) {
        super(context);

        mSurfaceView = sv;
        mHolder = mSurfaceView.getHolder();
        mHolder.setFormat(PixelFormat.TRANSPARENT);
        mHolder.addCallback(this);
        mHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
        mPaint = new Paint();

    }

    @Override
    protected void onDraw (Canvas canvas) {

        int w = getWidth();
        int h = getHeight();
        int radius = 100; //Math.min(w,h)/2 - (int) (Math.random()*10);

        //mPaint.setARGB(255, 255, 0, 0);
        canvas.drawCircle(w / 2, h / 2, radius, mPaint);
    }

    public void setPaintColor(int r, int g,int b){
        mPaint.setARGB(r,g,b,0);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        // We purposely disregard child measurements because act as a
        // wrapper to a SurfaceView that centers the camera preview instead
        // of stretching it.
        final int width = resolveSize(getSuggestedMinimumWidth(), widthMeasureSpec);
        final int height = resolveSize(getSuggestedMinimumHeight(), heightMeasureSpec);
        setMeasuredDimension(width, height);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        if (changed && getChildCount() > 0) {
            final View child = getChildAt(0);

            final int width = r - l;
            final int height = b - t;

            int previewWidth = width;
            int previewHeight = height;
            if (mPreviewSize != null) {
                previewWidth = mPreviewSize.width;
                previewHeight = mPreviewSize.height;
            }

            // Center the child SurfaceView within the parent.
            if (width * previewHeight > height * previewWidth) {
                final int scaledChildWidth = previewWidth * height / previewHeight;
                child.layout((width - scaledChildWidth) / 2, 0,
                        (width + scaledChildWidth) / 2, height);
            } else {
                final int scaledChildHeight = previewHeight * width / previewWidth;
                child.layout(0, (height - scaledChildHeight) / 2,
                        width, (height + scaledChildHeight) / 2);
            }
        }
    }

    public void surfaceCreated(SurfaceHolder holder) {
        setWillNotDraw(false);
        //set default paint color
        setPaintColor(255, 255, 0);
        //tryDrawing(holder);
    }

    public void surfaceDestroyed(SurfaceHolder holder) {
        // Surface will be destroyed when we return, so stop the preview.
//        if (mCamera != null) {
//            mCamera.stopPreview();
//        }
    }

    public void surfaceChanged(SurfaceHolder holder, int format, int w, int h) {

//        if(mCamera != null) {
//            Camera.Parameters parameters = mCamera.getParameters();
//            parameters.setPreviewSize(mPreviewSize.width, mPreviewSize.height);
//            requestLayout();
//
//            mCamera.setParameters(parameters);
//            mCamera.startPreview();
//        }
    }
}