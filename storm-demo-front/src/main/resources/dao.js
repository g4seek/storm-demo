var ioc = {
	// 读取配置文件
	config : {
		type : "org.nutz.ioc.impl.PropertiesProxy",
		fields : {
			paths : [ "jdbc.properties" ]
		}
	},
	dao : {
		type : "org.nutz.dao.impl.NutDao",
		args : [ {
			refer : "dataSource"
		} ]
	},
	// 数据源
	dataSource : {
		type : "org.nutz.dao.impl.SimpleDataSource",
		fields : {
			jdbcUrl : {
				java : "$config.get('jdbc.url')"
			},
			username : {
				java : "$config.get('jdbc.username')"
			},
			password : {
				java : "$config.get('jdbc.password')"
			}
		}
	}
};