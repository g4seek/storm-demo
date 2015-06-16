package com.lvtu.monitor;

import org.nutz.dao.Dao;
import org.nutz.mvc.Mvcs;
import org.nutz.mvc.annotation.At;
import org.nutz.mvc.annotation.Encoding;
import org.nutz.mvc.annotation.IocBy;
import org.nutz.mvc.annotation.Modules;
import org.nutz.mvc.annotation.SetupBy;
import org.nutz.mvc.ioc.provider.ComboIocProvider;

import com.lvtu.monitor.setup.StormSetup;
import com.lvtu.monitor.shop.entity.Behavior;
import com.lvtu.monitor.shop.entity.Category;
import com.lvtu.monitor.shop.entity.Customer;
import com.lvtu.monitor.shop.entity.CustomerTagWeight;
import com.lvtu.monitor.shop.entity.Goods;

/**
 * @Title: MainModule.java
 * @Package com.lvtu.monitor
 * @Description: nutz框架的主模块
 * @author lvzimin
 * @date 2015年5月14日 下午12:55:48
 * @version V1.0.0
 */
@SetupBy(StormSetup.class)
@Modules(scanPackage = true)
@IocBy(type = ComboIocProvider.class, args = { "*json", "dao.js",
		"*annotation", "com.lvtu.monitor" })
@Encoding(input = "UTF-8", output = "UTF-8")
public class MainModule {

	@At
	public String initDb() {
		Dao dao = Mvcs.getIoc().get(Dao.class, "dao");
		dao.create(Category.class, false);
		dao.create(Behavior.class, false);
		dao.create(Customer.class, false);
		dao.create(Goods.class, false);
		dao.create(CustomerTagWeight.class, false);
		return "ok";
	}
}
