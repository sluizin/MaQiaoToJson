
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

import com.alibaba.fastjson.JSON;

import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.junit.Test;

import net.sf.json.*;
import MaQiao.MaQiaoToJson.MQToJson;

@SuppressWarnings("unused")
public class testing_Corp_java {

	// private static Connection dbConn = null;

	@Test
	public void test4() throws JsonProcessingException {
		Monitoring.begin();
		List<Corp> list = new ArrayList<Corp>();
		for (int i = 0; i < 1000; i++) {
			//list.add(fullObject(Corp.class));
			list.add(new Corp());
		}
		Monitoring.end("生成数据");
		int ssize = 1;
		for (int i = 0; i < ssize; i++) {
			Monitoring.begin();
			MQjsonOffset(list);
			Monitoring.end("Offset");
		}

		for (int i = 0; i < ssize; i++) {
			Monitoring.begin();
			jackson(list);
			Monitoring.end("Jackson");
		}

		for (int i = 0; i < ssize; i++) {
			Monitoring.begin();
			fastjson(list);
			Monitoring.end("fastjson");
		}

	}

	public static void MQjsonOffset(List<Corp> list) {
		String a = null;
		int len = list.size();
		for (Corp corp : list)
			a = MQToJson.toJsonString(corp);
		//System.out.println(a);
	}

	public static void libjson(List<Corp> list) {
		String string = null;
		for (Corp corp : list) {
			string = JSONObject.fromObject(corp).toString();
		}
		//System.out.println(string);
	}

	public static void fastjson(List<Corp> list) {
		String string = null;
		for (Corp corp : list) {
			string = JSON.toJSONString(corp);
		}
		//System.out.println(string);
	}

	public static void jackson(List<Corp> list) throws JsonProcessingException {
		ObjectMapper a = new ObjectMapper();
		String string = null;
		for (Corp corp : list) {
			try {
				string = a.writeValueAsString(corp);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		//System.out.println(string);
	}

	/**
	 * 填充一个对象（一般用于测试）
	 */
	@SuppressWarnings("rawtypes")
	public static <T> T fullObject(Class<T> cl) {
		T t = null;
		try {
			t = cl.newInstance();
			Method methods[] = cl.getMethods();
			for (Method method : methods) {
				// 如果是set方法,进行随机数据的填充
				if (method.getName().indexOf("set") == 0) {
					Class param = method.getParameterTypes()[0];
					if (param.equals(String.class)) {
						method.invoke(t, "aabbcc");
					} else if (param.equals(Short.class)) {
						method.invoke(t, (short) new Random().nextInt(5));
					} else if (param.equals(Float.class)) {
						method.invoke(t, new Random().nextFloat());
					} else if (param.equals(Double.class)) {
						method.invoke(t, new Random().nextDouble());
					} else if (param.equals(Integer.class)) {
						method.invoke(t, new Random().nextInt(10));
					} else if (param.equals(Long.class)) {
						method.invoke(t, new Random().nextLong());
					} else if (param.equals(Date.class)) {
						method.invoke(t, new Date());
					} else if (param.equals(Timestamp.class)) {
						method.invoke(t, new Timestamp(System.currentTimeMillis()));
					} else if (param.equals(java.sql.Date.class)) {
						method.invoke(t, new java.sql.Date(System.currentTimeMillis()));
					}
				}
			}
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
		return t;
	}
}
