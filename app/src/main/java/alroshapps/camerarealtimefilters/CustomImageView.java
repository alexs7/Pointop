package alroshapps.camerarealtimefilters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.TextureView;
import android.widget.ImageView;

/**
 * Created by ar1v13 on 09/06/15.
 */
public class CustomImageView extends ImageView {

    Paint mPaint;
    private TextureView mTextureView;

    public CustomImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setWillNotDraw(false);
        mPaint = new Paint();
        mPaint.setARGB(255, 255, 0, 0);
    }

    public void setTextureView(TextureView textV){
        this.mTextureView = textV;
    }

    @Override
    protected void onDraw (Canvas canvas) {
//        Log.i("mImageView", "setImageBitmap called");
//
//        int w = getWidth();
//        int h = getHeight();
//        int radius = 100; //Math.min(w,h)/2 - (int) (Math.random()*10);
//
//        //mPaint.setARGB(255, 255, 0, 0);
//        canvas.drawCircle(w / 2, h / 2, radius, mPaint);
        super.onDraw(canvas);
    }
}
