package cn.htwater.framework.listener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import cn.htwater.framework.dao.MyDataSource;
import cn.htwater.framework.utils.MyLog;

public class MyContextListener implements ServletContextListener {

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		// TODO Auto-generated method stub
		MyLog.i("MyContextListener:contextDestroyed");
	}

	@Override
	public void contextInitialized(ServletContextEvent sce) {
		// TODO Auto-generated method stub
		MyLog.i("MyContextListener:contextInitialized");
		doInit();
	}

	private void doInit() {
		MyLog.i("MyContextListener:doInit");
		MyDataSource.init();
	}

}
