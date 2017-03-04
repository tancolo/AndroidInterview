package view.custom.shrimpcolo.com.customview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.LinearLayout;

/**
 * Created by Johnny Tam on 2017/3/4.
 */

public class MyLinearLayout extends LinearLayout {
    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        //return super.onInterceptTouchEvent(ev);
        return false;
    }

    public MyLinearLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }
}
