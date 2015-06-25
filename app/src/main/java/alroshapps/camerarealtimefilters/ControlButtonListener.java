package alroshapps.camerarealtimefilters;

import android.view.View;
import android.widget.Button;

/**
 * Created by ar1v13 on 25/06/15.
 */
public class ControlButtonListener implements View.OnClickListener {
    @Override
    public void onClick(View view) {
        int currentFunction = LiveCameraActivity.getProcessingFunction();

        switch(currentFunction){
            case 6:
                String btnText = (String) ((Button) view).getText();
                if (btnText == "+") {
                    AngleCalculator.increaseAngle(10);
                }else{
                    AngleCalculator.increaseAngle(-10);
                }
                break;
            default:
                System.out.println("Default Mode");
                break;
        }

    }
}
