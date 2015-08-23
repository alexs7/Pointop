package com.apps.alexs7.pointop;

import android.app.Activity;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.WindowManager;

import com.apps.alexs7.pointop.fragments.CleanPreviewFragment;


public class MainPreviewActivity extends Activity
        implements CleanPreviewFragment.OnBitmapUpdatedListener {

    private BProcessor bProcessor;
    private FragmentHelper fragmentHelper;
    private UIHelper uiHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main_preview);

        bProcessor = new BProcessor(this);
        fragmentHelper = new FragmentHelper(this);
        uiHelper = new UIHelper(this,bProcessor);

        fragmentHelper.setupFragments();
        uiHelper.buildListWithChoices();
    }

    @Override
    public void onCleanPreviewBitmapUpdated(Bitmap origBmp) {
        if(fragmentHelper.getProcessedPreviewFragment() != null) {
            fragmentHelper.getProcessedPreviewFragment().setImageViewBitmap(
                    bProcessor.processBitmap(origBmp)
            );
        }
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        setContentView(R.layout.activity_main_preview);

        fragmentHelper.setupFragments();
        uiHelper.buildListWithChoices();
    }
}
