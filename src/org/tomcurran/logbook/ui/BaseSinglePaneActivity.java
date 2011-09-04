package org.tomcurran.logbook.ui;

import org.tomcurran.logbook.R;

import android.os.Bundle;
import android.support.v4.app.Fragment;

/**
 * A {@link BaseActivity} that simply contains a single fragment. The intent used to invoke this
 * activity is forwarded to the fragment as arguments during fragment instantiation. Derived
 * activities should only need to implement
 * {@link com.google.android.apps.iosched.ui.BaseSinglePaneActivity#onCreatePane()}.
 */
public abstract class BaseSinglePaneActivity extends BaseActivity {

    private Fragment mFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_singlepane_empty);

        if (savedInstanceState == null) {
            mFragment = onCreatePane();
            mFragment.setArguments(intentToFragmentArguments(getIntent()));

            getSupportFragmentManager().beginTransaction()
                    .add(R.id.root_container, mFragment)
                    .commit();
        }
    }

    /**
     * Called in <code>onCreate</code> when the fragment constituting this activity is needed.
     * The returned fragment's arguments will be set to the intent used to invoke this activity.
     */
    protected abstract Fragment onCreatePane();

}