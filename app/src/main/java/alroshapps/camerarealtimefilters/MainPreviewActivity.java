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
import android.renderscript.*;

public class MainPreviewActivity extends Activity implements TextureView.SurfaceTextureListener {
    private Camera mCamera;
    private TextureView mTextureView;
    private CustomImageView mImageView;

    private Bitmap bmp;
    private Bitmap bmpCopy;

    private MatrixGenerator matrixGenerator;
    private BitmapProcessor bitmapProcessor;

    private int processingFunction = 0;
    private int maxFunctions = 4;

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
        bitmapProcessor = new BitmapProcessor();

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

        switch(processingFunction){
            case 0: processingFunction = 0;
                ColorMatrixColorFilter filter0 = matrixGenerator.getInverseMatrixFilter();
                mImageView.setColorFilter(filter0);
                mImageView.setImageBitmap(bmpCopy);
                break;
            case 1: processingFunction = 1;
                ColorMatrixColorFilter filter1 = matrixGenerator.getBlackAndWhiteMatrix();
                mImageView.setColorFilter(filter1);
                mImageView.setImageBitmap(bmpCopy);
                break;
            case 2: processingFunction = 2;
                mImageView.clearColorFilter();
                mImageView.setImageBitmap(bmpCopy);
                break;
            case 3: processingFunction = 3;
                ColorMatrixColorFilter filter2 = matrixGenerator.getRandomMatrix();
                mImageView.setColorFilter(filter2);
                mImageView.setImageBitmap(bmpCopy);
                break;
            case 4: processingFunction = 4;
                Bitmap processedBmp = bitmapProcessor.processBmp(bmp,bmpCopy);
                mImageView.clearColorFilter();
                mImageView.setImageBitmap(processedBmp);
        }
    }

    public void previousFunction(View v){
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