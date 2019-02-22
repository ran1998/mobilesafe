package com.itheima.mobilesafe.receiver;

import com.itheima.mobilesafe.utils.ConstantValues;
import com.itheima.mobilesafe.utils.SpUtils;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.telephony.SmsManager;
import android.telephony.TelephonyManager;
import android.telephony.gsm.SmsMessage;

public class BootReceiver extends BroadcastReceiver{

	@Override
	public void onReceive(Context context, Intent intent) {
		// 获得存储的手机号
		String simnumber = SpUtils.getString(context, ConstantValues.SIM_NUMBER, "");
		// 获得手机序列号
		TelephonyManager telephoneManager = (TelephonyManager) context.getSystemService(context.TELEPHONY_SERVICE);
		String serialNumber = telephoneManager.getSimSerialNumber();
		if (!simnumber.equals(serialNumber)) {
			// 号码不一致，发送紧急短信
			SmsManager smsManager = SmsManager.getDefault();
			smsManager.sendTextMessage("5556", null, "sos", null, null);
		}
	}

}
