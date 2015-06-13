package alroshapps.camerarealtimefilters;

import android.graphics.Bitmap;
import android.graphics.Color;

/**
 * Created by alex on 13/06/15.
 */
public class BitmapProcessor {

    public BitmapProcessor(){}

    public Bitmap processBmp(Bitmap bmp, Bitmap bmpCopy) {
        for(int x=0;x<bmp.getWidth();x++){
            for(int y=0;y<bmp.getHeight();y++){
                if(bmp.getPixel(x,y) < -8388608){
                    bmpCopy.setPixel(x,y, Color.WHITE);
                }else{
                    bmpCopy.setPixel(x,y,Color.BLACK);
                }
            }
        }
        return bmpCopy;
    };
}
