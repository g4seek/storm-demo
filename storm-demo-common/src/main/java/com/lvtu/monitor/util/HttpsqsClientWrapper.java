package com.lvtu.monitor.util;

import com.lvtu.monitor.util.Constant.PROPERTY_FILE;
import com.lvtu.monitor.util.httpsqs4j.Httpsqs4j;
import com.lvtu.monitor.util.httpsqs4j.HttpsqsClient;
import com.lvtu.monitor.util.httpsqs4j.HttpsqsException;

/** 
 * @Title: HttpsqsClientWrapper.java 
 * @Package com.lvtu.monitor.util.httpsqs4j 
 * @Description: HttpsqsClient的单例包装类 
 * @author lvzimin 
 * @date 2015年5月14日 下午2:47:37 
 * @version V1.0.0 
 */
public class HttpsqsClientWrapper {

	private static HttpsqsClient client;
	
	public static HttpsqsClient getClient() {
		return client;
	}
	
	public static void init() {
		String connectUrl = Constant.getValue("httpsqs.connectUrl", PROPERTY_FILE.HTTPSQS);
		String charset = Constant.getValue("httpsqs.charset", PROPERTY_FILE.HTTPSQS);
		try {
			Httpsqs4j.setConnectionInfo(connectUrl, charset);
			HttpsqsClient newClient = Httpsqs4j.createNewClient();
			client = newClient;
		} catch (HttpsqsException e) {
			e.printStackTrace();
		}
	}
	
}
