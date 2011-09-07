package org.tomcurran.logbook.ui.fragments;

import java.lang.ref.WeakReference;

import android.content.AsyncQueryHandler;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.net.Uri;

public class BaseDialogQueryHandler extends AsyncQueryHandler {

    private WeakReference<BaseDialogQueryListener> mListener;

    public interface BaseDialogQueryListener {
        void onInsertComplete(Uri uri);
        void onUpdateComplete();
    }

    public BaseDialogQueryHandler(ContentResolver resolver, BaseDialogQueryListener listener) {
        super(resolver);
        setQueryListener(listener);
    }

    public void setQueryListener(BaseDialogQueryListener listener) {
        mListener = new WeakReference<BaseDialogQueryListener>(listener);
    }

    public void clearQueryListener() {
        mListener = null;
    }

    public void startUpdate(Uri uri, ContentValues values) {
        startUpdate(-1, null, uri, values, null, null);
    }

    public void startInsert(Uri uri, ContentValues values) {
        startInsert(-1, null, uri, values);
    }

    @Override
    protected void onInsertComplete(int token, Object cookie, Uri uri) {
        final BaseDialogQueryListener listener = mListener == null ? null : mListener.get();
        if (listener != null) {
            listener.onInsertComplete(uri);
        }
    }

    @Override
    protected void onUpdateComplete(int token, Object cookie, int result) {
        final BaseDialogQueryListener listener = mListener == null ? null : mListener.get();
        if (listener != null) {
            listener.onUpdateComplete();
        }
    }

}