package alroshapps.camerarealtimefilters;

import java.io.IOException;
import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Color;
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

public class LiveCameraActivity extends Activity implements TextureView.SurfaceTextureListener {
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

    private static int processingFunction = 0; //should be zero

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
                int itemIndex = (int) parent.getItemIdAtPosition(position);

                if(itemIndex == 7){
                    mCamera.stopPreview();

                    bmp = mTextureView.getBitmap();
                    //edit bitmap here
                    bmpCopy = bmp.copy(bmp.getConfig(),true);

                    double fourierSum = 0;
                    int width = bmp.getWidth()/8;
                    int height = bmp.getHeight()/8;

                    for (int x = 0; x < width; x++) {
                        for (int y = 0; y < height; y++) {

                            int rgbInt = bmp.getPixel(x, y);
                            int red = (rgbInt >> 16) & 0x0ff;
                            int green = (rgbInt >> 8) & 0x0ff;
                            int blue = (rgbInt) & 0x0ff;
                            int averagePixelV = (red + blue + green)/3;

                            bmpCopy.setPixel(x, y, averagePixelV);
                        }
                    }

//                    for (int k1 = 0; k1 < width; k1++) {
//                        for (int k2 = 0; k2 < height; k2++) {
//
//                            fourierSum = 0;
//                            for (int x = 0; x < width; x++) {
//                                for (int y = 0; y < height; y++) {
//
//                                    double angle = 2 * Math.PI * (((k1*x) / width) + ((k2*y) / height));
//                                    fourierSum = fourierSum + averagePixelV * Math.cos(angle);
//                                }
//                            }
//
//                            int fourierPixelV = (int) Math.round(fourierSum);
//                            fourierPixelV = -((fourierPixelV & 0x0ff) << 16) | ((fourierPixelV & 0x0ff) << 8) | (fourierPixelV & 0x0ff);
//
//                            bmpCopy.setPixel(k1,k2, fourierPixelV);
//                        }
//                    }
                    mImageView.setImageBitmap(bmpCopy);

                }else{
                    mCamera.startPreview();
                    processingFunction = itemIndex;
                }

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
        System.out.println("onSurfaceTextureSizeChanged");
    }

    public boolean onSurfaceTextureDestroyed(SurfaceTexture surface) {
        mCamera.stopPreview();
        mCamera.release();
        return true;
    }

    public void onSurfaceTextureUpdated(SurfaceTexture surface) {
        // Invoked every time there's a new Camera preview frame

//        bmp = mTextureView.getBitmap();
//        //edit bitmap here
//        bmpCopy = bmp.copy(bmp.getConfig(),true);
//
//        switch(processingFunction){
//            case 0:
//                ColorMatrixColorFilter filter0 = matrixGenerator.getInverseMatrixFilter();
//                mImageView.setColorFilter(filter0);
//                mImageView.setImageBitmap(bmpCopy);
//                informationView.setText(getString(R.string.inverseInfo));
//                break;
//            case 1:
//                ColorMatrixColorFilter filter1 = matrixGenerator.getBlackAndWhiteMatrix();
//                mImageView.setColorFilter(filter1);
//                mImageView.setImageBitmap(bmpCopy);
//                informationView.setText(getString(R.string.blackAndWhite));
//                break;
//            case 2:
//                ColorMatrixColorFilter filter2 = matrixGenerator.getRandomMatrix();
//                mImageView.setColorFilter(filter2);
//                mImageView.setImageBitmap(bmpCopy);
//                informationView.setText(getString(R.string.randomValue));
//                break;
//            case 3:
//                mImageView.clearColorFilter();
//                mImageView.setImageBitmap(bmpCopy);
//                informationView.setText(getString(R.string.defaultMode));
//                break;
//            case 4:
//                mImageView.clearColorFilter();
//                bitmapProcessor.processBmpAvgOper(bmp, bmpCopy);
//                mImageView.setImageBitmap(bmpCopy);
//                informationView.setText(getString(R.string.averageOperator));
//                break;
//            case 5:
//                mImageView.clearColorFilter();
//                bitmapProcessor.processBmpEdgeDetect(bmp, bmpCopy);
//                mImageView.setImageBitmap(bmpCopy);
//                informationView.setText(getString(R.string.edgeDetection));
//                break;
//            case 6:
//                mImageView.clearColorFilter();
//                Matrix rotationMatrix = matrixGenerator.getRotationMatrix(AngleCalculator.getAngle());
//                Bitmap rotatedBitMap = Bitmap.createBitmap(bmpCopy,0,0,bmpCopy.getWidth(),bmpCopy.getHeight(),rotationMatrix,true);
//                mImageView.setImageBitmap(rotatedBitMap);
//                informationView.setText(getString(R.string.rotationMatrix));
//                break;
//            case 7:
//                mImageView.clearColorFilter();
//                bitmapProcessor.processFourierTransform(bmp, bmpCopy);
//                mImageView.setImageBitmap(bmpCopy);
//                informationView.setText(getString(R.string.fourierTransform));
//                break;
//        }
    }

    public static int getProcessingFunction() {
        return processingFunction;
    }

    public static void setProcessingFunction(int arg) {
        processingFunction = arg;
    }
}