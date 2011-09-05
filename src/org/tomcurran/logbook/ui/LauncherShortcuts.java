package org.tomcurran.logbook.ui;

import org.tomcurran.logbook.R;
import org.tomcurran.logbook.provider.LogbookContract;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;


public class LauncherShortcuts extends FragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        final String action = getIntent().getAction();

        if (Intent.ACTION_CREATE_SHORTCUT.equals(action)) {
            final Uri jumpUri = LogbookContract.Jumps.CONTENT_URI;
            final Intent shortcutIntent = new Intent(Intent.ACTION_INSERT, jumpUri);

            Intent intent = new Intent();
            intent.putExtra(Intent.EXTRA_SHORTCUT_INTENT, shortcutIntent);
            intent.putExtra(Intent.EXTRA_SHORTCUT_NAME, getString(R.string.shortcut_jump_insert_name));
            intent.putExtra(Intent.EXTRA_SHORTCUT_ICON_RESOURCE, Intent.ShortcutIconResource.fromContext(this, R.drawable.shortcut_jump_insert));
            setResult(FragmentActivity.RESULT_OK, intent);

            finish();
            return;
        }

        /* activity isn't used for anything else so close if its attempted to be opened
         * it could be used to display ui for choosing multiple shortcuts */
        finish();
        return;
    }

}