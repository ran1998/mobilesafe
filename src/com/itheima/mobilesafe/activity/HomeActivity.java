package com.itheima.mobilesafe.activity;

import com.itheima.mobilesafe.R;
import com.itheima.mobilesafe.utils.ConstantValues;
import com.itheima.mobilesafe.utils.SpUtils;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class HomeActivity extends Activity {

	private GridView mGridView;
	private String[] strItems;
	private int[] imgItems;
	private String Tag = "HomeActivity";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_home);
		
		initUI();
		initData();
	}

	private void initData() {
		strItems = new String[] {
				"手机防盗","通讯卫士","软件管理","进程管理","流量统计","手机杀毒","缓存清理","高级工具","设置中心"
		};
		imgItems = new int[] {
			R.drawable.home_safe,	
			R.drawable.home_callmsgsafe,	
			R.drawable.home_apps,	
			R.drawable.home_taskmanager,	
			R.drawable.home_netmanager,	
			R.drawable.home_trojan,	
			R.drawable.home_sysoptimize,	
			R.drawable.home_tools,	
			R.drawable.home_settings,	
		};
		mGridView.setAdapter(new MyAdapter());
		mGridView.setOnItemClickListener(new OnItemClickListener() {
			
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				switch (position) {
				case 0:
					showDialog();
					break;
				case 8:
					Intent intent = new Intent(getApplicationContext(), SettingActivity.class);
					startActivity(intent);
					break;

				default:
					break;
				}
			}
		});
	}

	private void initUI() {
		mGridView = (GridView) findViewById(R.id.gv_home);
	}
	
	/**
	 * 手机防盗
	 */
	private void showDialog() {
		String isSetPwd = SpUtils.getString(getApplicationContext(), ConstantValues.MOBILE_SAFE_PWD, "");
		System.out.println(isSetPwd);
		if (TextUtils.isEmpty(isSetPwd)) {
			Log.i(Tag, "123");
			showSetPwdDialog();
		} else {
			Log.i(Tag, "456");
			showConfirmDialog();
		}
	}
	
	
	/**
	 *显示确定密码的弹窗 
	 */
	private void showConfirmDialog() {
		Builder builder = new AlertDialog.Builder(this);
		final AlertDialog dialog = builder.create();
		final View view = View.inflate(getApplicationContext(), R.layout.dialog_comfirm_pwd, null);
		dialog.setView(view);
		dialog.show();
		
		Button bt_comfirm = (Button) view.findViewById(R.id.bt_comfirm);
		Button bt_cancel = (Button) view.findViewById(R.id.bt_cancel);
		
		bt_comfirm.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				EditText et_comfirmPwd = (EditText) view.findViewById(R.id.et_comfirmPwd);
				
				String setConfirm = et_comfirmPwd.getText().toString();
				
				if (!TextUtils.isEmpty(setConfirm)) {
					String pwd = SpUtils.getString(getApplicationContext(), ConstantValues.MOBILE_SAFE_PWD, "");
					if (pwd.equals(setConfirm)) {
						Intent intent = new Intent(getApplicationContext(), StepOverActivity.class);
						startActivity(intent);
						// 跳转到其他activity需要隐藏dialog
						dialog.dismiss();
						// 保存密码
					} else {
						Toast.makeText(getApplicationContext(), "密码错误", 0).show();
					}
				} else {
					Toast.makeText(getApplicationContext(), "密码不能为空", 0).show();
				}
			}
		});
		
		bt_cancel.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				dialog.dismiss();
			}
		});
	}

	/**
	 * 显示设置密码弹窗
	 */
	private void showSetPwdDialog() {
		Builder builder = new AlertDialog.Builder(this);
		final AlertDialog dialog = builder.create();
		final View view = View.inflate(getApplicationContext(), R.layout.dialog_set_pwd, null);
		dialog.setView(view);
		dialog.show();
		
		Button bt_comfirm = (Button) view.findViewById(R.id.bt_comfirm);
		Button bt_cancel = (Button) view.findViewById(R.id.bt_cancel);
		
		bt_comfirm.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				EditText et_setPwd = (EditText) view.findViewById(R.id.et_setPwd);
				EditText et_comfirmPwd = (EditText) view.findViewById(R.id.et_comfirmPwd);
				
				String setPwd = et_setPwd.getText().toString();
				String setConfirm = et_comfirmPwd.getText().toString();
				
				if (!TextUtils.isEmpty(setConfirm) && !TextUtils.isEmpty(setPwd)) {
					if (setPwd.equals(setConfirm)) {
						Intent intent = new Intent(getApplicationContext(), StepOverActivity.class);
						startActivity(intent);
						// 跳转到其他activity需要隐藏dialog
						dialog.dismiss();
						// 保存密码
						SpUtils.putString(getApplicationContext(), ConstantValues.MOBILE_SAFE_PWD, setPwd);
					} else {
						Toast.makeText(getApplicationContext(), "两次密码不一致", 0).show();
					}
				} else {
					Toast.makeText(getApplicationContext(), "密码不能为空", 0).show();
				}
			}
		});
		
		bt_cancel.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				dialog.dismiss();
			}
		});
	}

	/**
	 * 九宫格数据适配器
	 * @author Administrator
	 *
	 */
	class MyAdapter extends BaseAdapter {

		@Override
		public int getCount() {
			return strItems.length;
		}

		@Override
		public Object getItem(int position) {
			return null;
		}

		@Override
		public long getItemId(int position) {
			return 0;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			View v = null;
			if (v != null) {
				v = convertView;
			} else {
				v = View.inflate(getApplicationContext(), R.layout.grid_item, null);
			}
			ImageView iv_img = (ImageView) v.findViewById(R.id.iv_img);
			TextView tv_text = (TextView) v.findViewById(R.id.tv_text);
			iv_img.setImageResource(imgItems[position]);
			tv_text.setText(strItems[position]);
			return v;
		}
		
	}
}
