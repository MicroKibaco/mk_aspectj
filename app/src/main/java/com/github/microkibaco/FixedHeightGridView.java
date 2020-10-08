package com.github.microkibaco;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.GridView;

/**
 * @author 杨正友(小木箱)于 2020/10/8 13 26 创建
 * @Email: yzy569015640@gmail.com
 * @Tel: 18390833563
 * @function description:
 */
public class FixedHeightGridView extends GridView {
    public FixedHeightGridView(Context context, AttributeSet attrs) {
        super(context, attrs);
        // TODO Auto-generated constructor stub
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        // TODO Auto-generated method stub
        int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2,

                MeasureSpec.AT_MOST);

        super.onMeasure(widthMeasureSpec, expandSpec);
    }

}