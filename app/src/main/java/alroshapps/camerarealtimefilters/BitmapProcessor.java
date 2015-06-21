package alroshapps.camerarealtimefilters;

import android.content.Context;
import android.graphics.Bitmap;

/**
 * Created by alex on 13/06/15.
 */
public class BitmapProcessor {

    private Context ctx;
    private RenderScriptEdgeDetectWrapper renderScriptEdgeDetectWrapper;
    private RenderScriptAvgOperWrapper renderScriptAvgOperWrapper;

    public BitmapProcessor(Context context){
        ctx = context;
        renderScriptEdgeDetectWrapper = new RenderScriptEdgeDetectWrapper(ctx);
        renderScriptAvgOperWrapper = new RenderScriptAvgOperWrapper(ctx);
    }

    public Bitmap processBmpAvgOper(Bitmap bmp, Bitmap bmpCopy) {
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
        renderScriptAvgOperWrapper.setInAllocation(bmp);
        renderScriptAvgOperWrapper.setOutAllocation(bmpCopy);
        renderScriptAvgOperWrapper.setScriptWidth(bmp.getWidth());
        renderScriptAvgOperWrapper.setScriptHeight(bmp.getHeight());
        renderScriptAvgOperWrapper.forEach_root();

        return bmpCopy;
    };

    public Bitmap processBmpEdgeDetect(Bitmap bmp, Bitmap bmpCopy) {

        //type will be used for different edge detection algorithms
        renderScriptEdgeDetectWrapper.setInAllocation(bmp);
        renderScriptEdgeDetectWrapper.setOutAllocation(bmpCopy);
        renderScriptEdgeDetectWrapper.setScriptWidth(bmp.getWidth());
        renderScriptEdgeDetectWrapper.setScriptHeight(bmp.getHeight());
        renderScriptEdgeDetectWrapper.forEach_root();

        return bmpCopy;
    }

}
