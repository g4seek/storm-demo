package com.lvtu.monitor.util;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import com.lvtu.monitor.util.Constant.PROPERTY_FILE;

public class ConfigHelper {

	/**
	 * 缓存配置文件的Map(key:配置文件,value:配置项)
	 */
	private static Map<PROPERTY_FILE, Properties> propFileMap;

	static {
		if (propFileMap == null) {
			propFileMap = new HashMap<PROPERTY_FILE, Properties>();
		}
	}

	/**
	 * 根据路径返回配置文件
	 * 
	 * @param path
	 * @return
	 */
	public static final Properties getProperties(PROPERTY_FILE propertyFile) {
		
		if (propFileMap.get(propertyFile) == null) {
			Properties properties = new Properties();
			try {
				InputStream inputStream = ConfigHelper.class.getClassLoader()
						.getResourceAsStream(propertyFile.getFileName());
				properties.load(inputStream);
			} catch (FileNotFoundException e) {
			} catch (IOException e) {
				e.printStackTrace();
			}
			propFileMap.put(propertyFile, properties);
			return properties;
		} else {
			return propFileMap.get(propertyFile);
		}
		
	}
}