package alroshapps.camerarealtimefilters;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v8.renderscript.*;

/**
 * Created by alex on 13/06/15.
 */
public class RenderScriptEdgeDetectWrapper {

    private Allocation inAllocation;
    private Allocation outAllocation;
    private RenderScript rs;
    private ScriptC_edgedetect edgeDetectScript;
    private Context ctx;


    public RenderScriptEdgeDetectWrapper(Context context){
        ctx = context;
        rs = RenderScript.create(ctx);
        edgeDetectScript = new ScriptC_edgedetect(rs, ctx.getResources(), R.raw.edgedetect);
    };

    public void setInAllocation(Bitmap bmp){
        inAllocation = Allocation.createFromBitmap(rs,bmp);
        edgeDetectScript.set_inPixels(inAllocation);
    };

    public void setOutAllocation(Bitmap bmp){
        outAllocation = Allocation.createFromBitmap(rs,bmp);
    };

    public void setScriptWidth(int scriptWidth) {
        edgeDetectScript.set_width(scriptWidth);
    }

    public void setScriptHeight(int scriptHeight) {
        edgeDetectScript.set_height(scriptHeight);
    }

    public void forEach_root(){
        edgeDetectScript.forEach_root(inAllocation,outAllocation);
    }
}
