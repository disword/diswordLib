package com.disword.diswordlib.core.util.keyboard;

import android.os.Build;
import android.view.View;
import android.view.ViewTreeObserver;

/**
 * 防止键盘遮挡 要传activity根view
 */

public class KeyboardListener {
    private KeyboardCallback mCallback;
    private ViewTreeObserver.OnGlobalLayoutListener layoutListener;
    private int lastHeight;
    private final View rootView;

    public KeyboardListener(View rootView, KeyboardCallback mCallback) {
        this.mCallback = mCallback;
        this.rootView = rootView;
        rootView.getViewTreeObserver().addOnGlobalLayoutListener(createListener());
    }

    private ViewTreeObserver.OnGlobalLayoutListener createListener() {
        layoutListener = new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                int heightDiff = rootView.getRootView().getHeight() - rootView.getHeight();
                System.out.println("onGlobalLayout = " + heightDiff);
                if (lastHeight == 0)
                    lastHeight = heightDiff;

                if (heightDiff < lastHeight) {
                    lastHeight = heightDiff;
                    mCallback.keyboardHidden();

                } else if (heightDiff > lastHeight) {
                    lastHeight = heightDiff;
                    mCallback.keyboardShown();
                }
            }
        };
        return layoutListener;
    }


    public void release(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            rootView.getViewTreeObserver().removeOnGlobalLayoutListener(layoutListener);
        }
    }

}
