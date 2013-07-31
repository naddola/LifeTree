package com.example.lifetree;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.ScaleGestureDetector;

public class MainActivity extends Activity {


	
	ScaleGestureDetector mScaleDetector;
	float mScaleFactor = 1.f;

	TreeView tv;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		tv = new TreeView(this);
		setContentView(tv);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}



}
