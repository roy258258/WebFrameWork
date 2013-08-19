package cn.htwater.framework.dao;

import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.JdbcTemplate;

public class MyDao {

	private String dsName = null;
	private JdbcTemplate jdbcTemplate = null;

	public MyDao() {
		dsName = MyDataSource.MYDS1;
		jdbcTemplate = new JdbcTemplate(
				MyDataSource.getCurDataSoureByDSName(dsName));
	}

	public MyDao(String dsName) {
		this.dsName = dsName;
		jdbcTemplate = new JdbcTemplate(
				MyDataSource.getCurDataSoureByDSName(dsName));
	}

	public List<Map<String, Object>> query(String sql) {

		return jdbcTemplate.queryForList(sql);
	}

	public void exec(String sql) {
		jdbcTemplate.execute(sql);
	}

	public JdbcTemplate getJdbcTemplate() {

		return jdbcTemplate;
	}

}
