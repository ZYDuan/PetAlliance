package com.example.yd.sockettest1.ui;

import android.content.Context;
import android.util.AttributeSet;

/**
 * Created by 张YD on 2017/8/22.
 */

public class MarqueTextView extends android.support.v7.widget.AppCompatTextView {

    public MarqueTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public MarqueTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MarqueTextView(Context context) {
        super(context);
    }

    @Override

    public boolean isFocused() {
        return true;
    }
}
