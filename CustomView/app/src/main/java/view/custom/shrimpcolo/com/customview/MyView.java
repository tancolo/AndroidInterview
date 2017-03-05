package view.custom.shrimpcolo.com.customview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by Johnny Tam on 2017/3/5.
 */

public class MyView extends View{

    Paint paint = new Paint();
    public float currentX = 50;
    public float currentY = 50;
    public int textColor;
    public String text;

    public MyView(Context context, AttributeSet attrs) {
        super(context, attrs);

        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.ball_view);
        textColor = array.getColor(R.styleable.ball_view_TextColor, Color.BLACK);
        text = array.getString(R.styleable.ball_view_Text);
        textColor = Color.RED;
        Log.e("TANHQ", "textColor = " + textColor);
        Log.e("TANHQ", "text = " + text);
        array.recycle();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        paint.setColor(Color.BLUE);
        canvas.drawCircle(currentX, currentY, 30, paint);
        paint.setColor(textColor);
        canvas.drawText(text, currentX-30, currentX+50, paint);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        currentX = event.getX();
        currentY = event.getY();
        invalidate();

        return true;
        //return super.onTouchEvent(event);
    }
}
