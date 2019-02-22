package com.itheima.mobilesafe.activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.itheima.mobilesafe.R;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class ContactListActivity extends Activity {
	private ListView lv_contact;
	private MyAdapter mAdapter;
	private List<HashMap<String, String>> contactList = new ArrayList<HashMap<String, String>>();
	private Handler handler = new Handler() {


		@Override
		public void handleMessage(Message msg) {
			mAdapter = new MyAdapter();
			lv_contact.setAdapter(mAdapter);
			super.handleMessage(msg);
		}
		
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_contact_list);
		
		initUI();
		initData();
	}

	class MyAdapter extends BaseAdapter {

		@Override
		public int getCount() {
			return contactList.size();
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
			View v;
			if (convertView != null) {
				v = convertView;
			} else {
				v = View.inflate(getApplicationContext(), R.layout.listview_contact_item, null);
			}
			TextView tv_name = (TextView) v.findViewById(R.id.tv_name);
			TextView tv_phone = (TextView) v.findViewById(R.id.tv_phone);
			
			tv_name.setText(contactList.get(position).get("name"));
			tv_phone.setText(contactList.get(position).get("phone"));
			
			return v;
		}
		
	}
	private void initData() {
		new Thread() {

			@Override
			public void run() {
				// 获得内容解析者
				ContentResolver resolver = getContentResolver();
				Cursor cursor = resolver.query(Uri.parse("content://com.android.contacts/raw_contacts"),
						new String[] {"contact_id"}, null, null, null);
				while (cursor.moveToNext()) {
					String contact_id = cursor.getString(0);
					// 根据用户唯一性id查询data, mimetype
					Cursor cursor2 = resolver.query(Uri.parse("content://com.android.contacts/data"), 
							new String[] {"data1","mimetype"}, "raw_contact_id=?", new String[] {contact_id}, null);
					HashMap<String,String> contactItem = new HashMap<String, String>();
					while (cursor2.moveToNext()) {
						String data1 = cursor2.getString(0);
						String mimetype = cursor2.getString(1);
						
						if ("vnd.android.cursor.item/phone_v2".equals(mimetype)) {
							contactItem.put("phone", data1);
						}
						if ("vnd.android.cursor.item/name".equals(mimetype)) {
							contactItem.put("name", data1);
						}
					}
					cursor2.close();
					contactList.add(contactItem);
				}
				cursor.close();
				// 发送一个消息更新listview
				handler.sendEmptyMessage(0);
			}
			
		}.start();
	}

	private void initUI() {
		lv_contact = (ListView) findViewById(R.id.lv_contact);
		lv_contact.setOnItemClickListener(new OnItemClickListener() {
			
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				if (mAdapter != null) {					
					HashMap<String, String> item = contactList.get(position);
					String phone = item.get("phone");
					
					Intent intent = new Intent();
					intent.putExtra("phone", phone);
					setResult(1, intent);
					
					finish();
				}
			}
		});
	}

}
