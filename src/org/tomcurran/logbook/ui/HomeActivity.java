package org.tomcurran.logbook.ui;

import org.tomcurran.logbook.R;
import org.tomcurran.logbook.ui.fragments.AircraftListFragment;
import org.tomcurran.logbook.ui.fragments.EquipmentListFragment;
import org.tomcurran.logbook.ui.fragments.JumpListFragment;
import org.tomcurran.logbook.ui.fragments.PlaceListFragment;
import org.tomcurran.logbook.ui.fragments.StatisticsFragment;

import android.os.Bundle;
import android.support.v4.app.ActionBar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.widget.ArrayAdapter;

public class HomeActivity extends BaseActivity implements ActionBar.OnNavigationListener {

    private static final int LIST_JUMP      = 0;
    private static final int LIST_STATS     = 1;
    private static final int LIST_PLACES    = 2;
    private static final int LIST_AIRCRAFTS = 3;
    private static final int LIST_EQUIPMENT = 4;

    private JumpListFragment      mJumpFragment;
    private StatisticsFragment    mStatFragment;
    private PlaceListFragment     mPlaceFragment;
    private AircraftListFragment  mAircraftFragment;
    private EquipmentListFragment mEquipmentFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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