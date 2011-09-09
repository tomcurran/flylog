package org.tomcurran.logbook.ui;

import org.tomcurran.logbook.R;
import org.tomcurran.logbook.provider.LogbookContract;
import org.tomcurran.logbook.ui.fragments.AircraftListFragment;
import org.tomcurran.logbook.ui.fragments.EquipmentListFragment;
import org.tomcurran.logbook.ui.fragments.JumpListFragment;
import org.tomcurran.logbook.ui.fragments.PlaceListFragment;
import org.tomcurran.logbook.ui.fragments.StatisticsFragment;
import org.tomcurran.logbook.util.TestData;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v4.app.ActionBar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.Menu;
import android.support.v4.view.MenuItem;
import android.view.MenuInflater;
import android.widget.ArrayAdapter;

public class HomeActivity extends BaseActivity implements ActionBar.OnNavigationListener {

    private static final boolean DEBUG = true;
    
    private static final int LIST_JUMP      = 0;
    private static final int LIST_STATS     = 1;
    private static final int LIST_PLACES    = 2;
    private static final int LIST_AIRCRAFTS = 3;
    private static final int LIST_EQUIPMENT = 4;
    private static final int LIST_DEFAULT = LIST_JUMP;

    private JumpListFragment      mJumpFragment;
    private StatisticsFragment    mStatFragment;
    private PlaceListFragment     mPlaceFragment;
    private AircraftListFragment  mAircraftFragment;
    private EquipmentListFragment mEquipmentFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (DEBUG) {
            StrictMode.enableDefaults();
        }
        ensureSupportActionBarAttached();

        ActionBar ab = getSupportActionBar();
        FragmentManager fm = getSupportFragmentManager();

        ArrayAdapter<CharSequence> list = ArrayAdapter.createFromResource(this, R.array.locations, R.layout.abs__simple_spinner_item);
        list.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        ab.setNavigationMode(ActionBar.NAVIGATION_MODE_LIST);
        ab.setListNavigationCallbacks(list, this);

        mJumpFragment      = (JumpListFragment)      setupFragment(fm, JumpListFragment.class,      JumpListFragment.TAG);
        mStatFragment      = (StatisticsFragment)    setupFragment(fm, StatisticsFragment.class,    StatisticsFragment.TAG);
        mPlaceFragment     = (PlaceListFragment)     setupFragment(fm, PlaceListFragment.class,     PlaceListFragment.TAG);
        mAircraftFragment  = (AircraftListFragment)  setupFragment(fm, AircraftListFragment.class,  AircraftListFragment.TAG);
        mEquipmentFragment = (EquipmentListFragment) setupFragment(fm, EquipmentListFragment.class, EquipmentListFragment.TAG);

        final Uri uri = getIntent().getData();
        if (uri == null) {
            ab.setSelectedNavigationItem(LIST_DEFAULT);
        } else {
            if (uri.equals(LogbookContract.Jumps.CONTENT_URI)) {
                ab.setSelectedNavigationItem(LIST_JUMP);
            } else if (uri.equals(LogbookContract.Places.CONTENT_URI)) {
                ab.setSelectedNavigationItem(LIST_PLACES);
            } else if (uri.equals(LogbookContract.Aircrafts.CONTENT_URI)) {
                ab.setSelectedNavigationItem(LIST_AIRCRAFTS);
            } else if (uri.equals(LogbookContract.Equipment.CONTENT_URI)) {
                ab.setSelectedNavigationItem(LIST_EQUIPMENT);
            } else {
                ab.setSelectedNavigationItem(LIST_DEFAULT);
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflator = getMenuInflater();
        inflator.inflate(R.menu.options_menu_home, menu);
        if (DEBUG) {
            inflator.inflate(R.menu.options_menu_home_debug, menu);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
        case R.id.options_menu_home_preferences: {
            startActivity(new Intent(this, PreferencesActivity.class));
            return true;
        }
        case R.id.options_menu_home_debug_insert: {
            new TestData(getContentResolver()).insert();
            return true;
        }
        case R.id.options_menu_home_debug_delete: {
            new TestData(getContentResolver()).delete();
            return true;
        }
        default:
            return super.onOptionsItemSelected(item);
        }
    }

    private Fragment setupFragment(FragmentManager fragmentManager, Class<? extends Fragment> fragmentClass, final String TAG) {
        Fragment fragment = fragmentClass.cast(fragmentManager.findFragmentByTag(TAG));
        if (fragment == null) {
            try {
                fragment = fragmentClass.newInstance();
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return fragment;
    }

    @Override
    public boolean onNavigationItemSelected(int itemPosition, long itemId) {
        switch (itemPosition) {
        case LIST_JUMP:
            navigate(mJumpFragment, JumpListFragment.TAG);
            return true;
        case LIST_STATS:
            navigate(mStatFragment, StatisticsFragment.TAG);
            return true;
        case LIST_PLACES:
            navigate(mPlaceFragment, PlaceListFragment.TAG);
            return true;
        case LIST_AIRCRAFTS:
            navigate(mAircraftFragment, AircraftListFragment.TAG);
            return true;
        case LIST_EQUIPMENT:
            navigate(mEquipmentFragment, EquipmentListFragment.TAG);
            return true;
        default:
            return false;
        }
    }

    private void navigate(Fragment fragment, final String TAG) {
        if (!fragment.isAdded()) {
            getSupportFragmentManager()
                .beginTransaction()
                .replace(android.R.id.content, fragment, TAG)
                .commit();
        }
    }

}