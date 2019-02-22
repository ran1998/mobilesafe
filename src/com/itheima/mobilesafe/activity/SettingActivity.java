package com.itheima.mobilesafe.activity;

import com.itheima.mobilesafe.R;
import com.itheima.mobilesafe.utils.ConstantValues;
import com.itheima.mobilesafe.utils.SpUtils;
import com.itheima.mobilesafe.view.SettingItemView;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

public class SettingActivity extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_setting);
		
		initUpdate();
	}

	private void initUpdate() {
		final SettingItemView siv_update = (SettingItemView) findViewById(R.id.siv_update);
		// 初始化checkbox状态
		boolean isChecked = SpUtils.getBoolean(getApplicationContext(), ConstantValues.SIV_UPDATE, false);
		siv_update.setCheck(isChecked);
		
		siv_update.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				boolean check = siv_update.isCheck();
				siv_update.setCheck(!check);
				SpUtils.putBoolean(getApplicationContext(), ConstantValues.SIV_UPDATE, !check);
			}
		});
	}
}
