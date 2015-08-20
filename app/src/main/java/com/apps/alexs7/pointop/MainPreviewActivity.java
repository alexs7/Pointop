package com.apps.alexs7.pointop;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;


public class MainPreviewActivity extends Activity
        implements CleanPreviewFragment.OnBitmapUpdatedListener {

    private BProcessor bProcessor;
    private FragmentHelper fragmentHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main_preview);

        bProcessor = new BProcessor(this);

        fragmentHelper = new FragmentHelper(this);
        fragmentHelper.setupFragments();
        UIHelper.buidListWithChoices(this, bProcessor);
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
        UIHelper.buidListWithChoices(this, bProcessor);
    }
}
