package com.lvtu.monitor.setup;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.nutz.ioc.Ioc;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.mvc.NutConfig;
import org.nutz.mvc.Setup;

import com.lvtu.monitor.util.HttpsqsClientWrapper;

/**
 * @Title: StormSetup.java
 * @Package com.lvtu.monitor.setup
 * @Description: 应用启动和停止时执行
 * @author lvzimin
 * @date 2015年5月14日 下午12:44:02
 * @version V1.0.0
 */
@IocBean
public class StormSetup implements Setup {

	private Log log = LogFactory.getLog(this.getClass());

	@Override
	public void init(NutConfig config) {

		log.info("stormSetup.init...");
		Ioc ioc = config.getIoc();
		// 初始化httpsqs队列客户端
		this.initHttpsqsClient(ioc);
	}

	@Override
	public void destroy(NutConfig config) {
		log.info("stormSetup.destroy...");
	}

	private void initHttpsqsClient(Ioc ioc) {
		HttpsqsClientWrapper.init();
	}

}
