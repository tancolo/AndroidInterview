package view.custom.shrimpcolo.com.customview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jakewharton.rxbinding.view.RxView;

import rx.Observable;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;


public class FlowLayoutActivity extends AppCompatActivity {

    private FlowLayout mFlowLayoutPink;
    private FlowLayout mFlowLayoutYellow;
    private LayoutInflater mLayoutInflater;

    private String[] mVals = new String[]
            {"Do", "one thing", "at a time", "and do well.", "Never", "forget",
                    "to say", "thanks.", "Keep on", "going ", "never give up."};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flow_layout);

        mFlowLayoutPink = (FlowLayout)findViewById(R.id.flowlayout1);
        mFlowLayoutYellow = (FlowLayout)findViewById(R.id.flowlayout2);
        mLayoutInflater = LayoutInflater.from(this);
//        initFlowlayout2();
        initFlowlayoutWithRxJava();
    }

    private void initFlowlayoutWithRxJava() {
        Observable<View> itemViews = Observable
                .from(mVals)  // Observable<String>
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
                        mFlowLayoutYellow.addView(view);
                    }
                })
                .flatMap(new Func1<View, Observable<String>>() {
                    @Override
                    public Observable<String> call(final View view) {
                        return RxView.clicks(view)
                                .map(new Func1<Void, String>() {
                                    @Override
                                    public String call(Void aVoid) {
                                        return getItemName(view);
                                    }
                                });
                    }
                })  // Observable<String>
                .map(new Func1<String, View>() {
                    @Override
                    public View call(String name) {
                        return createItemView(name);
                    }
                })  // Observable<View> clicked view
                .doOnNext(new Action1<View>() {
                    @Override
                    public void call(View view) {
                        mFlowLayoutPink.addView(view);
                    }
                })
                .flatMap(new Func1<View, Observable<View>>() {
                    @Override
                    public Observable<View> call(final View view) {
                        return RxView.clicks(view).map(new Func1<Void, View>() {
                            @Override
                            public View call(Void aVoid) {
                                return view;
                            }
                        });
                    }
                })
                .subscribe(new Action1<View>() {
                    @Override
                    public void call(View view) {
                        mFlowLayoutPink.removeView(view);
                    }
                });
    }

    private String getItemName(View view) {
        if (view == null) return null;

        TextView nameTxt = (TextView) view.findViewById(R.id.tv);
        return nameTxt.getText().toString();
    }

    private View createItemView(String name) {
        RelativeLayout itemView = (RelativeLayout) mLayoutInflater.inflate(R.layout.flow_layout, mFlowLayoutYellow, false);
        TextView textView = (TextView) itemView.findViewById(R.id.tv);
        textView.setText(name);
        return itemView;
    }

    public void initFlowlayout2() {
        for (int i = 0; i < mVals.length; i++) {
            final RelativeLayout rl2 = (RelativeLayout) mLayoutInflater.inflate(R.layout.flow_layout, mFlowLayoutYellow, false);
            TextView tv2 = (TextView) rl2.findViewById(R.id.tv);
            tv2.setText(mVals[i]);
            rl2.setTag(i);
            mFlowLayoutYellow.addView(rl2);
            rl2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int i = (int) v.getTag();
                    addViewToFlowlayout1(i);
                    rl2.setBackgroundResource(R.drawable.flow_layout_disable_bg);
                    rl2.setClickable(false);
                }
            });
        }
    }

    public void addViewToFlowlayout1(int i){
        RelativeLayout rl1 = (RelativeLayout) mLayoutInflater.inflate(R.layout.flow_layout, mFlowLayoutPink, false);
        ImageView iv = (ImageView) rl1.findViewById(R.id.iv);
        iv.setVisibility(View.VISIBLE);
        TextView tv1 = (TextView) rl1.findViewById(R.id.tv);
        tv1.setText(mVals[i]);
        rl1.setTag(i);
        mFlowLayoutPink.addView(rl1);
        rl1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int i = (int) v.getTag();
                mFlowLayoutPink.removeView(v);
                View view = mFlowLayoutYellow.getChildAt(i);
                view.setClickable(true);
                view.setBackgroundResource(R.drawable.flow_layout_bg);
            }
        });
    }
}
