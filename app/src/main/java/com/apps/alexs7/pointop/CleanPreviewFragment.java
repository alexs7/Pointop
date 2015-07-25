package com.apps.alexs7.pointop;

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

    private CenteredSquareTextureView mTextureView;
    private Camera mCamera;

    public CleanPreviewFragment() {}

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
    public void onSurfaceTextureAvailable(SurfaceTexture surfaceTexture, int width, int height) {
        mCamera = Camera.open(Camera.CameraInfo.CAMERA_FACING_FRONT);
        Camera.Parameters parameters = mCamera.getParameters();
        List<Camera.Size> resolutionSizes = parameters.getSupportedPictureSizes();

        Camera.Size optimalSize = CameraUtilities.getOptimalPreviewSize(resolutionSizes, width, height);
        parameters.setPreviewSize(optimalSize.width, optimalSize.height);

        try {
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
        return false;
    }

    @Override
    public void onSurfaceTextureUpdated(SurfaceTexture surfaceTexture) {

    }
}
