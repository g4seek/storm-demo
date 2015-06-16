package com.lvtu.monitor.utils;

import org.nutz.dao.Dao;
import org.nutz.dao.impl.NutDao;
import org.nutz.dao.impl.SimpleDataSource;

import com.lvtu.monitor.util.Constant;
import com.lvtu.monitor.util.Constant.PROPERTY_FILE;

public class DbHelper {

	private static Dao dao;

	public static Dao getDao() {
		if (dao == null) {
			SimpleDataSource ds = new SimpleDataSource();

			String jdbcUrl = Constant.getValue("jdbc.url", PROPERTY_FILE.JDBC);
			String username = Constant.getValue("jdbc.username",
					PROPERTY_FILE.JDBC);
			String password = Constant.getValue("jdbc.password",
					PROPERTY_FILE.JDBC);
			ds.setJdbcUrl(jdbcUrl);
			ds.setUsername(username);
			ds.setPassword(password);

			dao = new NutDao(ds);
		}
		return dao;
	}

}
