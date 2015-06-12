package alroshapps.camerarealtimefilters;

import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;

/**
 * Created by alex on 12/06/15.
 */
public class MatrixGenerator {

    public MatrixGenerator(){}

    public ColorMatrixColorFilter getInverseMatrixFilter(){
        ColorMatrix colorMatrix = new ColorMatrix();
        colorMatrix.setSaturation(0);

        ColorMatrix threshold = new ColorMatrix(new float[] {
                -1, 0, 0, 1, 255,
                0, -1, 0, 1, 255,
                0, 0, -1, 1, 255,
                0, 0, 0, 1, 0
        });

        //colorMatrix.postConcat(threshold);

        ColorMatrixColorFilter filter = new ColorMatrixColorFilter(colorMatrix);
        return filter;
    };

    public ColorMatrixColorFilter getBlackAndWhiteMatrix(){
        ColorMatrix colorMatrix = new ColorMatrix();
        colorMatrix.setSaturation(0);

        float m = 255f;
        float t = -255*1.2f;
        ColorMatrix threshold = new ColorMatrix(new float[] {
                m, 0, 0, 1, t,
                0, m, 0, 1, t,
                0, 0, m, 1, t,
                0, 0, 0, 1, 0
        });

        colorMatrix.postConcat(threshold);

        ColorMatrixColorFilter filter = new ColorMatrixColorFilter(colorMatrix);
        return filter;
    };


}
