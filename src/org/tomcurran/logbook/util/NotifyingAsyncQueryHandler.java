/*
 * Copyright (C) 2009 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.tomcurran.logbook.util;

import android.content.AsyncQueryHandler;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;

import java.lang.ref.WeakReference;

/**
 * Slightly more abstract {@link AsyncQueryHandler} that helps keep a
 * {@link WeakReference} back to a listener. Will properly close any
 * {@link Cursor} if the listener ceases to exist.
 * <p>
 * This pattern can be used to perform background queries without leaking
 * {@link Context} objects.
 *
 * @hide pending API council review
 */
public class NotifyingAsyncQueryHandler extends AsyncQueryHandler {

    private WeakReference<AsyncQueryListener> mQueryListener;
    private WeakReference<AsyncInsertListener> mInsertListener;
    private WeakReference<AsyncUpdateListener> mUpdateListener;
    private WeakReference<AsyncDeleteListener> mDeleteListener;

    /**
     * Interface to listen for completed query operations.
     */
    public interface AsyncQueryListener {
        void onQueryComplete(Cursor cursor);
    }

    public interface AsyncInsertListener {
        void onInsertComplete(Uri uri);
    }

    public interface AsyncUpdateListener {
        void onUpdateComplete(int result);
    }

    public interface AsyncDeleteListener {
        void onDeleteComplete(int result);
    }

    public NotifyingAsyncQueryHandler(ContentResolver resolver) {
        super(resolver);
    }

    /**
     * Assign the given {@link AsyncQueryListener} to receive query events from
     * asynchronous calls. Will replace any existing listener.
     */
    public void setQueryListener(AsyncQueryListener listener) {
        mQueryListener = new WeakReference<AsyncQueryListener>(listener);
    }

    public void setInsertListener(AsyncInsertListener listener) {
        mInsertListener = new WeakReference<AsyncInsertListener>(listener);
    }

    public void setUpdateListener(AsyncUpdateListener listener) {
        mUpdateListener = new WeakReference<AsyncUpdateListener>(listener);
    }

    public void setDeleteListener(AsyncDeleteListener listener) {
        mDeleteListener = new WeakReference<AsyncDeleteListener>(listener);
    }

    /**
     * Clear any {@link AsyncQueryListener} set through
     * {@link #setQueryListener(AsyncQueryListener)}
     */
    public void clearQueryListener() {
        mQueryListener = null;
    }
    public void clearInsertListener() {
        mInsertListener = null;
    }
    public void clearUpdateListener() {
        mUpdateListener = null;
    }
    public void clearDeleteListener() {
        mDeleteListener = null;
    }

    /**
     * Begin an asynchronous query with the given arguments. When finished,
     * {@link AsyncQueryListener#onQueryComplete(int, Object, Cursor)} is
     * called if a valid {@link AsyncQueryListener} is present.
     */
    public void startQuery(Uri uri, String[] projection) {
        startQuery(-1, null, uri, projection, null, null, null);
    }

    /**
     * Begin an asynchronous query with the given arguments. When finished,
     * {@link AsyncQueryListener#onQueryComplete(int, Object, Cursor)} is called
     * if a valid {@link AsyncQueryListener} is present.
     */
    public void startQuery(Uri uri, String[] projection, String sortOrder) {
        startQuery(-1, null, uri, projection, null, null, sortOrder);
    }

    /**
     * Begin an asynchronous update with the given arguments.
     */
    public void startUpdate(Uri uri, ContentValues values) {
        startUpdate(-1, null, uri, values, null, null);
    }

    public void startInsert(Uri uri, ContentValues values) {
        startInsert(-1, null, uri, values);
    }

    public void startDelete(Uri uri) {
        startDelete(-1, null, uri, null, null);
    }

    /** {@inheritDoc} */
    @Override
    protected void onQueryComplete(int token, Object cookie, Cursor cursor) {
        final AsyncQueryListener listener = mQueryListener == null ? null : mQueryListener.get();
        if (listener != null) {
            listener.onQueryComplete(cursor);
        } else if (cursor != null) {
            cursor.close();
        }
    }

    @Override
    protected void onInsertComplete(int token, Object cookie, Uri uri) {
        final AsyncInsertListener listener = mInsertListener == null ? null : mInsertListener.get();
        if (listener != null) {
            listener.onInsertComplete(uri);
        }
    }

    @Override
    protected void onUpdateComplete(int token, Object cookie, int result) {
        final AsyncUpdateListener listener = mUpdateListener == null ? null : mUpdateListener.get();
        if (listener != null) {
            listener.onUpdateComplete(result);
        }
    }

    @Override
    protected void onDeleteComplete(int token, Object cookie, int result) {
        final AsyncDeleteListener listener = mDeleteListener == null ? null : mDeleteListener.get();
        if (listener != null) {
            listener.onDeleteComplete(result);
        }
    }

}