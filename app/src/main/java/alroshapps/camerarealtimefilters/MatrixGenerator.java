package alroshapps.camerarealtimefilters;

import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Matrix;
import android.util.Log;

import java.util.Random;

/**
 * Created by alex on 12/06/15.
 */
public class MatrixGenerator {

    private Random randomGen = new Random();

    public MatrixGenerator(){}

    public ColorMatrixColorFilter getInverseMatrixFilter(){
        ColorMatrix inverse = new ColorMatrix(new float[] {
                -1, 0, 0,  0, 255,
                0, -1, 0,  0, 255,
                0,  0, -1, 0, 255,
                0,  0, 0,  1, 0
        });

        ColorMatrixColorFilter filter = new ColorMatrixColorFilter(inverse);
        return filter;
    };

    public ColorMatrixColorFilter getBlackAndWhiteMatrix(){
        ColorMatrix colorMatrix = new ColorMatrix();
        colorMatrix.setSaturation(0);

        float m = -255f;
        float t = 255f;
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

    public ColorMatrixColorFilter getRandomMatrix() {
        float r = randomGen.nextFloat();
        float g = randomGen.nextFloat();
        float b = randomGen.nextFloat();

        ColorMatrix inverse = new ColorMatrix(new float[] {
                r,  0, 0, 0, 0,
                0,  g, 0, 0, 0,
                0,  0, b, 0, 0,
                0,  0, 0, 1, 0
        });

        ColorMatrixColorFilter filter = new ColorMatrixColorFilter(inverse);
        return filter;
    };

    public static Matrix getRotationMatrix(float angle) {
        Matrix matrix = new Matrix();
        matrix.postRotate(angle);
        return matrix;
    };
}
