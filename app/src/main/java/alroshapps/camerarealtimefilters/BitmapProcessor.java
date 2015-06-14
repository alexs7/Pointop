package alroshapps.camerarealtimefilters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.util.Log;

/**
 * Created by alex on 13/06/15.
 */
public class BitmapProcessor {

    private Context ctx;
    private PixelCalcScriptWrapper pixelCalcScriptWrapper;

    public BitmapProcessor(Context context){
        ctx = context;
        pixelCalcScriptWrapper = new PixelCalcScriptWrapper(ctx);
    }

    public Bitmap processBmp(Bitmap bmp, Bitmap bmpCopy) {
//        for(int x=0;x<bmp.getWidth();x++){
//            for(int y=0;y<bmp.getHeight();y++){
//                //pixelRGBArray = pixelToRGB(bmp.getPixel(x, y));
//
//                //Log.i("RGB Array", pixelRGBArray.toString());
//                //Log.i("Pixel integer",Integer.toString(RGBToPixel(pixelRGBArray)));
//
//                if(bmp.getPixel(x,y) < -8388608){
//                    bmpCopy.setPixel(x,y, Color.WHITE);
//                }else{
//                    bmpCopy.setPixel(x,y,Color.BLACK);
//                }
//            }
//        }
        pixelCalcScriptWrapper.setInAllocation(bmp);
        pixelCalcScriptWrapper.setOutAllocation(bmpCopy);
        pixelCalcScriptWrapper.setScriptWidth(bmp.getWidth());
        pixelCalcScriptWrapper.setScriptHeight(bmp.getHeight());
        pixelCalcScriptWrapper.forEach_root();

        return bmpCopy;
    };

}
