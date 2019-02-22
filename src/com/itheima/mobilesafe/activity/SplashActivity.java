package com.itheima.mobilesafe.activity;

import java.io.File;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import org.json.JSONObject;

import com.itheima.mobilesafe.R;
import com.itheima.mobilesafe.utils.StreamUtils;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

/**
 * @author Administrator
 *
 */
public class SplashActivity extends Activity {
	private String tag = "SplashActivity";
	private TextView mVersionName;
	private int mVersionCode;
	private String versionName;
	private String mVersionDesc;
	private String vDownloadUrl;
	private final int UPDATE_VERSION = 100;
	private final int ENTER_HOME = 101;
	private final int INTNET_ERROR = 102;
	private Handler handler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			System.out.println(msg.what);
			switch (msg.what) {
			case UPDATE_VERSION:
				// 升级
				showDialog();
				break;
			case ENTER_HOME:
				// 跳转到主页面
				enterHome();
				break;
			case INTNET_ERROR:
				// 跳转到主页面
				Toast.makeText(getApplicationContext(), "网络连接超时", 0).show();
				enterHome();
				
				break;

			default:
				break;
			}
		}
	};


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_splash);
		
		initUI();
		initData();
	}

	
	/**
	 * 初始化数据
	 */
	private void initData() {
		mVersionName.setText("版本名称"+getVersionName());
		mVersionCode = getVersionCode();
		
		// 检查版本号
		checkVersion();
	}


	/**
	 * 检查版本
	 */
	private void checkVersion() {
		new Thread() {
			private Message msg;

			@Override
			public void run() {
				long startTime = System.currentTimeMillis();
				try {
					URL url = new URL("http://192.168.0.107:8080/update.json");
					HttpURLConnection conn = (HttpURLConnection) url.openConnection();
					conn.setConnectTimeout(3000);
					int code = conn.getResponseCode();
					if (code == 200) {
						InputStream in = conn.getInputStream();
						String json = StreamUtils.stream2String(in);
						
						JSONObject jsonObject = new JSONObject(json);
						versionName = (String) jsonObject.get("versionName");
						String versionCode = (String) jsonObject.get("versionCode");
						mVersionDesc = (String) jsonObject.get("versionDesc");
						vDownloadUrl = (String) jsonObject.get("downloadUrl");
						
						Log.i(tag, versionName);
						Log.i(tag, mVersionDesc);
						Log.i(tag, vDownloadUrl);
						
						msg = Message.obtain();
						if (mVersionCode < Integer.parseInt(versionCode)) {
							// 升级提示
							msg.what = UPDATE_VERSION;
						} else {
							msg.what = ENTER_HOME;
						}
					} else {
						msg = Message.obtain();
						msg.what = INTNET_ERROR;
					}
				} catch (Exception e) {
					e.printStackTrace();
					msg = Message.obtain();
					msg.what = INTNET_ERROR;
				} finally {
					long endTime = System.currentTimeMillis();
					long destTime;
//					if ((destTime = endTime-startTime) < 4000) {
//						try {
//							sleep((4000 - destTime));
//						} catch (InterruptedException e) {
//							e.printStackTrace();
//						}
//					}
					System.out.println(msg);
					handler.sendMessage(msg);
				}
			}
			
		}.start();
		
	}
	/**
	 *版本更新弹窗 
	 */
	private void showDialog() {
		Builder builder = new AlertDialog.Builder(this);
		builder.setTitle("版本更新提示");
		builder.setMessage(mVersionDesc);
		builder.setPositiveButton("立即更新", new OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				downloadApk();
			}
		});
		builder.setNegativeButton("取消", new OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				enterHome();
			}
		});
		builder.setOnCancelListener(new OnCancelListener() {
			
			@Override
			public void onCancel(DialogInterface dialog) {
				enterHome();
				dialog.dismiss();
			}
		});
		builder.show();
	}
	
	/**
	 *	下载apk 
	 */
	private void downloadApk() {
		if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
			String path = Environment.getExternalStorageDirectory().getAbsolutePath()+File.separator+"mobilesafe.apk";			
			HttpUtils httpUtils = new HttpUtils();
			System.out.println(vDownloadUrl);
			httpUtils.download(vDownloadUrl, path, new RequestCallBack<File>() {
				
				@Override
				public void onSuccess(ResponseInfo<File> resultFile) {
					// 下载成功
					File result = resultFile.result;
					installApk(result);
				}

				@Override
				public void onFailure(HttpException arg0, String arg1) {
					// 下载失败
					Toast.makeText(getApplicationContext(), "更新失败", 0).show();
					enterHome();
					System.out.println(arg1);
				}
				@Override
				public void onLoading(long total, long current, boolean isUploading) {
					// 下载中
					ProgressBar progressBar = new ProgressBar(getApplicationContext());
					progressBar.setMax((int) total);
					progressBar.setProgress((int) current);
					super.onLoading(total, current, isUploading);
				}
			});
		} else {
			Toast.makeText(this, "sd卡无效", 0).show();
			enterHome();
		}
	}
	
	/**
	 * 安装apk
	 * @param result
	 */
	private void installApk(File result) {
		Intent intent = new Intent("android.intent.action.VIEW");
		intent.addCategory("android.intent.category.DEFAULT");
		intent.setDataAndType(Uri.fromFile(result),"application/vnd.android.package-archive");
		startActivityForResult(intent, 0);
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		enterHome();
	}
	
	/**
	 *进入主界面 
	 */
	private void enterHome() {
		Intent intent = new Intent(this, HomeActivity.class);
		startActivity(intent);
		
		finish();
	}


	/**
	 * 获取版本号
	 * @return
	 */
	private int getVersionCode() {
		PackageManager pm = getPackageManager();
		try {
			PackageInfo packageInfo = pm.getPackageInfo(getPackageName(), 0);
			return packageInfo.versionCode;
		} catch (NameNotFoundException e) {
			e.printStackTrace();
		}
		return -1;
	}


	/**
	 * 获取版本名称
	 * @return
	 */
	private String getVersionName() {
		PackageManager pm = getPackageManager();
		try {
			PackageInfo packageInfo = pm.getPackageInfo(getPackageName(), 0);
			return packageInfo.versionName;
		} catch (NameNotFoundException e) {
			e.printStackTrace();
		}
		return null;
	}


	/**
	 * 初始化ui
	 */
	private void initUI() {
		mVersionName = (TextView) findViewById(R.id.tv_version);
		
	}
}
