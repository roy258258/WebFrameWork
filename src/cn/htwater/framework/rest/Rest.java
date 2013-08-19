package cn.htwater.framework.rest;

import cn.htwater.framework.dao.MyDao;
import cn.htwater.framework.dao.MyDataSource;

public class Rest {

	protected MyDao dao = new MyDao();
	protected MyDao dao2nd = new MyDao(MyDataSource.MYDS2);

}
