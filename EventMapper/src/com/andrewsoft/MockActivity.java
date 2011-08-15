package com.andrewsoft;

import android.app.Activity;
import android.os.Bundle;

public class MockActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.mock);
	}

}
