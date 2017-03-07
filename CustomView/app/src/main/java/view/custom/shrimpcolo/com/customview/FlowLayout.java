package view.custom.shrimpcolo.com.customview;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Johnny Tam on 2017/3/5.
 */

public class FlowLayout  extends ViewGroup{
    private final static String TAG = FlowLayout.class.getSimpleName();

    public FlowLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        // 获得此ViewGroup上级容器为其推荐的宽和高，以及计算模式
        int sizeWidth = MeasureSpec.getSize(widthMeasureSpec);
        int sizeHeight = MeasureSpec.getSize(heightMeasureSpec);

        int modeWidth = MeasureSpec.getMode(widthMeasureSpec);
        int modeHeight = MeasureSpec.getMode(heightMeasureSpec);

//        Log.e(TAG, "TANHQ===> onMeasure: sizeWidth: " + sizeWidth + ", sizeHeight: " + sizeHeight
//        + ", modeWidth: " + modeWidth + ", modeHeight: " + modeHeight);

        // 用于warp_content情况下，来记录父view宽和高
        int width = 0;
        int height = 0;

        int lineWidth = 0;  // 取每一行宽度的最大值
        int lineHeight = 0; // 每一行的高度累加

        // 获得子view的个数
        int cCount = getChildCount();

        for (int i = 0; i < cCount; i++) {
            View child = getChildAt(i);

            // 测量子View的宽和高（子view在布局文件中是wrap_content）
            measureChild(child, widthMeasureSpec, heightMeasureSpec);
            MarginLayoutParams lp = (MarginLayoutParams) child.getLayoutParams(); // 得到LayoutParams

            // 根据测量宽度加上Margin值算出子view的实际宽度（上文中有说明）
            int childWidth = child.getMeasuredWidth() + lp.leftMargin + lp.rightMargin;

            // 根据测量高度加上Margin值算出子view的实际高度
            int childHeight = child.getMeasuredHeight() + lp.topMargin+ lp.bottomMargin;

            // 这里的父view是有padding值的，如果再添加一个元素就超出最大宽度就换行
            if (lineWidth + childWidth > sizeWidth - getPaddingLeft() - getPaddingRight()) {
                width = Math.max(width, lineWidth); // 父view宽度=以前父view宽度、当前行宽的最大值
                lineWidth = childWidth; // 换行了，当前行宽=第一个view的宽度
                height += lineHeight; // 父view的高度=各行高度之和
                lineHeight = childHeight; //换行了，当前行高=第一个view的高度
            } else {
                lineWidth += childWidth; // 叠加行宽
                lineHeight = Math.max(lineHeight, childHeight); // 得到当前行最大的高度
            }
            // 最后一个控件
            if (i == cCount - 1) {
                width = Math.max(lineWidth, width);
                height += lineHeight;
            }
        }
        /**
         * EXACTLY对应match_parent 或具体值
         * AT_MOST对应wrap_content
         * 在FlowLayout布局文件中
         * android:layout_width="fill_parent"
         * android:layout_height="wrap_content"
         *
         * 如果是MeasureSpec.EXACTLY则直接使用父ViewGroup传入的宽和高，否则设置为自己计算的宽和高。
         */
        setMeasuredDimension(
                (modeWidth == MeasureSpec.EXACTLY) ? sizeWidth : (width + getPaddingLeft() + getPaddingRight()),
                (modeHeight == MeasureSpec.EXACTLY) ? sizeHeight : (height + getPaddingTop()+ getPaddingBottom())
        );
    }

    private List<List<View>> mAllViews = new ArrayList<>(); //存储所有的View
    private List<Integer> mLineHeight = new ArrayList<>(); //存储每一行的高度
    //private List<View> mLineViews = new ArrayList<>(); //存储每一行的view

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        //mLineViews.clear();
        mAllViews.clear();
        mLineHeight.clear();
        //Log.e(TAG, "TANHQ===> onLayout!");

        int width = getWidth(); // 当前ViewGroup的宽度

        int lineWidth = 0;
        int lineHeight = 0;

        List<View> lineViews = new ArrayList<>(); // 存储每一行所有的childView

        int cCount = getChildCount();

        for (int i = 0; i < cCount; i++) {
            View child = getChildAt(i);
            MarginLayoutParams lp = (MarginLayoutParams) child.getLayoutParams();

            int childWidth = child.getMeasuredWidth();
            int childHeight = child.getMeasuredHeight();

            // 换行，在onMeasure中childWidth是加上Margin值的
            if (lineWidth + childWidth + lp.leftMargin + lp.rightMargin > width - getPaddingLeft() - getPaddingRight()) {
                mLineHeight.add(lineHeight); // 记录行高
                mAllViews.add(lineViews); // 记录当前行的Views
                //mAllViews.add(mLineViews); // 记录当前行的Views

                // 新行的行宽和行高
                lineWidth = 0;
                lineHeight = childHeight + lp.topMargin + lp.bottomMargin;
                lineViews = new ArrayList<>(); // 新行的View集合
                //mLineViews.clear();
            }
            //不管换不换行都要处理当前子view
            lineWidth += childWidth + lp.leftMargin + lp.rightMargin;
            lineHeight = Math.max(lineHeight, childHeight + lp.topMargin+ lp.bottomMargin);
            lineViews.add(child);
            //mLineViews.add(child);
        }
        // 处理最后一行
        mLineHeight.add(lineHeight);
        mAllViews.add(lineViews);
        //mAllViews.add(mLineViews);

        // 设置子View的位置
        int left = getPaddingLeft();
        int top = getPaddingTop();

        // 行数
        int lineNum = mAllViews.size();

        for (int i = 0; i < lineNum; i++) {
            // 当前行的所有的View
            lineViews = mAllViews.get(i);
            //mLineViews = mAllViews.get(i);
            lineHeight = mLineHeight.get(i);

            for (int j = 0; j < lineViews.size(); j++) {
            //for (int j = 0; j < mLineViews.size(); j++) {
              //  View child = mLineViews.get(j);
                View child = lineViews.get(j);
                // 判断child的状态
                if (child.getVisibility() == View.GONE) {
                    continue;
                }
                MarginLayoutParams lp = (MarginLayoutParams) child.getLayoutParams();

                int lc = left + lp.leftMargin;
                int tc = top + lp.topMargin;
                int rc = lc + child.getMeasuredWidth();
                int bc = tc + child.getMeasuredHeight();

                // 为子View进行布局
                child.layout(lc, tc, rc, bc);

                left += child.getMeasuredWidth() + lp.leftMargin+ lp.rightMargin;
            }
            left = getPaddingLeft() ;
            top += lineHeight ;
        }
    }

    /**
     * 因为我们只需要支持margin，所以直接使用系统的MarginLayoutParams
     */
    @Override
    public LayoutParams generateLayoutParams(AttributeSet attrs) {
        return new MarginLayoutParams(getContext(), attrs);
    }
}
