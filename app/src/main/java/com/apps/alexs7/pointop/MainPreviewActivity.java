package com.apps.alexs7.pointop;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;


public class MainPreviewActivity extends Activity
        implements CleanPreviewFragment.OnBitmapUpdatedListener {

    private ListView lvlChoices;
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

        if(getResources().getConfiguration().orientation == getResources().getConfiguration().ORIENTATION_PORTRAIT &&
                findViewById(R.id.clean_preview_fragment_container) != null){

            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            if(savedInstanceState != null){
                System.out.println("savedInstanceState is NOT null");
                return;
            }
            CleanPreviewFragment cleanPreviewFragment = new CleanPreviewFragment();
            processedPreviewFragment = new ProcessedPreviewFragment();

            fragmentTransaction.add(R.id.clean_preview_fragment_container,cleanPreviewFragment);
            fragmentTransaction.detach(cleanPreviewFragment);
            fragmentTransaction.replace(R.id.clean_preview_fragment_container, processedPreviewFragment);
            fragmentTransaction.attach(cleanPreviewFragment);
            //fragmentTransaction.addToBackStack(null);

            fragmentTransaction.commit();

            //fragmentTransaction.replace(R.id.clean_preview_fragment, processedPreviewFragment);
            //fragmentTransaction.commit();
        }

        lvlChoices = (ListView) findViewById(R.id.lvImageProcessChoices);
        if(lvlChoices != null){
            lvlChoices.setAdapter(new ArrayAdapter<String>(this,
                    R.layout.control_filter_item_view,
                    getResources().getStringArray(R.array.list_of_image_processing_choices)));

            lvlChoices.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    bProcessor.setFunction((int) parent.getItemIdAtPosition(position));
                }
            });
        }
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
