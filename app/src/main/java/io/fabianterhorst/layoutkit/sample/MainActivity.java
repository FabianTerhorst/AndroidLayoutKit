package io.fabianterhorst.layoutkit.sample;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.util.Arrays;

import io.fabianterhorst.layoutkit.Axis;
import io.fabianterhorst.layoutkit.android.AndroidBaseView;
import io.fabianterhorst.layoutkit.android.LayoutView;
import io.fabianterhorst.layoutkit.layouts.BaseLayout;
import io.fabianterhorst.layoutkit.utils.SizeLayoutBuilder;
import io.fabianterhorst.layoutkit.utils.StackLayoutBuilder;

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
        /*setContentView(
                new LayoutView(this, new SizeLayoutBuilder()
                        .baseView(new CustomBaseView())
                        .size(100f, 100f)
                        .build())
        );*/
        //setContentView(new CustomView(this));

        setContentView(new LayoutView(this, new StackLayoutBuilder()
                .axis(Axis.VERTICAL)
                .subLayouts(Arrays.asList(new SizeLayoutBuilder()
                        .baseView(new AndroidBaseView(new Button(this)))
                        .size(1f, 200f)
                        .layoutConfig(new BaseLayout.LayoutConfig() {
                            @Override
                            public void onConfigure(Object view) {
                                if (view instanceof Button) {
                                    Button button = (Button) view;
                                    button.setText("bla");
                                    button.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {
                                            Log.d("bla", stringFromJNI());
                                        }
                                    });
                                }
                            }
                        })
                        .build(), new SizeLayoutBuilder()
                        .baseView(new AndroidBaseView(new Button(this)))
                        .size(1f, 200f)
                        .layoutConfig(new BaseLayout.LayoutConfig() {
                            @Override
                            public void onConfigure(Object view) {
                                if (view instanceof Button) {
                                    Button button = (Button) view;
                                    button.setText("bla2");
                                    button.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {
                                            Log.d("bla2", stringFromJNI());
                                        }
                                    });
                                }
                            }
                        })
                        .build()
                ))
                .build()));

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
