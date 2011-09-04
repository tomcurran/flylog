package org.tomcurran.logbook.ui.widget;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.DataSetObserver;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;

public class AddSpinner extends Spinner implements DialogInterface.OnClickListener {

	private AlertDialog mPopup;
	protected AddSpinner.OnAddClickListener mListener = null;
	protected DialogInterface.OnClickListener mOnClickListener = new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				if (mListener != null) {
					mListener.onAddClick(AddSpinner.this);
				}
			}
	};
	
	public interface OnAddClickListener {
        void onAddClick(Spinner spinner);
    }
	
	public AddSpinner(Context context) {
		this(context, null);
	}

	public AddSpinner(Context context, AttributeSet attrs) {
		this(context, attrs, android.R.attr.spinnerStyle);
	}

	public AddSpinner(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	@Override
	protected void onDetachedFromWindow() {
		super.onDetachedFromWindow();

		if (mPopup != null && mPopup.isShowing()) {
			mPopup.dismiss();
			mPopup = null;
		}
	}

	@Override
	public boolean performClick() {
//		boolean handled = super.performClick();
		boolean handled = false;

		if (!handled) {
            handled = true;
            Context context = getContext();
            
            final DropDownAdapter adapter = new DropDownAdapter(getAdapter());

            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setPositiveButton("Add", mOnClickListener);
            
			CharSequence prompt = getPrompt();
            if (prompt != null) {
                builder.setTitle(prompt);
            }
            mPopup = builder.setSingleChoiceItems(adapter, getSelectedItemPosition(), this).show();
        }

        return handled;
	}

	@Override
	public void onClick(DialogInterface dialog, int which) {
		setSelection(which);
		dialog.dismiss();
		mPopup = null;
	}
	
	public void setOnAddListener(AddSpinner.OnAddClickListener listener) {
		mListener = listener;
	}

	public void setSelectionDbRow(final Long rowId, final String column) {
		if (rowId == null) {
			return;
		}

		final int parentCount = this.getCount();
        for (int i = 0; i < parentCount; i++) {
            final Cursor cursor = (Cursor) getItemAtPosition(i);
            if (rowId == cursor.getLong(cursor.getColumnIndex(column))) {
                setSelection(i);
                break;
            }
        }
	}

    /**
     * <p>Wrapper class for an Adapter. Transforms the embedded Adapter instance
     * into a ListAdapter.</p>
     */
    private static class DropDownAdapter implements ListAdapter, SpinnerAdapter {
        private SpinnerAdapter mAdapter;
        private ListAdapter mListAdapter;

        /**
         * <p>Creates a new ListAdapter wrapper for the specified adapter.</p>
         *
         * @param adapter the Adapter to transform into a ListAdapter
         */
        public DropDownAdapter(SpinnerAdapter adapter) {
            this.mAdapter = adapter;
            if (adapter instanceof ListAdapter) {
                this.mListAdapter = (ListAdapter) adapter;
            }
        }

        public int getCount() {
            return mAdapter == null ? 0 : mAdapter.getCount();
        }

        public Object getItem(int position) {
            return mAdapter == null ? null : mAdapter.getItem(position);
        }

        public long getItemId(int position) {
            return mAdapter == null ? -1 : mAdapter.getItemId(position);
        }

        public View getView(int position, View convertView, ViewGroup parent) {
            return getDropDownView(position, convertView, parent);
        }

        public View getDropDownView(int position, View convertView, ViewGroup parent) {
            return mAdapter == null ? null :
                    mAdapter.getDropDownView(position, convertView, parent);
        }

        public boolean hasStableIds() {
            return mAdapter != null && mAdapter.hasStableIds();
        }

        public void registerDataSetObserver(DataSetObserver observer) {
            if (mAdapter != null) {
                mAdapter.registerDataSetObserver(observer);
            }
        }

        public void unregisterDataSetObserver(DataSetObserver observer) {
            if (mAdapter != null) {
                mAdapter.unregisterDataSetObserver(observer);
            }
        }

        /**
         * If the wrapped SpinnerAdapter is also a ListAdapter, delegate this call.
         * Otherwise, return true. 
         */
        public boolean areAllItemsEnabled() {
            final ListAdapter adapter = mListAdapter;
            if (adapter != null) {
                return adapter.areAllItemsEnabled();
            } else {
                return true;
            }
        }

        /**
         * If the wrapped SpinnerAdapter is also a ListAdapter, delegate this call.
         * Otherwise, return true.
         */
        public boolean isEnabled(int position) {
            final ListAdapter adapter = mListAdapter;
            if (adapter != null) {
                return adapter.isEnabled(position);
            } else {
                return true;
            }
        }

        public int getItemViewType(int position) {
            return 0;
        }

        public int getViewTypeCount() {
            return 1;
        }
        
        public boolean isEmpty() {
            return getCount() == 0;
        }
    }
}
