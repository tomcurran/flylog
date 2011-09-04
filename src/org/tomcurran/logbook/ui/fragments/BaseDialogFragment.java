package org.tomcurran.logbook.ui.fragments;

import org.tomcurran.logbook.R;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.provider.BaseColumns;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;

public abstract class BaseDialogFragment extends DialogFragment {

    protected Long mRowId;
	private int mViewResource;
	private BaseDialogFragment.OnSuccessListener mOnSuccessListener;
    private OnClickListener mOnClickListener = new DialogInterface.OnClickListener() {
		public void onClick(DialogInterface dialog, int whichButton) {
        	switch (whichButton) {
        	case DialogInterface.BUTTON_POSITIVE:
        		onPositiveButtonClick(dialog);
        		break;
        	case DialogInterface.BUTTON_NEUTRAL:
        		onNeutralButtonClick(dialog);
        		break;
        	case DialogInterface.BUTTON_NEGATIVE:
        		onNegativeButtonClick(dialog);
        		break;
        	}
        }
	};

	public interface OnSuccessListener {
		void onSuccess(Long rowId);
	}

	public BaseDialogFragment() {
		this(R.layout.fragment_dialog_single);
	}

	public BaseDialogFragment(int viewResource) {
		mViewResource = viewResource;
	}

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
    	Bundle args = getArguments();
        FragmentActivity activity = getActivity();

        if (args != null) {
	        long id = args.getLong(BaseColumns._ID, 0);
	        mRowId = id != 0 ? id : null;
        } else {
        	mRowId = null;
        }

        View view = LayoutInflater.from(activity).inflate(mViewResource, null);
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);

        builder.setView(view);
        builder.setPositiveButton(R.string.dialog_button_base_positive, mOnClickListener);
        builder.setNegativeButton(R.string.dialog_button_base_negative, mOnClickListener);

        setupView(builder, view);

        return builder.create();
    }
    
    public void setOnSuccessListener(OnSuccessListener listener) {
    	mOnSuccessListener = listener;
    }
    
    protected void dispatchOnSuccessListener() {
    	if (mOnSuccessListener != null) {
    		mOnSuccessListener.onSuccess(mRowId);
    	}
    }

    public void setupView(AlertDialog.Builder builder, View view) {
    	if (mRowId != null) {
    		builder.setTitle(R.string.dialog_title_base_edit);
    	    builder.setNeutralButton(R.string.dialog_button_base_neutral, mOnClickListener);
    	} else {
    		builder.setTitle(R.string.dialog_title_base_add);
    	}
    }
    public void onPositiveButtonClick(DialogInterface dialog) {}
    public void onNeutralButtonClick(DialogInterface dialog) {}
    public void onNegativeButtonClick(DialogInterface dialog) {}
}
