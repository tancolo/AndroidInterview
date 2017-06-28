package com.angine.www.layoutinflaterdemo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

/**
 * Created by Johnny Tam on 2017/6/27.
 */

public class InflateAdapter extends BaseAdapter {

    private LayoutInflater mInflater = null;

    public InflateAdapter(Context context) {
        mInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return 8;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //说明：这里是测试inflate方法参数代码，不再考虑性能优化等TAG处理
        return getXmlToView(convertView, position, parent);
    }

    private View getXmlToView(View convertView, int position, ViewGroup parent) {
        View[] viewList = {
                mInflater.inflate(R.layout.textview_layout, null),
//                mInflater.inflate(R.layout.textview_layout, parent),
                mInflater.inflate(R.layout.textview_layout, parent, false),
//                mInflater.inflate(R.layout.textview_layout, parent, true),
                mInflater.inflate(R.layout.textview_layout, null, true),
                mInflater.inflate(R.layout.textview_layout, null, false),

                mInflater.inflate(R.layout.textview_layout_parent, null),
//                mInflater.inflate(R.layout.textview_layout_parent, parent),
                mInflater.inflate(R.layout.textview_layout_parent, parent, false),
//                mInflater.inflate(R.layout.textview_layout_parent, parent, true),
                mInflater.inflate(R.layout.textview_layout_parent, null, true),
                mInflater.inflate(R.layout.textview_layout_parent, null, false),
        };

        convertView = viewList[position];

        return convertView;
    }

}
