package com.apps.alexs7.pointop;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.graphics.Color;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;


public class MainPreviewActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_main_preview);
    }

    @Override
    protected void onStart() {
        FragmentManager fragmentManager = getFragmentManager();

        Fragment cleanPreviewFragment = fragmentManager.findFragmentById(R.id.clean_preview_fragment);
        cleanPreviewFragment.getView().setBackgroundColor(Color.BLUE);

        //Fragment processedPreviewFragment = fragmentManager.findFragmentById(R.id.processed_preview_fragment);
        //processedPreviewFragment.getView().setBackgroundColor(Color.RED);

        super.onStart();
    }
}
