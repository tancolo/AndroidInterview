package view.custom.shrimpcolo.com.customview;

import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.util.Pair;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jakewharton.rxbinding.view.RxView;

import java.util.HashMap;

import rx.Observable;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;

import static android.text.TextUtils.isEmpty;

public class FlowLayoutActivity extends AppCompatActivity {

    private FlowLayout mFlowLayoutPink;
    private FlowLayout mFlowLayoutYellow;
    private LayoutInflater mLayoutInflater;

    private String[] mVals = new String[]
            {"Do", "one thing", "at a time", "and do well.", "Never", "forget",
                    "to say", "thanks.", "Keep on", "going ", "never give up.", "Do"};

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
                        //Log.d("aaa", "view.hashcode: " + view.hashCode());
                        mFlowLayoutYellow.addView(view);
                    }
                })
                .flatMap(new Func1<View, Observable<Pair<Integer, String>>>() {
                    @Override
                    public Observable<Pair<Integer, String>> call(final View view) {
                        return RxView.clicks(view)
                                .map(new Func1<Void, Pair<Integer, String>>() {
                                    @Override
                                    public Pair<Integer, String> call(Void aVoid) {
                                        view.setEnabled(false);
                                        view.setBackgroundResource(R.drawable.flow_layout_disable_bg);
                                        //Log.d("aaa", "Clicked View's HashCode: " + view.hashCode());

                                        return getItemViewPair(view);
//                                        Pair<Integer, String> pair = getItemViewPair(view);
//                                        Log.d("aaa", "click view's hashCode: " + pair.first + ", name: " + pair.second);
//                                        return pair;
                                    }
                                });
                    }
                })  // Observable<Pair<Integer, String>>
                .map(new Func1<Pair<Integer, String>, View>() {
                    @Override
                    public View call(Pair<Integer, String> itemViewPair) {
                        return createItemViewByPair(itemViewPair);
                    }
                })  // Observable<View> clicked view
                .doOnNext(new Action1<View>() {
                    @Override
                    public void call(View view) {
                        ImageView imageView = (ImageView) view.findViewById(R.id.iv);
                        imageView.setVisibility(View.VISIBLE);
                        mFlowLayoutPink.addView(view);
                    }
                })
                .flatMap(new Func1<View, Observable<View>>() {
                    @Override
                    public Observable<View> call(final View view) {
                        return RxView.clicks(view)
                                .map(new Func1<Void, View>() {
                                    @Override
                                    public View call(Void aVoid) {
                                        //Log.e("aaa", "Click Pink's View: getTag(): " + view.getTag());
                                        return view;
                                    }
                                });
                    }
                })
                .doOnNext(new Action1<View>() {
                    @Override
                    public void call(View view) {
                        mFlowLayoutPink.removeView(view);
                    }
                })
                .map(new Func1<View, View>() {
                    @Override
                    public View call(View view) {
                        //Log.e("aaa", "==> Clicked Pink's View: getTag(): " + view.getTag());
                        return findViewInYellow(view);
                    }
                }).subscribe(new Action1<View>() {
                    @Override
                    public void call(View view) {
                        if (view != null) {
                            view.setEnabled(true);
                            view.setBackgroundResource(R.drawable.flow_layout_bg);
                        }
                    }
                });
    }

    private Pair<Integer,String> getItemViewPair(View view) {
        if (view == null) return null;

        TextView nameTxt = (TextView) view.findViewById(R.id.tv);
        String name = nameTxt.getText().toString();
        return Pair.create(view.hashCode(), name);//使用hashCode
    }

    private View createItemViewByPair(Pair<Integer, String> itemViewPair) {
        //Log.d("aaa", "createItemViewByPair==》 hashCode: " + itemViewPair.first + ", name: " + itemViewPair.second);
        RelativeLayout itemView = (RelativeLayout) mLayoutInflater.inflate(R.layout.flow_layout, mFlowLayoutYellow, false);
        itemView.setTag(itemViewPair.first);

        TextView textView = (TextView) itemView.findViewById(R.id.tv);
        textView.setText(itemViewPair.second);

        //Log.d("aaa", "createItemViewByPair==》 itemView.getTag(): " + itemView.getTag() + ", name: " + textView.getText().toString());
        return itemView;
    }

    @Nullable
    private View findViewInYellow(View view) {
        Pair<Integer, String> viewPair = getItemViewPairByTag(view);
        //Log.d("aaa", "findViewInYellow: hashCode: " + viewPair.first + ", name: " + viewPair.second);
        if (isEmpty(viewPair.second)) return null;

        for(int index = 0; index < mFlowLayoutYellow.getChildCount(); index++) {
            View itemView = mFlowLayoutYellow.getChildAt(index);
            Pair<Integer, String> targetViewPair = getItemViewPair(itemView);
            if (viewPair.first.intValue() == targetViewPair.first.intValue()) {
                //Log.d("aaa", "Finded the View");
                return itemView;
            }
        }

        return null;
    }

    private Pair<Integer,String> getItemViewPairByTag(View view) {
        if (view == null) return null;

        TextView nameTxt = (TextView) view.findViewById(R.id.tv);
        String name = nameTxt.getText().toString();
        return Pair.create((Integer)view.getTag(), name);
    }

    private Pair<Integer, String> getItemName(View view) {
        if (view == null) return null;

        TextView nameTxt = (TextView) view.findViewById(R.id.tv);
        String name = nameTxt.getText().toString();
        return Pair.create(view.hashCode(), name);

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
