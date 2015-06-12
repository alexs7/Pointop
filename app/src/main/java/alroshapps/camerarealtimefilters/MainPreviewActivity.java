package alroshapps.camerarealtimefilters;

import java.io.IOException;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.SurfaceTexture;
import android.hardware.Camera;
import android.os.Bundle;
import android.util.Log;
import android.view.TextureView;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
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
    private Bitmap bmpCopy;

    private MatrixGenerator matrixGenerator;

    private int processingFunction = 0;
    private int maxFunctions = 1;

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

        matrixGenerator = new MatrixGenerator();

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

        //edit bitmap here
        bmpCopy = bmp.copy(bmp.getConfig(),true);

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

        mImageView.setImageBitmap(bmpCopy);

        switch(processingFunction){
            case 0: processingFunction = 0;
                ColorMatrixColorFilter filter0 = matrixGenerator.getInverseMatrixFilter();
                mImageView.setColorFilter(filter0);
                break;
            case 1: processingFunction = 1;
                ColorMatrixColorFilter filter1 = matrixGenerator.getBlackAndWhiteMatrix();
                mImageView.setColorFilter(filter1);
                break;
        }
    }

    public void previousFunction(View v){
        Log.i("processingFunction", Integer.toString(processingFunction));
        if(processingFunction !=0) {
            processingFunction -= 1;
        }else{
            Toast.makeText(this, getString(R.string.funtionLimitWarning), Toast.LENGTH_SHORT).show();
        }
    }

    public void nextFunction(View v){
        if(processingFunction != maxFunctions) {
            processingFunction += 1;
        }else{
            Toast.makeText(this, getString(R.string.funtionLimitWarning), Toast.LENGTH_SHORT).show();
        }
    }
}