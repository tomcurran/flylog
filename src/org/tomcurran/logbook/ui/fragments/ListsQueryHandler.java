package org.tomcurran.logbook.ui.fragments;

import java.lang.ref.WeakReference;

import android.content.AsyncQueryHandler;
import android.content.ContentResolver;
import android.net.Uri;

public class ListsQueryHandler extends AsyncQueryHandler {

    private WeakReference<ListsQueryListener> mListener;

    public interface ListsQueryListener {
        void onDeleteComplete();
    }

    public ListsQueryHandler(ContentResolver resolver, ListsQueryListener listener) {
        super(resolver);
        setQueryListener(listener);
    }

    public void setQueryListener(ListsQueryListener listener) {
        mListener = new WeakReference<ListsQueryListener>(listener);
    }

    public void clearQueryListener() {
        mListener = null;
    }

    public void startDelete(Uri uri) {
        startDelete(-1, null, uri, null, null);
    }

    @Override
    protected void onDeleteComplete(int token, Object cookie, int result) {
        final ListsQueryListener listener = mListener == null ? null : mListener.get();
        if (listener != null) {
            listener.onDeleteComplete();
        }
    }

}