package lucas.trainone;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import java.util.Date;

/**
 * Created by root on 4/14/15.
 */

public class Now extends Activity implements View.OnClickListener{
    Button btn;

    //onCreate is started when the activity starts, the first thing you need to do
    //is to chain upward t othe superclass.
    @Override
    public void onCreate(Bundle icircle){
        super.onCreate(icircle);
        btn = new Button(this);
        btn.setOnClickListener(this);
        updateTime();
        setContentView(btn);

    }
    //A JButton click raises an ActionEvent, which is passed to the ActionListener configured
    //for the button. In Android, a button click causes onClick() to be invoked in the

    public void onClick(View view){
        updateTime();
    }
    private void updateTime(){
        btn.setText(new Date().toString());
    }

}
