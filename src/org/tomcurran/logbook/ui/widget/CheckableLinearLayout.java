package org.tomcurran.logbook.ui.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Checkable;
import android.widget.CheckedTextView;
import android.widget.LinearLayout;

public class CheckableLinearLayout extends LinearLayout implements Checkable {

	private CheckedTextView mCheckbox;

    public CheckableLinearLayout(Context context) {
        super(context, null);
    }

    public CheckableLinearLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onFinishInflate() {
    	super.onFinishInflate();
    	final int childCount = getChildCount();
    	for (int i = 0; i < childCount; i++) {
    		final View view = getChildAt(i);
    		if (view instanceof CheckedTextView) {
    			mCheckbox = (CheckedTextView)view;
    			break;
    		}
    	}
    }

    @Override
    public boolean isChecked() {
        return mCheckbox != null ? mCheckbox.isChecked() : false;
    }

    @Override
    public void setChecked(boolean checked) {
        if (mCheckbox != null) {
        	mCheckbox.setChecked(checked);
        }
    }

    @Override
    public void toggle() {
    	if (mCheckbox != null) {
        	mCheckbox.toggle();
        }
    }

}
