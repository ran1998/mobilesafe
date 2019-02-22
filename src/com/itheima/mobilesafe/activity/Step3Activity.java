package com.itheima.mobilesafe.activity;

import com.itheima.mobilesafe.R;
import com.itheima.mobilesafe.utils.ConstantValues;
import com.itheima.mobilesafe.utils.SpUtils;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.provider.SyncStateContract.Constants;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Step3Activity extends BaseSetupActivity {
	private EditText et_phone_number;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_step3);
		
		initUI();
	}
	
	private void initUI() {
		et_phone_number = (EditText) findViewById(R.id.et_phone_number);
		Button bt_select_number = (Button) findViewById(R.id.bt_select_number);
		// 回显数据
		String contactPhone = SpUtils.getString(getApplicationContext(), ConstantValues.CONTACT_PHONE, "");
		et_phone_number.setText(contactPhone);
		
		bt_select_number.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(getApplicationContext(), ContactListActivity.class);
				startActivityForResult(intent, 0);
			}
		});
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (resultCode == 1) {
			String phone = data.getStringExtra("phone");
			phone.replace("-", "").trim();
			et_phone_number.setText(phone);
			// 存储到sp中
			SpUtils.putString(getApplicationContext(), ConstantValues.CONTACT_PHONE, phone);
		}
	}

	@Override
	protected void showNextPage() {
		String contactPhone = SpUtils.getString(getApplicationContext(), ConstantValues.CONTACT_PHONE, "");
		String phone = et_phone_number.getText().toString().trim();
		if (!TextUtils.isEmpty(contactPhone)) {			
			Intent intent = new Intent(this, Step4Activity.class);
			startActivity(intent);
			
			finish();
			overridePendingTransition(R.anim.next_in_anim, R.anim.next_out_anim);
		} else {
			Toast.makeText(getApplicationContext(), "手机号码不能为空", 0).show();
		}
		SpUtils.putString(getApplicationContext(), ConstantValues.CONTACT_PHONE, phone);
	}

	@Override
	protected void showPrePage() {
		Intent intent = new Intent(this, Step2Activity.class);
		startActivity(intent);
		
		finish();
		overridePendingTransition(R.anim.pre_in_anim, R.anim.pre_out_anim);
	}
}
