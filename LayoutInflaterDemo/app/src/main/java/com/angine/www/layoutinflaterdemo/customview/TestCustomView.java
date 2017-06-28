package com.angine.www.layoutinflaterdemo.customview;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.angine.www.layoutinflaterdemo.R;

public class TestCustomView extends AppCompatActivity {

    private static final String TAG = TestCustomView.class.getSimpleName();

    private ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_custom_view);

        imageView = (ImageView) findViewById(R.id.img_launcher);
        Log.d(TAG, "\n\n 1111 onCreate");
        Log.d(TAG, "width: " + imageView.getWidth() + ", height: " + imageView.getHeight());
        Log.d(TAG, "MeasureWidth: " + imageView.getMeasuredWidth()
                + ", MeasureHeight: " + imageView.getMeasuredWidth());

        Button button = (Button) findViewById(R.id.btn_click);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "\n\n 2222 onClick");
                Log.d(TAG, "width: " + imageView.getWidth() + ", height: " + imageView.getHeight());
                Log.d(TAG, "MeasureWidth: " + imageView.getMeasuredWidth()
                        + ", MeasureHeight: " + imageView.getMeasuredWidth());
            }
        });

        imageView.post(new Runnable() {
            @Override
            public void run() {
                Log.d(TAG, "\n\n 3333 post");
                Log.d(TAG, "width: " + imageView.getWidth() + ", height: " + imageView.getHeight());
                Log.d(TAG, "MeasureWidth: " + imageView.getMeasuredWidth()
                        + ", MeasureHeight: " + imageView.getMeasuredWidth());
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();

        Log.d(TAG, "\n\n 4444 onResume");
        Log.d(TAG, "width: " + imageView.getWidth() + ", height: " + imageView.getHeight());
        Log.d(TAG, "MeasureWidth: " + imageView.getMeasuredWidth()
                + ", MeasureHeight: " + imageView.getMeasuredWidth());
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);

        Log.d(TAG, "\n\n 5555 onWindowFocusChanged");
        Log.d(TAG, "width: " + imageView.getWidth() + ", height: " + imageView.getHeight());
        Log.d(TAG, "MeasureWidth: " + imageView.getMeasuredWidth()
                + ", MeasureHeight: " + imageView.getMeasuredWidth());

    }
}
