package com.zt.recordmanager.view.widget;

import android.content.Context;
import android.view.KeyEvent;

import com.qmuiteam.qmui.widget.dialog.QMUITipDialog;

public class KeyDownDialog extends QMUITipDialog {

    private onKeyDownListener onKeyDownListener;

    public KeyDownDialog(Context context) {
        super(context);
    }

    public KeyDownDialog(Context context, int themeResId) {
        super(context, themeResId);
    }

    public void setOnKeyDownListener(KeyDownDialog.onKeyDownListener onKeyDownListener) {
        this.onKeyDownListener = onKeyDownListener;
    }

    @Override
    public boolean onKeyDown(int keyCode,  KeyEvent event) {
        return onKeyDownListener.onKeyDown(keyCode,event);
    }

    public interface onKeyDownListener{
        public boolean onKeyDown(int keyCode,  KeyEvent event);
    }
}
