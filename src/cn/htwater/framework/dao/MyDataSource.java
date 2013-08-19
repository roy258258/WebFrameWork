package cn.htwater.framework.dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.apache.commons.dbcp.BasicDataSource;
import org.apache.commons.dbcp.BasicDataSourceFactory;

import cn.htwater.framework.utils.MyLog;

public class MyDataSource {

	/* please config your datasource here begain */
	public static final String MYDS1 = "myds1";
	public static final String MYDS2 = "myds2";

	private static MyDSConfig dsConfigList[] = {
			new MyDSConfig(MYDS1,
					"com.microsoft.sqlserver.jdbc.SQLServerDriver",
					"jdbc:sqlserver://192.168.100.1;databaseName=SZJC", "sa",
					"htwater1,nbsl"),
			new MyDSConfig(MYDS2,
					"com.microsoft.sqlserver.jdbc.SQLServerDriver",
					"jdbc:sqlserver://localhost;databaseName=SZJC", "sa",
					"nbht@nbszc"), };

	/* config datasource here end */

	private static List<MyDsConnection> dsList = new ArrayList<MyDsConnection>();

	public static void init() {

		if (dsList.size() != 0) {

			for (MyDsConnection bDs : dsList) {
				try {
					bDs.getBds().close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				bDs = null;
			}
		}

		for (MyDSConfig dsc : dsConfigList) {

			Properties p = new Properties();
			p.setProperty(MyDSConfig.DRIVERCLASSNAME, dsc.getDriverClassName());
			p.setProperty(MyDSConfig.URL, dsc.getUrl());
			p.setProperty(MyDSConfig.USERNAME, dsc.getUsername());
			p.setProperty(MyDSConfig.PASSWORD, dsc.getPassword());
			BasicDataSource bds = null;
			try {
				bds = (BasicDataSource) BasicDataSourceFactory
						.createDataSource(p);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				MyLog.i("MyDataSource:CreateDataSource Failed:"
						+ dsc.getDbName());
				bds = null;
			}

			if (null != bds) {
				MyDsConnection mds = new MyDsConnection(dsc.getDbName(), bds);
				MyLog.i("MyDataSource:CreateDataSource Success:"
						+ mds.getDsName());
				dsList.add(mds);
			}
		}
	}

	public static BasicDataSource getCurDataSoureByDSName(String dsName) {

		MyDsConnection curDsc = null;
		if (null == dsName) {

			MyLog.i("getCurDataSoureByDSName Failed: no dsName");
			return null;
		}

		if (dsList.size() == 0) {
			init();
		}

		for (MyDsConnection bDs : dsList) {

			if (bDs.getDsName().equals(dsName)) {
				curDsc = bDs;
			}
		}

		if (null != curDsc) {
			return curDsc.getBds();
		}

		MyLog.i("MyDataSource:getCurDataSoureByDSName Failed: No Such Ds:"
				+ dsName);
		return null;

	}

	public static synchronized Connection getConnectionByDSName(String dsName)
			throws SQLException {

		MyDsConnection curDsc = null;

		if (null == dsName) {

			MyLog.i("MyDataSource:getConnectionByDSName Failed: no dsName");
			return null;
		}

		if (dsList.size() == 0) {
			init();
		}

		for (MyDsConnection bDs : dsList) {

			if (bDs.getDsName().equals(dsName)) {
				curDsc = bDs;
			}
		}

		if (null != curDsc) {
			return curDsc.getBds().getConnection();
		}

		MyLog.i("MyDataSource:getConnectionByDSName Failed: No Such Ds:"
				+ dsName);
		return null;
	}
}
