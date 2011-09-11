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
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.Menu;
import android.support.v4.view.MenuItem;
import android.support.v4.view.ViewPager;
import android.view.MenuInflater;
import android.widget.ArrayAdapter;

import com.jakewharton.android.viewpagerindicator.TitlePageIndicator;
import com.jakewharton.android.viewpagerindicator.TitleProvider;

public class HomeActivity extends BaseActivity {

    private static final boolean DEBUG = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (DEBUG) {
            StrictMode.enableDefaults();
        }

        setContentView(R.layout.activity_home);
        ViewPager pager = (ViewPager)findViewById(R.id.pager);
        pager.setAdapter(new TitleFragmentAdapter(getSupportFragmentManager()));

        TitlePageIndicator indicator = (TitlePageIndicator)findViewById(R.id.indicator);
        indicator.setViewPager(pager, TitleFragmentAdapter.DEFAULT);

        final Uri uri = getIntent().getData();
        if (uri != null) {
            if (uri.equals(LogbookContract.Jumps.CONTENT_URI)) {
                indicator.setCurrentItem(TitleFragmentAdapter.JUMP);
            } else if (uri.equals(LogbookContract.Places.CONTENT_URI)) {
                indicator.setCurrentItem(TitleFragmentAdapter.PLACES);
            } else if (uri.equals(LogbookContract.Aircrafts.CONTENT_URI)) {
                indicator.setCurrentItem(TitleFragmentAdapter.AIRCRAFTS);
            } else if (uri.equals(LogbookContract.Equipment.CONTENT_URI)) {
                indicator.setCurrentItem(TitleFragmentAdapter.EQUIPMENT);
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

    public class TitleFragmentAdapter extends FragmentPagerAdapter implements TitleProvider {

        private static final int STATS     = 0;
        private static final int JUMP      = 1;
        private static final int PLACES    = 2;
        private static final int AIRCRAFTS = 3;
        private static final int EQUIPMENT = 4;
        public static final int DEFAULT = JUMP;

        private static final int COUNT = 5;

        private ArrayAdapter<CharSequence> mTitles;

        public TitleFragmentAdapter(FragmentManager fm) {
            super(fm);
            mTitles = ArrayAdapter.createFromResource(getApplicationContext(), R.array.home_titles, R.layout.abs__simple_spinner_item);
            if (mTitles.getCount() != COUNT) {
                throw new IllegalStateException("Title and fragment count out of sync");
            }
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
            case STATS:
                return new StatisticsFragment();
            case JUMP:
                return new JumpListFragment();
            case PLACES:
                return new PlaceListFragment();
            case AIRCRAFTS:
                return new AircraftListFragment();
            case EQUIPMENT:
                return new EquipmentListFragment();
            default:
                return null;
            }
        }

        @Override
        public int getCount() {
            return COUNT;
        }

        @Override
        public String getTitle(int position) {
            return mTitles.getItem(position).toString();
        }

    }
}