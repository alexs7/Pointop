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
    private RenderScriptFourierWrapper renderScriptFourierWrapper;

    public BitmapProcessor(Context context){
        ctx = context;
        renderScriptEdgeDetectWrapper = new RenderScriptEdgeDetectWrapper(ctx);
        renderScriptAvgOperWrapper = new RenderScriptAvgOperWrapper(ctx);
        renderScriptFourierWrapper = new RenderScriptFourierWrapper(ctx);
    }

    /*
        Any operations that work on the bitmaps, change the bitmap. Bitmaps behave as a static
        object. They do not need to be returned in the methods, just left the return method
        just in case. So for example processBmpAvgOper although it returns a bitmap it is not used,
        as it edits one, and that is enough
     */

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
        renderScriptAvgOperWrapper.setScriptWidth(bmp.getWidth()-1);
        renderScriptAvgOperWrapper.setScriptHeight(bmp.getHeight()-1);
        renderScriptAvgOperWrapper.forEach_root();

        return bmpCopy;
    };

    public Bitmap processBmpEdgeDetect(Bitmap bmp, Bitmap bmpCopy) {

        //type will be used for different edge detection algorithms
        renderScriptEdgeDetectWrapper.setInAllocation(bmp);
        renderScriptEdgeDetectWrapper.setOutAllocation(bmpCopy);
        renderScriptEdgeDetectWrapper.setScriptWidth(bmp.getWidth()-1);
        renderScriptEdgeDetectWrapper.setScriptHeight(bmp.getHeight()-1);
        renderScriptEdgeDetectWrapper.forEach_root();

        return bmpCopy;
    }

    public Bitmap processFourierTransform(Bitmap bmp, Bitmap bmpCopy) {

        renderScriptFourierWrapper.setInAllocation(bmp);
        renderScriptFourierWrapper.setOutAllocation(bmpCopy);
        renderScriptFourierWrapper.setScriptWidth(bmp.getWidth()-1);
        renderScriptFourierWrapper.setScriptHeight(bmp.getHeight()-1);
        renderScriptFourierWrapper.forEach_root();

        return bmpCopy;
    }
}
