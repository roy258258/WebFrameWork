package cn.htwater.framework.dao;

import org.apache.commons.dbcp.BasicDataSource;

public class MyDsConnection {

	private String dsName;
	private BasicDataSource bds;

	public String getDsName() {
		return dsName;
	}

	public void setDsName(String dsName) {
		this.dsName = dsName;
	}

	public BasicDataSource getBds() {
		return bds;
	}

	public void setBds(BasicDataSource bds) {
		this.bds = bds;
	}

	public MyDsConnection(String dsName, BasicDataSource bds) {

		this.dsName = dsName;
		this.bds = bds;
	}

}
