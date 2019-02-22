package com.itheima.mobilesafe.activity;

import com.itheima.mobilesafe.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

public abstract class BaseSetupActivity extends Activity {
	private GestureDetector gestureDetector;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		gestureDetector = new GestureDetector(this, new GestureDetector.SimpleOnGestureListener() {

			@Override
			public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
				// 向左滑动
				if (e1.getX() > e2.getX()) {
					showNextPage();
				}
				// 向右滑动
				if (e1.getX() < e2.getX()) {
					showPrePage();
				}
				return super.onFling(e1, e2, velocityX, velocityY);
			}
			
		});
	}
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		// 通过手势处理类处理多种事件
		gestureDetector.onTouchEvent(event);
		return super.onTouchEvent(event);
	}
	/**
	 *显示下页的抽象方法 
	 */
	protected abstract void showNextPage();
	/**
	 * 显示上页的抽象方法
	 */
	protected abstract void showPrePage();
	
	
	public void nextPage(View v) {
		showNextPage();
	}
	public void prePage(View v) {
		showPrePage();
	}

}
