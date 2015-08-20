package com.apps.alexs7.pointop;

import android.app.Activity;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.view.TextureView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

/**
 * Created by alex on 09/08/15.
 */
public class UIHelper {

    public static void buidListWithChoices(Activity activity, BProcessor processor) {
        final BProcessor mBProcessor = processor;
        final Activity mActivity = activity;
        final Resources resources = mActivity.getResources();
        ListView lvlChoices = (ListView) activity.findViewById(R.id.lvImageProcessChoices);

        if(lvlChoices != null){
            lvlChoices.setAdapter(new ArrayAdapter<String>(activity,
                    R.layout.control_filter_item_view,
                    activity.getResources().getStringArray(R.array.list_of_image_processing_choices)));

            lvlChoices.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    int itemId = (int) parent.getItemIdAtPosition(position);
                    switch (position) {
                        case 0:
                            if (resources.getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
                                mActivity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
                            } else {
                                Toast.makeText(mActivity, R.string.portrait_warning, Toast.LENGTH_SHORT).show();
                            }
                            break;
                        case 1:
                            if (resources.getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
                                mActivity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
                            } else {
                                Toast.makeText(mActivity, R.string.landscape_warning, Toast.LENGTH_SHORT).show();
                            }
                            break;
                        case 9:
                            CleanPreviewFragment  cleanPreviewFragment = (CleanPreviewFragment)
                                    mActivity.getFragmentManager().findFragmentByTag(FragmentHelper.CLEAN_PREVIEW_TAG);
                            cleanPreviewFragment.stopPreview();
                            mBProcessor.setFunction(itemId);
                            break;
                        default:
                            mBProcessor.setFunction(itemId);
                            break;
                    }
                }
            });
        }
    }
}
