package view.custom.shrimpcolo.com.customview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class FlowLayoutActivity extends AppCompatActivity {

    private FlowLayout mFlowLayout1;
    private FlowLayout mFlowLayout2;
    private LayoutInflater mLayoutInflater;

    private String[] mVals = new String[]
            {"Do", "one thing", "at a time", "and do well.", "Never", "forget",
                    "to say", "thanks.", "Keep on", "going ", "never give up."};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flow_layout);

        mFlowLayout1 = (FlowLayout)findViewById(R.id.flowlayout1);
        mFlowLayout2 = (FlowLayout)findViewById(R.id.flowlayout2);
        mLayoutInflater = LayoutInflater.from(this);
        initFlowlayout2();
    }

    public void initFlowlayout2() {
        for (int i = 0; i < mVals.length; i++) {
            final RelativeLayout rl2 = (RelativeLayout) mLayoutInflater.inflate(R.layout.flow_layout, mFlowLayout2, false);
            TextView tv2 = (TextView) rl2.findViewById(R.id.tv);
            tv2.setText(mVals[i]);
            rl2.setTag(i);
            mFlowLayout2.addView(rl2);
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
        RelativeLayout rl1 = (RelativeLayout) mLayoutInflater.inflate(R.layout.flow_layout, mFlowLayout1, false);
        ImageView iv = (ImageView) rl1.findViewById(R.id.iv);
        iv.setVisibility(View.VISIBLE);
        TextView tv1 = (TextView) rl1.findViewById(R.id.tv);
        tv1.setText(mVals[i]);
        rl1.setTag(i);
        mFlowLayout1.addView(rl1);
        rl1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int i = (int) v.getTag();
                mFlowLayout1.removeView(v);
                View view = mFlowLayout2.getChildAt(i);
                view.setClickable(true);
                view.setBackgroundResource(R.drawable.flow_layout_bg);
            }
        });
    }
}
