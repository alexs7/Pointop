package com.apps.alexs7.pointop;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.graphics.Color;
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


public class MainPreviewActivity extends Activity {

    private ListView lvChoices;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_main_preview);

        FragmentManager fragmentManager = getFragmentManager();
        Fragment cleanPreviewFragment = fragmentManager.findFragmentById(R.id.clean_preview_fragment);
        cleanPreviewFragment.getView().setBackgroundColor(Color.BLACK);

        lvChoices = (ListView) findViewById(R.id.lvImageProcessChoices);
        lvChoices.setAdapter(new ArrayAdapter<String>(this,
                R.layout.control_filter_item_view,
                getResources().getStringArray(R.array.list_of_image_processing_choices)));

        lvChoices.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //Toast.makeText(view.getContext(), Long.toString(id), Toast.LENGTH_LONG).show();
                //processingFunction = (int) parent.getItemIdAtPosition(position);
            }

            ;
        });
    }

}
