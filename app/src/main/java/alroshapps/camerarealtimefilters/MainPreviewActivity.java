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
import android.widget.TextView;
import android.widget.Toast;

public class MainPreviewActivity extends Activity implements TextureView.SurfaceTextureListener {
    private Camera mCamera;
    private int currentCamera;
    private TextureView mTextureView;
    private CustomImageView mImageView;
    private TextView informationView;

    private Bitmap bmp;
    private Bitmap bmpCopy;

    private MatrixGenerator matrixGenerator;
    private BitmapProcessor bitmapProcessor;

    private int processingFunction = 0; //should be zero
    private int maxFunctions = 4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.main_layout);

        mTextureView = (TextureView) findViewById(R.id.textureview);
        mTextureView.setSurfaceTextureListener(this); //assigns all the listeners of this class to that mTextureView
        mTextureView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mCamera.stopPreview();
                mCamera.release();

                if(currentCamera == Camera.CameraInfo.CAMERA_FACING_BACK){
                    mCamera = Camera.open(Camera.CameraInfo.CAMERA_FACING_FRONT);
                    currentCamera = Camera.CameraInfo.CAMERA_FACING_FRONT;
                }else{
                    mCamera = Camera.open(Camera.CameraInfo.CAMERA_FACING_BACK);
                    currentCamera = Camera.CameraInfo.CAMERA_FACING_BACK;
                };

                Camera.Parameters parameters = mCamera.getParameters();
                //parameters.setFocusMode(Camera.Parameters.FOCUS_MODE_CONTINUOUS_PICTURE);
                mCamera.setParameters(parameters);

                try {
                    mCamera.setPreviewTexture(mTextureView.getSurfaceTexture());
                    mCamera.startPreview();
                } catch (IOException ioe) {
                    // Something bad happened
                }
            }
        });

        mImageView = (CustomImageView) findViewById(R.id.imageview);
        mImageView.setTextureView(mTextureView);

        matrixGenerator = new MatrixGenerator();
        bitmapProcessor = new BitmapProcessor(this);

        informationView = (TextView)findViewById(R.id.informationField);

        Toast.makeText(this, getString(R.string.actions_help), Toast.LENGTH_LONG).show();

    }

    public void onSurfaceTextureAvailable(SurfaceTexture surface, int width, int height) {
        mCamera = Camera.open(Camera.CameraInfo.CAMERA_FACING_FRONT);
        currentCamera = Camera.CameraInfo.CAMERA_FACING_FRONT;

        Camera.Parameters parameters = mCamera.getParameters();
        //parameters.setFocusMode(Camera.Parameters.FOCUS_MODE_CONTINUOUS_PICTURE);
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
                informationView.setText(getString(R.string.inverseInfo));
                break;
            case 1: processingFunction = 1;
                ColorMatrixColorFilter filter1 = matrixGenerator.getBlackAndWhiteMatrix();
                mImageView.setColorFilter(filter1);
                mImageView.setImageBitmap(bmpCopy);
                informationView.setText(getString(R.string.blackAndWhite));
                break;
            case 2: processingFunction = 2;
                ColorMatrixColorFilter filter2 = matrixGenerator.getRandomMatrix();
                mImageView.setColorFilter(filter2);
                mImageView.setImageBitmap(bmpCopy);
                informationView.setText(getString(R.string.randomValue));
                break;
            case 3: processingFunction = 3;
                mImageView.clearColorFilter();
                mImageView.setImageBitmap(bmpCopy);
                informationView.setText(getString(R.string.defaultMode));
                break;
            case 4: processingFunction = 4;
                Bitmap processedBmp = bitmapProcessor.processBmp(bmp,bmpCopy);
                mImageView.clearColorFilter();
                mImageView.setImageBitmap(processedBmp);
                informationView.setText(getString(R.string.renderScript));
                break;
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