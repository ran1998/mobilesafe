package com.itheima.mobilesafe.view;

import com.itheima.mobilesafe.R;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.CheckBox;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class SettingItemView extends RelativeLayout {
	
	private final static String namespace = "http://schemas.android.com/apk/res/com.itheima.mobilesafe";
	private String mTitle;
	private String mDeson;
	private String mDesoff;
	private CheckBox cb_box;
	private TextView tv_desc;

	public SettingItemView(Context context) {
		this(context, null);
	}
	
	public SettingItemView(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}
	
	public SettingItemView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		// xml -> view 加载xml
		View view = View.inflate(getContext(), R.layout.setting_item_view, this);
		
		TextView tv_title = (TextView) view.findViewById(R.id.tv_title);
		tv_desc = (TextView) view.findViewById(R.id.tv_des);
		cb_box = (CheckBox) view.findViewById(R.id.cb_box);
		// 初始化属性
		initAttr(attrs);
		tv_title.setText(mTitle);
	}

	private void initAttr(AttributeSet attrs) {
		
// 		attrs.getAttributeCount(); 获取总的attr的count
//		for (int i=0; i< attrs.getAttributeCount(); i++) {
//			attrs.getAttributeName(i);
//			attrs.getAttributeValue(i);
//		}
		// 根据命名空间拿值
		mTitle = attrs.getAttributeValue(namespace, "destitle");
		mDeson = attrs.getAttributeValue(namespace, "deson");
		mDesoff = attrs.getAttributeValue(namespace, "desoff");
	}
	
	/**
	 * 是否选中
	 * @return
	 */
	public boolean isCheck() {
		return cb_box.isChecked();
	}
	
	/**
	 * 设置checkbox
	 * @param isChecked 
	 */
	public void setCheck(boolean isChecked) {		
		cb_box.setChecked(isChecked);
		if (isChecked) {
			tv_desc.setText(mDeson);
		} else {
			tv_desc.setText(mDesoff);
		}
	}

}
