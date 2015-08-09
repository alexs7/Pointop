package com.apps.alexs7.pointop;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.SurfaceTexture;
import android.hardware.Camera;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Size;
import android.view.LayoutInflater;
import android.view.TextureView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import java.io.IOException;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
@SuppressWarnings("deprecation" ) //For camera
public class CleanPreviewFragment extends Fragment implements TextureView.SurfaceTextureListener {

    OnBitmapUpdatedListener mCallback;
    private CenteredSquareTextureView mTextureView;
    private Camera mCamera;

    public CleanPreviewFragment() {
        mCamera = Camera.open(Camera.CameraInfo.CAMERA_FACING_BACK);
    }

    public Camera getCamera() {
        return mCamera;
    }

    public interface OnBitmapUpdatedListener {
        public void onCleanPreviewBitmapUpdated(Bitmap bmp);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View fragmentView = inflater.inflate(R.layout.fragment_clean_preview, container, false);
        return fragmentView;
    }

    @Override
    public void onStart() {
        mTextureView = (CenteredSquareTextureView) getView().findViewById(R.id.clean_preview_txview);
        mTextureView.setSurfaceTextureListener(this);
        super.onStart();
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        // This makes sure that the container activity has implemented
        // the callback interface. If not, it throws an exception
        try {
            mCallback = (OnBitmapUpdatedListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnBitmapUpdatedListener");
        }

    }

    @Override
    public void onSurfaceTextureAvailable(SurfaceTexture surfaceTexture, int width, int height) {
        Camera.Parameters parameters = mCamera.getParameters();
        List<Camera.Size> resolutionSizes = parameters.getSupportedPictureSizes();

        Camera.Size optimalSize = CameraUtilities.getOptimalPreviewSize(resolutionSizes, width, height);
        System.out.println("Width: "+optimalSize.width);
        System.out.println("Height: "+optimalSize.height);
        parameters.setPreviewSize(optimalSize.width, optimalSize.height);
        mCamera.setParameters(parameters);

        try {
            mCamera.setPreviewCallback(null);
            mCamera.setPreviewTexture(surfaceTexture);
            mCamera.startPreview();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onSurfaceTextureSizeChanged(SurfaceTexture surfaceTexture, int width, int height) {
    }

    @Override
    public boolean onSurfaceTextureDestroyed(SurfaceTexture surfaceTexture) {
        mCamera.stopPreview();
        mCamera.release();
        return true;
    }

    @Override
    public void onSurfaceTextureUpdated(SurfaceTexture surfaceTexture) {
        Bitmap origBmp = mTextureView.getBitmap();
        mCallback.onCleanPreviewBitmapUpdated(origBmp);
    }
}
