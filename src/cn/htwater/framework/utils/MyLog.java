package cn.htwater.framework.utils;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class MyLog {

	static final Log myLog = LogFactory.getLog(MyLog.class);
	static final boolean DEBUG = false;

	public static void i(String msg) {
		if (DEBUG) {
			myLog.info(msg);
		}
	}
}