package alroshapps.camerarealtimefilters;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v8.renderscript.*;

/**
 * Created by alex on 13/06/15.
 */
public class PixelCalcScriptWrapper {

    private Allocation inAllocation;
    private Allocation outAllocation;
    RenderScript rs;
    ScriptC_pixelsCalc script;

    public PixelCalcScriptWrapper(Context context){
        rs = RenderScript.create(context);
        script = new ScriptC_pixelsCalc(rs, context.getResources(), R.raw.pixelscalc);
    };

    public void setInAllocation(Bitmap bmp){
        inAllocation = Allocation.createFromBitmap(rs,bmp);
    };

    public void setOutAllocation(Bitmap bmp){
        outAllocation = Allocation.createFromBitmap(rs,bmp);
    };

    public void forEach_root(){
        script.forEach_root(inAllocation, outAllocation);
    }
}
