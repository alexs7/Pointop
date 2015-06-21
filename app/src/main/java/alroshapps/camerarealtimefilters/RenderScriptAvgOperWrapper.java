package alroshapps.camerarealtimefilters;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v8.renderscript.*;

/**
 * Created by alex on 13/06/15.
 */
public class RenderScriptAvgOperWrapper {

    private Allocation inAllocation;
    private Allocation outAllocation;
    private RenderScript rs;
    private ScriptC_avgoper avgScript;
    private Context ctx;


    public RenderScriptAvgOperWrapper(Context context){
        ctx = context;
        rs = RenderScript.create(ctx);
        avgScript = new ScriptC_avgoper(rs, ctx.getResources(), R.raw.avgoper);
    };

    public void setInAllocation(Bitmap bmp){
        inAllocation = Allocation.createFromBitmap(rs,bmp);
        avgScript.set_inPixels(inAllocation);
    };

    public void setOutAllocation(Bitmap bmp){
        outAllocation = Allocation.createFromBitmap(rs,bmp);
    };

    public void setScriptWidth(int scriptWidth) {
        avgScript.set_width(scriptWidth);
    }

    public void setScriptHeight(int scriptHeight) {
        avgScript.set_height(scriptHeight);
    }

    public void forEach_root(){
        avgScript.forEach_root(inAllocation,outAllocation);
    }
}
