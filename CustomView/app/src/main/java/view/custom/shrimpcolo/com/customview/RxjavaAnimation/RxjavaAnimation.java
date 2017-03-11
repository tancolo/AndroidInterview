package view.custom.shrimpcolo.com.customview.RxjavaAnimation;

import android.animation.ValueAnimator;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jakewharton.rxbinding.view.RxView;

import oxim.digital.rxanim.RxAnimationBuilder;
import oxim.digital.rxanim.RxObservableValueAnimator;
import oxim.digital.rxanim.RxValueAnimator;
import rx.Observable;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import view.custom.shrimpcolo.com.customview.FlowLayout;
import view.custom.shrimpcolo.com.customview.R;

public class RxjavaAnimation extends AppCompatActivity {

    private static final String TAG = "aaa";
    private FlowLayout mFlowLayoutPink;
    private FlowLayout mFlowLayoutTransparent;
    private LayoutInflater mLayoutInflater;
    private View mMiddleView;
    private FlowLayout mFlowLayoutStub;

    private String[] stringArray = new String[]
            {"Do", "one thing", "at a time", "and do well.", "Never", "forget",
                    "to say", "thanks.", "Keep on", "going ", "never give up.", "Do"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rxjava_animation);

        mFlowLayoutPink = (FlowLayout)findViewById(R.id.flowlayout_pink);
        mFlowLayoutTransparent = (FlowLayout)findViewById(R.id.flowlayout_yellow);
        mMiddleView = findViewById(R.id.middle_view);
        mFlowLayoutStub = (FlowLayout)findViewById(R.id.flowlayout_stub);

        mLayoutInflater = LayoutInflater.from(this);
        initFlowlayoutWithRxJava();
    }

    private void initFlowlayoutWithRxJava() {
        Observable<View> itemViews = Observable.from(stringArray)
                .map(new Func1<String, View>() {
                    @Override
                    public View call(String name) {
                        return createItemView(name);
                    }
                });

        Subscription subscription = itemViews
                .doOnNext(new Action1<View>() {
                    @Override
                    public void call(View view) {
                        addView(view);
                    }
                })
                .flatMap(new Func1<View, Observable<View>>() {
                    @Override
                    public Observable<View> call(View view) {
                        return touchView(view);
                    }
                })
                .subscribe(new Action1<View>() {
                    @Override
                    public void call(View view) {
                        move(view);
                    }
                });
    }

    private void addView(View view) {
        mFlowLayoutTransparent.addView(view);
    }

    private void move(final View view) {
        RxAnimationBuilder.animate(view, 2000)
                .translateY(1100)
                .schedule(false)
                .subscribe();

//        Direction direction = (Direction) view.getTag();
//        int xValue = 0;
//        int yValue = 0;
//
//        if(direction != Direction.NONE) {
//            if(direction == Direction.DOWN) {
//                xValue = view.getLeft();
//                yValue = mFlowLayoutTransparent.getHeight() - mFlowLayoutStub.getHeight() + view.getTop();
//            } else if(direction == Direction.UP) {
//                xValue = view.getLeft();
//                yValue = view.getTop();
//            }
//
////            view.animate().setDuration(1000);
////            view.animate().x(xValue).y(yValue);
//        }
    }

    private Observable<View> touchView(final View view) {
        return RxView.touches(view).map(new Func1<MotionEvent, View>() {
            @Override
            public View call(MotionEvent motionEvent) {
                return buildView(motionEvent, view);
            }
        });
    }

    private View buildView(MotionEvent motionEvent, View view) {
        Direction direction = getMoveDirection(motionEvent);
        view.setTag(direction);//存储view滑动方向
        return view;
    }

    private void viewMoving(View view) {
        Direction direction = (Direction) view.getTag();
        int xValue = 0;
        int yValue = 0;

        if(direction != Direction.NONE) {
            if(direction == Direction.DOWN) {
                xValue = view.getLeft();
                yValue = mFlowLayoutTransparent.getHeight() - mFlowLayoutStub.getHeight() + view.getTop();
            } else if(direction == Direction.UP) {
                xValue = view.getLeft();
                yValue = view.getTop();
            }

            view.animate().setDuration(1000);
            view.animate().x(xValue).y(yValue);
        }
    }

    private View createItemView(String name) {
        RelativeLayout itemView = (RelativeLayout) mLayoutInflater.inflate(R.layout.flow_layout, mFlowLayoutTransparent, false);
        TextView textView = (TextView) itemView.findViewById(R.id.tv);
        textView.setText(name);

        return itemView;
    }

    private Direction getMoveDirection(MotionEvent event) {
        //获取当前坐标
        int y = (int)event.getY();
        int vertical_Y1 = 0;
        int vertical_Y2 = 0;

        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                vertical_Y1 = y;
                break;
            case MotionEvent.ACTION_UP:
                vertical_Y2 = y;

                //Log.i(TAG,"滑动参值 vertical_Y1 ="+ vertical_Y1 +"; vertical_Y2 =" + vertical_Y2);
                int DISTANCE = 80;
                if(vertical_Y2 - vertical_Y1 > DISTANCE) { //向下滑动
                    return Direction.DOWN;
                } else if(vertical_Y1 - vertical_Y2 > DISTANCE) { //向上滑动
                    return Direction.UP;
                }
                break;
            default:
                break;
        }

        return Direction.NONE;
    }
    enum Direction {
        DOWN,
        UP,
        LEFT,
        RIGHT,
        NONE
    }

}
