package com.itheima.mobilesafe.activity;

import com.itheima.mobilesafe.R;
import com.itheima.mobilesafe.utils.ConstantValues;
import com.itheima.mobilesafe.utils.SpUtils;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

public class StepOverActivity extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		boolean stepOver = SpUtils.getBoolean(getApplicationContext(), ConstantValues.STEP_OVER, false);
		if (stepOver) {
			setContentView(R.layout.activity_step_over);			
		} else {
			Intent intent = new Intent(this, Step1Activity.class);
			startActivity(intent);
		}
	}
}
