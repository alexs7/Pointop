package alroshapps.camerarealtimefilters;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ListAdapter;

/**
 * Created by alex on 23/06/15.
 */
public class ButtonAdapter extends BaseAdapter {
    private Context mContext;
    public String[] filenames = { "+", "-" };
    private float scale;
    private int btnWidthPixels;
    private int btnHeightPixels;

    // Gets the context so it can be used later
    public ButtonAdapter(Context c) {
        mContext = c;
        scale = mContext.getResources().getDisplayMetrics().density;
    }

    // Total number of things contained within the adapter
    public int getCount() {
        return filenames.length;
    }

    // Require for structure, not really used in my code.
    public Object getItem(int position) {
        return null;
    }

    // Require for structure, not really used in my code. Can
    // be used to get the id of an item in the adapter for
    // manual control.
    public long getItemId(int position) {
        return position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        final Button btn;
        if (convertView == null) {
            // if it's not recycled, initialize some attributes
            btn = new Button(mContext);
            btnWidthPixels = (int) (80 * scale + 0.5f);
            btnHeightPixels = (int) (60 * scale + 0.5f);
            btn.setLayoutParams(new GridView.LayoutParams(btnWidthPixels, btnHeightPixels));
            btn.setPadding(2, 2, 2, 2);
        }
        else {
            btn = (Button) convertView;
        }

        btn.setText(filenames[position]);
        // filenames is an array of strings
        btn.setTextColor(Color.WHITE);
        //btn.setBackgroundResource(R.drawable.button);
        btn.setId(position);

        btn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String btnText = (String) ((Button) v).getText();
                if (btnText == "+") {
                    AngleCalculator.increaseAngle(10);
                };
                if (btnText == "-") {
                    AngleCalculator.increaseAngle(-10);
                };
            }
        });

        return btn;
    }
}