package com.zhy.autolayout.attr;

import android.view.View;

import com.zhy.autolayout.utils.AutoUtils;
import com.zhy.autolayout.utils.L;

public abstract class AutoAttr {
    public static final int BASE_WIDTH = 1;
    public static final int BASE_HEIGHT = 2;
    public static final int BASE_DEFAULT = 3;

    protected int pxVal;
    protected int baseWidth;
    protected int baseHeight;

    public AutoAttr(int pxVal, int baseWidth, int baseHeight) {
        this.pxVal = pxVal;
        this.baseWidth = baseWidth;
        this.baseHeight = baseHeight;
    }

    public void apply(View view) {
        boolean log = view.getTag() != null && view.getTag().toString().equals("auto");
        if (log) {
            L.e("pxVal = " + pxVal + " ," + getClass().getSimpleName());
        }
        int val;
        if (useDefault()) {
            val = defaultBaseWidth() ? getPercentWidthSize() : getPercentHeightSize();
            if (log) {
                L.e("useDefault val = " + val);
            }
        } else if (baseWidth()) {
            val = getPercentWidthSize();
            if (log) {
                L.e("baseWidth val = " + val);
            }
        } else {
            val = getPercentHeightSize();
            if (log) {
                L.e("baseHeight val = " + val);
            }
        }
        if (val > 0) {
            val = Math.max(val, 1);// for very thin divider
        }
        execute(view, val);
    }

    protected boolean useDefault() {
        return !contains(baseHeight, attrVal()) && !contains(baseWidth, attrVal());
    }

    protected boolean baseWidth() {
        return contains(baseWidth, attrVal());
    }

    protected boolean contains(int baseVal, int flag) {
        return (baseVal & flag) != 0;
    }

    protected int getPercentWidthSize() {
        return AutoUtils.getPercentWidthSizeBigger(pxVal);
    }

    protected int getPercentHeightSize() {
        return AutoUtils.getPercentHeightSizeBigger(pxVal);
    }

    @Override
    public String toString() {
        return "AutoAttr{" +
                "pxVal = " + pxVal +
                ", baseWidth = " + baseWidth() +
                ", defaultBaseWidth = " + defaultBaseWidth() +
                '}';
    }

    protected abstract int attrVal();

    protected abstract boolean defaultBaseWidth();

    protected abstract void execute(View view, int val);
}
