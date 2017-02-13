package io.fabianterhorst.layoutkit.sample;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import io.fabianterhorst.layoutkit.android.LayoutView;
import io.fabianterhorst.layoutkit.utils.SizeLayoutBuilder;

public class MainActivity extends AppCompatActivity {

    // Used to load the 'native-lib' library on application startup.
    static {
        System.loadLibrary("native-lib");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /*setContentView(
                new LayoutView(this, new SizeLayoutBuilder()
                        .baseView(new CustomBaseView())
                        .size(100f, 100f)
                        .build().arrangement().makeViews())
        );*/
        setContentView(
                new LayoutView(this, new SizeLayoutBuilder()
                        .baseView(new CustomBaseView())
                        .size(100f, 100f)
                        .build())
        );
        //setContentView(new CustomView(this)));

        // Example of a call to a native method
        //TextView tv = (TextView) findViewById(R.id.sample_text);
        //tv.setText(stringFromJNI());
        Log.d("text", stringFromJNI());
    }

    /**
     * A native method that is implemented by the 'native-lib' native library,
     * which is packaged with this application.
     */
    public native String stringFromJNI();
}
