package cn.htwater.framework.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.security.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import net.sf.json.processors.JsDateJsonValueProcessor;

@SuppressWarnings("serial")
public class RestServlet extends HttpServlet {

	/**
	 * 
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		response.setCharacterEncoding("UTF-8");
		request.setCharacterEncoding("UTF-8");
		PrintWriter out = response.getWriter();

		String path = request.getPathInfo();
		Map<String, String[]> parasMap = request.getParameterMap();

		Map<String, String> params = new HashMap<String, String>();
		if (null != parasMap) {

			Set<String> set = parasMap.keySet();
			for (String s : set) {
				params.put(s, parasMap.get(s)[0]);
			}
		}
		int tempIndex = 0;
		tempIndex = path.indexOf("/");
		if (-1 == tempIndex) {

			out.println("<URI><![CDATA[");
			out.println("RequestURL Error!");
			out.println("]]></URI>");
			out.flush();
			out.close();
			return;
		}
		String tempString = path.substring(tempIndex + 1);
		if (null != tempString && tempString.length() != 0) {

			String classAndMethod[] = tempString.split("!");
			if (classAndMethod.length != 2) {
				out.println("<URI><![CDATA[");
				out.println("Request Class or Method Error!");
				out.println("]]></URI>");
				out.flush();
				out.close();
				return;
			} else {

				String className = classAndMethod[0];
				String methodName = classAndMethod[1];

				if (null == className || className.length() == 0) {

					out.println("<URI><![CDATA[");
					out.println("Request Class Name Error!");
					out.println("]]></URI>");
					out.flush();
					out.close();
					return;
				}

				if (null == methodName || methodName.length() == 0) {

					out.println("<URI><![CDATA[");
					out.println("Request Method  Name Error!");
					out.println("]]></URI>");
					out.flush();
					out.close();
					return;
				}

				response.setContentType("text/javascript");
				List<Map<String, Object>> retList = doCall(className,
						methodName, params);
				if (null != retList) {
					out.print(objectToJSON(retList));
					out.flush();
					out.close();
					return;
				} else {
					out.println("<URI><![CDATA[");
					out.println("Request  Call Failed Error!");
					out.println("]]></URI>");
					out.flush();
					out.close();
					return;
				}
			}
		} else {
			out.println("<URI><![CDATA[");
			out.println("Request  path  Error!");
			out.println("]]></URI>");
			out.flush();
			out.close();
			return;
		}
	}

	private List<Map<String, Object>> doCall(String className,
			String methodName, Map<String, String> params) {

		try {
			Class<?> clazz = Class.forName("cn.htwater.framework.rest."
					+ className);
			Object obj = clazz.newInstance();
			Method method = clazz.getMethod(methodName,
					new Class[] { Map.class });
			Object retObj = method.invoke(obj, new Object[] { params });
			@SuppressWarnings("unchecked")
			List<Map<String, Object>> mList = (List<Map<String, Object>>) retObj;
			return mList;

		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return null;
	}

	private String objectToJSON(Object object) {
		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.registerJsonValueProcessor(java.util.Date.class,
				new JsDateJsonValueProcessor());
		jsonConfig.registerJsonValueProcessor(java.sql.Date.class,
				new JsDateJsonValueProcessor());
		jsonConfig.registerJsonValueProcessor(Timestamp.class,
				new JsDateJsonValueProcessor());
		if ((object instanceof Map))
			return JSONObject.fromObject(object, jsonConfig).toString();
		if ((object instanceof List)) {
			return JSONArray.fromObject(object, jsonConfig).toString();
		}
		return JSONObject.fromObject(object, jsonConfig).toString();
	}
}
