package com.lvtu.monitor.storm.spouts;

import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.nutz.dao.Dao;

import backtype.storm.spout.SpoutOutputCollector;
import backtype.storm.task.TopologyContext;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.topology.base.BaseRichSpout;
import backtype.storm.tuple.Fields;
import backtype.storm.tuple.Values;

import com.lvtu.monitor.shop.entity.Goods;
import com.lvtu.monitor.storm.data.UgcData;
import com.lvtu.monitor.util.HttpsqsClientWrapper;
import com.lvtu.monitor.util.httpsqs4j.HttpsqsClient;
import com.lvtu.monitor.util.httpsqs4j.HttpsqsException;
import com.lvtu.monitor.utils.DbHelper;

public class BehaviorSpout extends BaseRichSpout {

	private static final long serialVersionUID = -2304593696561644879L;

	private Log logger = LogFactory.getLog(BehaviorSpout.class);

	private SpoutOutputCollector collector;

	@Override
	public void nextTuple() {
		Dao dao = DbHelper.getDao();

		HttpsqsClient client = HttpsqsClientWrapper.getClient();
		String behaviorStr = null;
		
		try {
			behaviorStr = client.getString("storm-demo");
		} catch (HttpsqsException e) {
			try {
				Thread.sleep(10000L);
			} catch (InterruptedException e2) {
			}
			return;
		}

		String[] behaviorArr = behaviorStr.replace("\n", "").split(",");
		int customerId = Integer.valueOf(behaviorArr[0]);
		String opType = behaviorArr[1];
		int goodsId = Integer.valueOf(behaviorArr[2]);

		Goods goods = dao.fetch(Goods.class, goodsId);

		UgcData data = new UgcData();
		data.setCustomerId(customerId);
		data.setOperation(opType);
		data.setGoodsId(goodsId);
		data.setGoodsName(goods.getName());
		data.setGoodsTags(goods.getTags());

		logger.info("emit ugcdata..." + data.toString());

		collector.emit(new Values(data));
	}

	@SuppressWarnings("rawtypes")
	@Override
	public void open(Map map, TopologyContext context,
			SpoutOutputCollector collector) {
		this.collector = collector;
	}

	@Override
	public void declareOutputFields(OutputFieldsDeclarer declarer) {
		declarer.declare(new Fields("ugcData"));
	}

}
