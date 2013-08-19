package cn.htwater.framework.rest;

import java.util.List;
import java.util.Map;

public class DemoRest extends Rest {

	public List<Map<String, Object>> getWaterProjects(Map<String, String> params) {

		String sql = "select * from  WaterInvolve";
		return dao.query(sql);
	}

	

	public static void main(String[] args) {
		
		
	}
}
