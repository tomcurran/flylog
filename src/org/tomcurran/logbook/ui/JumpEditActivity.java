package org.tomcurran.logbook.ui;

import org.tomcurran.logbook.ui.fragments.JumpEditFragment;

import android.support.v4.app.Fragment;

public class JumpEditActivity extends BaseSinglePaneActivity {

	@Override
	protected Fragment onCreatePane() {
		return new JumpEditFragment();
	}

}
