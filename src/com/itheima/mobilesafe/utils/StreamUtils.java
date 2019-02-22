package com.itheima.mobilesafe.utils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class StreamUtils {

	/**
	 * 流转字符串
	 * @param in
	 * @return 
	 * @throws IOException
	 */
	public static String stream2String(InputStream in) throws IOException {
		// 保存在内存流里一次性读出来
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		byte[] buf = new byte[1024];
		int len = -1;
		while ((len = in.read(buf)) != -1) {
			out.write(buf, 0, len);
		}
		return out.toString();
	}

}
