package com.lvtu.monitor.storm.bolts;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.nutz.dao.Cnd;
import org.nutz.dao.Dao;

import backtype.storm.topology.BasicOutputCollector;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.topology.base.BaseBasicBolt;
import backtype.storm.tuple.Fields;
import backtype.storm.tuple.Tuple;

import com.lvtu.monitor.shop.entity.CustomerTagWeight;
import com.lvtu.monitor.storm.data.UgcData;
import com.lvtu.monitor.storm.spouts.BehaviorSpout;
import com.lvtu.monitor.utils.DbHelper;

public class BehaviorBolt extends BaseBasicBolt {

	private static final long serialVersionUID = 4236885687387212731L;

	private Log logger = LogFactory.getLog(BehaviorSpout.class);

	@Override
	public void execute(Tuple tuple, BasicOutputCollector collector) {
		Dao dao = DbHelper.getDao();

		// 读取上个节点提交的数据
		Object obj = tuple.getValueByField("ugcData");
		UgcData data = (UgcData) obj;
		int customerId = data.getCustomerId();

		for (String tag : data.getGoodsTags()) {
			CustomerTagWeight tagWeight = dao.fetch(
					CustomerTagWeight.class,
					Cnd.where("customerId", "=", customerId).and("tagName",
							"=", tag));
			int addWeight = this.getOperationWeight(data.getOperation());
			if (tagWeight != null) {
				// 修改用户标签关系的权重值
				tagWeight.setWeight(tagWeight.getWeight() + addWeight);
				logger.info("update tagWeight [" + tagWeight.getTagName() + "]");
				dao.update(tagWeight);
			} else {
				// 新增用户标签权重关系
				tagWeight = new CustomerTagWeight();
				tagWeight.setCustomerId(customerId);
				tagWeight.setTagName(tag);
				tagWeight.setWeight(addWeight);
				logger.info("insert tagWeight [" + tagWeight.getTagName() + "]");
				dao.insert(tagWeight);
			}
		}
	}

	private int getOperationWeight(String operation) {
		if ("VIEW".equals(operation)) {
			return 1;
		} else if ("LIKE".equals(operation)) {
			return 5;
		} else if ("BUY".equals(operation)) {
			return 30;
		}
		return 0;
	}

	@Override
	public void declareOutputFields(OutputFieldsDeclarer declarer) {
		// 定义输出数据的属性名称
		declarer.declare(new Fields("ugcData"));
	}

}
