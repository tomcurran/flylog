    package org.tomcurran.logbook.ui.fragments;
    
    import java.lang.ref.WeakReference;
    
    import org.tomcurran.logbook.R;
    import org.tomcurran.logbook.util.NotifyingAsyncQueryHandler;
    
    import android.app.AlertDialog;
    import android.app.Dialog;
    import android.content.ContentUris;
    import android.content.ContentValues;
    import android.content.DialogInterface;
    import android.content.DialogInterface.OnClickListener;
    import android.net.Uri;
    import android.os.Bundle;
    import android.provider.BaseColumns;
    import android.support.v4.app.DialogFragment;
    import android.support.v4.app.FragmentActivity;
    import android.text.TextUtils;
    import android.view.LayoutInflater;
    import android.view.View;
    import android.widget.EditText;
    
    public abstract class BaseDialogFragment extends DialogFragment implements NotifyingAsyncQueryHandler.AsyncInsertListener, NotifyingAsyncQueryHandler.AsyncUpdateListener {
    
        protected static final int STATE_INSERT = 0;
        protected static final int STATE_EDIT = 1;
    
        protected int mState;
        protected Uri mUri;
        private int mViewResource;
        private View mCustomView;
        private NotifyingAsyncQueryHandler mHandler;
        private WeakReference<BaseDialogSuccessListener> mOnSuccessListener;
        private OnClickListener mOnClickListener = new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                switch (whichButton) {
                case DialogInterface.BUTTON_POSITIVE:
                    onPositiveButtonClick(dialog);
                    break;
                case DialogInterface.BUTTON_NEGATIVE:
                    onNegativeButtonClick(dialog);
                    break;
                }
            }
        };
    
        public BaseDialogFragment(Uri uri) {
            this(uri, R.layout.fragment_dialog_single);
        }
    
        public BaseDialogFragment(Uri uri, int viewResource) {
            mUri = uri;
            mViewResource = viewResource;
        }
    
        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            mHandler = new NotifyingAsyncQueryHandler(getActivity().getContentResolver());
            mHandler.setInsertListener(this);
            mHandler.setUpdateListener(this);
    
            Bundle args = getArguments();
            long id = args.getLong(BaseColumns._ID, 0);
            if (id == 0) {
                mState = STATE_INSERT;
            } else {
                mUri = ContentUris.withAppendedId(mUri, id);
                mState = STATE_EDIT;
            }
        }
    
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            FragmentActivity activity = (FragmentActivity) getActivity();
    
            mCustomView = LayoutInflater.from(activity).inflate(mViewResource, null);
            AlertDialog.Builder builder = new AlertDialog.Builder(activity);
    
            builder.setView(mCustomView);
            builder.setPositiveButton(R.string.dialog_button_base_positive, mOnClickListener);
            builder.setNegativeButton(R.string.dialog_button_base_negative, mOnClickListener);
    
            setupView(builder, mCustomView);
    
            return builder.create();
        }
    
        public void setupView(AlertDialog.Builder builder, View view) {
            if (mState == STATE_INSERT) {
                 builder.setTitle(R.string.dialog_title_base_add);
             } else if (mState == STATE_EDIT) {
                builder.setTitle(R.string.dialog_title_base_edit);
                builder.setNeutralButton(R.string.dialog_button_base_neutral, mOnClickListener);
            }
        }
    
        public void onPositiveButtonClick(DialogInterface dialog) {
            if (validInputValues(dialog) == true) {
                if (mState == STATE_INSERT) {
                    mHandler.startInsert(mUri, getInputValues(dialog));
                } else if (mState == STATE_EDIT) {
                    mHandler.startUpdate(mUri, getInputValues(dialog));
                }
            }
        }
        public void onNegativeButtonClick(DialogInterface dialog) {}
    
        protected boolean validInputValues(DialogInterface dialog) {
            if (!TextUtils.isEmpty(
                    ((EditText)((AlertDialog)dialog).findViewById(R.id.dialog_text)).getText().toString())) {
                return true;
            } else {
                return false;
            }
        }
    
        protected abstract ContentValues getInputValues(DialogInterface dialog);
    
    
        public interface BaseDialogSuccessListener {
            void onDialogSuccess(Long rowId);
        }
    
        public BaseDialogFragment setOnSuccessListener(BaseDialogSuccessListener listener) {
            mOnSuccessListener = new WeakReference<BaseDialogSuccessListener>(listener);
            return this;
        }
        
        protected void dispatchOnSuccessListener() {
            final BaseDialogSuccessListener listener = mOnSuccessListener == null ? null : mOnSuccessListener.get();
            if (listener != null) {
                listener.onDialogSuccess(ContentUris.parseId(mUri));
            }
        }
    
        @Override
        public void onInsertComplete(Uri uri) {
            mUri = uri;
            mState = STATE_EDIT;
            dispatchOnSuccessListener();
        }
    
        @Override
        public void onUpdateComplete(int result) {
            dispatchOnSuccessListener();
        }
    
    }