package alroshapps.camerarealtimefilters;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v8.renderscript.Allocation;
import android.support.v8.renderscript.RenderScript;

/**
 * Created by alex on 02/07/15.
 */
public class RenderScriptFourierWrapper {

    private Allocation inAllocation;
    private Allocation outAllocation;
    private RenderScript rs;
    private ScriptC_fourier fourierScript;
    private Context ctx;

    public RenderScriptFourierWrapper(Context context){
        ctx = context;
        rs = RenderScript.create(ctx);
        fourierScript = new ScriptC_fourier(rs, ctx.getResources(), R.raw.fourier);
    };

    public void setInAllocation(Bitmap bmp){
        inAllocation = Allocation.createFromBitmap(rs,bmp, Allocation.MipmapControl.MIPMAP_NONE, Allocation.USAGE_SCRIPT);
        fourierScript.set_inPixels(inAllocation);
    };

    public void setOutAllocation(Bitmap bmp){
        outAllocation = Allocation.createFromBitmap(rs,bmp);
    };

    public void setScriptWidth(int scriptWidth) {
        fourierScript.set_width(scriptWidth);
    }

    public void setScriptHeight(int scriptHeight) {
        fourierScript.set_height(scriptHeight);
    }

    public void forEach_root(){
        fourierScript.forEach_root(inAllocation,outAllocation);
    }
}
