package com.github.microkibaco.mk_aspectj;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.Locale;

/**
 * @author 杨正友(小木箱)于 2020/10/8 13 26 创建
 * @Email: yzy569015640@gmail.com
 * @Tel: 18390833563
 * @function description:
 */
public class SAListViewAdapter extends BaseAdapter {
    private LayoutInflater mLayoutInflater;

    SAListViewAdapter(Context context) {
        mLayoutInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return 5;
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
        if (convertView == null) {
            convertView = mLayoutInflater.inflate(R.layout.item_listview, parent, false);
            ViewHolder holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        }
        ViewHolder holder = (ViewHolder) convertView.getTag();
        holder.title.setText(String.format(Locale.CHINA, "位置 %d", position));

        return convertView;
    }

    private static class ViewHolder {
        ViewHolder(View viewRoot) {
            title = viewRoot.findViewById(R.id.title);
        }

        public TextView title;
    }

}