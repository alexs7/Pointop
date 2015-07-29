package com.apps.alexs7.pointop;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;


public class MainPreviewActivity extends Activity
        implements CleanPreviewFragment.OnBitmapUpdatedListener {

    private ListView lvChoices;
    private FragmentManager fragmentManager;
    private BProcessor bProcessor;
    private ProcessedPreviewFragment processedPreviewFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_main_preview);

        fragmentManager = getFragmentManager();
        processedPreviewFragment = (ProcessedPreviewFragment) fragmentManager.findFragmentById(R.id.processed_preview_fragment);
        bProcessor = new BProcessor(this);

        lvChoices = (ListView) findViewById(R.id.lvImageProcessChoices);
        lvChoices.setAdapter(new ArrayAdapter<String>(this,
                R.layout.control_filter_item_view,
                getResources().getStringArray(R.array.list_of_image_processing_choices)));

        lvChoices.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                bProcessor.setFunction((int) parent.getItemIdAtPosition(position));
            }
        });
    }

    @Override
    public void onCleanPreviewBitmapUpdated(Bitmap origBmp) {

        if(processedPreviewFragment != null) {
            processedPreviewFragment.setImageViewBitmap(
                    bProcessor.processBitmap(origBmp)
            );
        }
    }

}
