package com.apps.alexs7.pointop;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.res.Configuration;
import android.content.res.Resources;

/**
 * Created by alex on 09/08/15.
 */
public class FragmentHelper {

    private Resources resources;
    private FragmentManager fragmentManager;
    private CleanPreviewFragment cleanPreviewFragment;
    private ProcessedPreviewFragment processedPreviewFragment;
    public static String CLEAN_PREVIEW_TAG = "CLEAN_PREVIEW__FRAGMENT_TAG";

    public FragmentHelper(Activity activity){
        resources = activity.getResources();
        fragmentManager = activity.getFragmentManager();
    }

    public void setupFragments() {
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        cleanPreviewFragment = new CleanPreviewFragment();
        processedPreviewFragment = new ProcessedPreviewFragment();

        if(resources.getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE){
            fragmentTransaction.add(R.id.clean_preview_fragment_container, cleanPreviewFragment,CLEAN_PREVIEW_TAG);
            fragmentTransaction.add(R.id.processed_preview_fragment_container, processedPreviewFragment);
        }else if(resources.getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT){
            fragmentTransaction.add(R.id.clean_preview_fragment_container, cleanPreviewFragment,CLEAN_PREVIEW_TAG);
            fragmentTransaction.detach(cleanPreviewFragment);
            fragmentTransaction.replace(R.id.clean_preview_fragment_container, processedPreviewFragment);
            fragmentTransaction.attach(cleanPreviewFragment);
        }
        fragmentTransaction.commit();
    }

    public ProcessedPreviewFragment getProcessedPreviewFragment() {
        return processedPreviewFragment;
    }

    public CleanPreviewFragment getCleanPreviewFragment() {
        return cleanPreviewFragment;
    }
}
