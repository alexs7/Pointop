package alroshapps.camerarealtimefilters;

import java.io.IOException;
import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Matrix;
import android.graphics.SurfaceTexture;
import android.hardware.Camera;
import android.os.Bundle;
import android.util.Log;
import android.view.TextureView;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class MainPreviewActivity extends Activity implements TextureView.SurfaceTextureListener {
    private Camera mCamera;
    private int currentCamera;

    private TextureView mTextureView;
    private CustomImageView mImageView;
    private TextView informationView;
    private ListView lvChoices;
    private GridView gridView;

    private Bitmap bmp;
    private Bitmap bmpCopy;

    private MatrixGenerator matrixGenerator;
    private BitmapProcessor bitmapProcessor;

    private int processingFunction = 0; //should be zero

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.main_layout);

        gridView = (GridView) findViewById(R.id.optionBtns);
        gridView.setAdapter(new ButtonAdapter(this));

        lvChoices = (ListView) findViewById(R.id.lvImageProcessChoices);
        lvChoices.setAdapter(new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1,
                getResources().getStringArray(R.array.list_of_image_processing_choices)));

        lvChoices.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id){
              //Toast.makeText(view.getContext(), Long.toString(id), Toast.LENGTH_LONG).show();
                processingFunction = (int) parent.getItemIdAtPosition(position);
            };
        });

        mTextureView = (TextureView) findViewById(R.id.textureview);
        mTextureView.setSurfaceTextureListener(this); //assigns all the listeners of this class to that mTextureView
        mTextureView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                mCamera.setPreviewCallback(null);
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
                parameters.setFlashMode(Camera.Parameters.FLASH_MODE_TORCH);
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
        mCamera = Camera.open(Camera.CameraInfo.CAMERA_FACING_BACK);
        currentCamera = Camera.CameraInfo.CAMERA_FACING_BACK;

        Camera.Parameters parameters = mCamera.getParameters();
        //parameters.setFocusMode(Camera.Parameters.FOCUS_MODE_CONTINUOUS_PICTURE);
        //parameters.setFlashMode(Camera.Parameters.FLASH_MODE_TORCH);
        mCamera.setParameters(parameters);

        try {
            mCamera.setPreviewCallback(null);
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
                bitmapProcessor.processBmpAvgOper(bmp, bmpCopy);
                mImageView.clearColorFilter();
                mImageView.setImageBitmap(bmpCopy);
                informationView.setText(getString(R.string.averageOperator));
                break;
            case 5: processingFunction = 5;
                bitmapProcessor.processBmpEdgeDetect(bmp, bmpCopy);
                mImageView.clearColorFilter();
                mImageView.setImageBitmap(bmpCopy);
                informationView.setText(getString(R.string.edgeDetection));
                break;
            case 6: processingFunction = 6;
                mImageView.clearColorFilter();
                Matrix rotationMatrix = MatrixGenerator.getRotationMatrix(AngleCalculator.getAngle());
                Bitmap rotatedBitMap = Bitmap.createBitmap(bmpCopy,0,0,bmpCopy.getWidth(),bmpCopy.getHeight(),rotationMatrix,true);
                mImageView.setImageBitmap(rotatedBitMap);
                informationView.setText(getString(R.string.rotationMatrix));
                break;
        }
    }

//    public void previousFunction(View v){
//        if(processingFunction !=0) {
//            processingFunction -= 1;
//        }else{
//            Toast.makeText(this, getString(R.string.funtionLimitWarning), Toast.LENGTH_SHORT).show();
//        }
//    }
//
//    public void nextFunction(View v){
//        if(processingFunction != maxFunctions) {
//            processingFunction += 1;
//        }else{
//            Toast.makeText(this, getString(R.string.funtionLimitWarning), Toast.LENGTH_SHORT).show();
//        }
//    }

}