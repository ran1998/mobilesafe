package com.itheima.mobilesafe.activity;

import com.itheima.mobilesafe.R;
import com.itheima.mobilesafe.utils.ConstantValues;
import com.itheima.mobilesafe.utils.SpUtils;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.Toast;

public class Step4Activity extends BaseSetupActivity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_step4);
		
		initUI();
	}
	
	private void initUI() {
		final CheckBox cb_box = (CheckBox) findViewById(R.id.cb_box);
		boolean open_security = SpUtils.getBoolean(getApplicationContext(), ConstantValues.OPEN_SECURITY, false);
		if (open_security) {
			cb_box.setText("安全设置已开启");
		} else {
			cb_box.setText("安全设置已关闭");
		}
		cb_box.setChecked(open_security);
		
		cb_box.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				SpUtils.putBoolean(getApplicationContext(), ConstantValues.OPEN_SECURITY, isChecked);
				if (isChecked) {
					cb_box.setText("安全设置已开启");
				} else {
					cb_box.setText("安全设置已关闭");
				}
			}
		});
	}

	@Override
	protected void showNextPage() {
		boolean open_security = SpUtils.getBoolean(getApplicationContext(), ConstantValues.OPEN_SECURITY, false);
		if (open_security) {			
			Intent intent = new Intent(this, StepOverActivity.class);
			startActivity(intent);
			
			finish();
			SpUtils.putBoolean(getApplicationContext(), ConstantValues.STEP_OVER, true);
			overridePendingTransition(R.anim.next_in_anim, R.anim.next_out_anim);
		} else {
			Toast.makeText(getApplicationContext(), "请开启防盗保护设置", 0).show();
		}
	}

	@Override
	protected void showPrePage() {
		Intent intent = new Intent(this, Step3Activity.class);
		startActivity(intent);
		
		finish();
		overridePendingTransition(R.anim.pre_in_anim, R.anim.pre_out_anim);
	}
}
