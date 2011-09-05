package org.tomcurran.logbook.ui;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.KeyEvent;

public abstract class BaseActivity extends FragmentActivity {

    public boolean onKeyLongPress(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            goHome();
            return true;
        }
        return super.onKeyLongPress(keyCode, event);
    }

    public void goHome() {
        if (goActivity(HomeActivity.class)) {
//            overridePendingTransition(R.anim.home_enter, R.anim.home_exit);
        }
    }

    public void goUp(Class<? extends FragmentActivity> targetActivity) {
        goActivity(targetActivity);
    }

    private boolean goActivity(Class<? extends FragmentActivity> targetActivity) {
        if (this instanceof HomeActivity || this.getClass() == targetActivity) {
            return false;
        }

        final Intent intent = new Intent(this, targetActivity);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        return true;
    }

    /**
     * Converts an intent into a {@link Bundle} suitable for use as fragment
     * arguments.
     */
    public static Bundle intentToFragmentArguments(Intent intent) {
        Bundle arguments = new Bundle();
        if (intent == null) {
            return arguments;
        }

        final Uri data = intent.getData();
        if (data != null) {
            arguments.putParcelable("_uri", data);
        }

        final String action = intent.getAction();
        if (action != null) {
            arguments.putString("_action", action);
        }

        final Bundle extras = intent.getExtras();
        if (extras != null) {
            arguments.putAll(intent.getExtras());
        }

        return arguments;
    }

    /**
     * Converts a fragment arguments bundle into an intent.
     */
    public static Intent fragmentArgumentsToIntent(Bundle arguments) {
        Intent intent = new Intent();
        if (arguments == null) {
            return intent;
        }

        final Uri data = arguments.getParcelable("_uri");
        if (data != null) {
            intent.setData(data);
        }

        final String action = arguments.getString("_action");
        if (action != null) {
            intent.setAction(action);
        }

        intent.putExtras(arguments);
        intent.removeExtra("_uri");
        intent.removeExtra("_action");
        return intent;
    }
}