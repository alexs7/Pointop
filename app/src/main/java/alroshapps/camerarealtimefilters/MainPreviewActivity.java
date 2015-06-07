package alroshapps.camerarealtimefilters;

import java.io.IOException;
import java.util.List;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Paint;
import android.graphics.SurfaceTexture;
import android.graphics.drawable.BitmapDrawable;
import android.hardware.Camera;
import android.os.Bundle;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.TextureView;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.Toast;

/**
 * Demonstrates the variable performance (exec. time) of TextureView.getBitmap(Bitmap).
 * The exec. time is logged and also printed on screen.
 *
 * Depending on unknown circumstances the getBitmap(Bitmap) function runs slowly most of
 * the time, dropping its performance for about 1/3, compared to when it runs fast.
 *
 *  * Tested on:
 * - Nexus 7 (4.2.2)
 * - Galaxy Nexus (4.2.2)
 *
 * Other problems:
 * getBitmap() appears to take too much time: on the Nexus 7 it is at least 15ms ~ 20ms.
 * However, drawing the resulting Bitmap only takes 7 ms.
 *
 *
 * Sample adapted from http://developer.android.com/reference/android/view/TextureView.html
 *
 * @author Rafael Sierra
 *
 */
public class MainPreviewActivity extends Activity implements TextureView.SurfaceTextureListener {
    private Camera mCamera;
    private TextureView mTextureView;
    private CustomImageView mImageView;

    private Bitmap bmp;
    private Bitmap bmp2;

    private Paint paint;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.main_layout);

        mTextureView = (TextureView) findViewById(R.id.textureview);
        mTextureView.setSurfaceTextureListener(this);

        mImageView = (CustomImageView) findViewById(R.id.imageview);
        mImageView.setTextureView(mTextureView);

        Toast.makeText(this, getString(R.string.actions_help), Toast.LENGTH_LONG).show();

    }

    public void onSurfaceTextureAvailable(SurfaceTexture surface, int width, int height) {
        mCamera = Camera.open();
        Camera.Parameters parameters = mCamera.getParameters();
        mCamera.setParameters(parameters);

        try {
            mCamera.setPreviewTexture(surface);
            mCamera.startPreview();
        } catch (IOException ioe) {
            // Something bad happened
        }
    }

    public void onSurfaceTextureSizeChanged(SurfaceTexture surface, int width, int height) {
        // Ignored, Camera does all the work for us
    }

    public boolean onSurfaceTextureDestroyed(SurfaceTexture surface) {
        mCamera.stopPreview();
        mCamera.release();
        return true;
    }

    public void onSurfaceTextureUpdated(SurfaceTexture surface) {
        // Invoked every time there's a new Camera preview frame

        bmp = mTextureView.getBitmap();
        //Log.i("Height",Integer.toString(bmp.getHeight()));
        //Log.i("Width", Integer.toString(bmp.getWidth()));
        //mImageView.setCustomBitMap(bmp);
        //mImageView.setImageBitmap(bmp);
        //mImageView.invalidate();

        //edit bitmap here
        bmp2 = bmp.copy(bmp.getConfig(),true);

//        for(int x=0;x<bmp.getWidth();x++){
//            for(int y=0;y<bmp.getHeight();y++){
//                //Log.i("Pixel RGB (Int)", Integer.toString(bmp.getPixel(x,y)));
//                if(bmp.getPixel(x,y) < -8388608){
//                    bmp2.setPixel(x,y,Color.WHITE);
//                }else{
//                    bmp2.setPixel(x,y,Color.BLACK);
//                }
//            }
//        }

//        Canvas canvas = new Canvas(bmp2);
//
//        paint.setColor(Color.GREEN);
//        canvas.drawLine(0, 0, 10, 10, paint);//up
//        canvas.drawLine(10, 20, 10, 02, paint);//left
        mImageView.setImageBitmap(bmp2);
        //mImageView.draw(canvas);

        ColorMatrix colorMatrix = new ColorMatrix();
        colorMatrix.setSaturation(0);

        float m = 255f;
        float t = -255*1.2f;
        ColorMatrix threshold = new ColorMatrix(new float[] {
                m, 0, 0, 1, t,
                0, m, 0, 1, t,
                0, 0, m, 1, t,
                0, 0, 0, 1, 0
        });

// Convert to grayscale, then scale and clamp
        colorMatrix.postConcat(threshold);

        ColorMatrixColorFilter filter = new ColorMatrixColorFilter(colorMatrix);
        mImageView.setColorFilter(filter);
    }
}