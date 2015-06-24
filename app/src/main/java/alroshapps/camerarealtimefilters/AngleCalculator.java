package alroshapps.camerarealtimefilters;

/**
 * Created by ar1v13 on 24/06/15.
 */
public class AngleCalculator {
    private static float angle = 0.0f;

    public static void setAngle(float arg){
        angle = arg;
    };

    public static float getAngle(){
        return angle;
    };

    public static float increaseAngle(float arg){
        return angle += arg;
    };
}
