package com.itheima.mobilesafe.activity;

import com.itheima.mobilesafe.R;
import com.itheima.mobilesafe.utils.ConstantValues;
import com.itheima.mobilesafe.utils.SpUtils;
import com.itheima.mobilesafe.view.SettingItemView;

import android.content.Intent;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Toast;

public class Step2Activity extends BaseSetupActivity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_step2);
		
		final SettingItemView cb_box = (SettingItemView) findViewById(R.id.sim_bind);
		String simnumber = SpUtils.getString(getApplicationContext(), ConstantValues.SIM_NUMBER, "");
		if (TextUtils.isEmpty(simnumber)) {
			cb_box.setCheck(false);
		} else {
			cb_box.setCheck(true);
		}
		cb_box.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				boolean check = cb_box.isCheck();
				cb_box.setCheck(!check);
				if (!check) {
					// 存储sim卡序列号
					TelephonyManager telephoneManager = (TelephonyManager) getSystemService(TELEPHONY_SERVICE);
					// 获取sim卡序列号
					String serialNumber = telephoneManager.getSimSerialNumber();
					// 存储
					SpUtils.putString(getApplicationContext(), ConstantValues.SIM_NUMBER, serialNumber);
				} else {
					SpUtils.remove(getApplicationContext(), ConstantValues.SIM_NUMBER);
				}
			}
		});
	}

	@Override
	protected void showNextPage() {
		String simnumber = SpUtils.getString(getApplicationContext(), ConstantValues.SIM_NUMBER, "");
		if (!TextUtils.isEmpty(simnumber)) {			
			Intent intent = new Intent(this, Step3Activity.class);
			startActivity(intent);
			
			finish();
			overridePendingTransition(R.anim.next_in_anim, R.anim.next_out_anim);
		} else {
			Toast.makeText(getApplicationContext(), "请先绑定sim卡", 0).show();
		}
	}

	@Override
	protected void showPrePage() {
		Intent intent = new Intent(this, Step1Activity.class);
		startActivity(intent);
		
		finish();
		overridePendingTransition(R.anim.pre_in_anim, R.anim.pre_out_anim);
	}
}
