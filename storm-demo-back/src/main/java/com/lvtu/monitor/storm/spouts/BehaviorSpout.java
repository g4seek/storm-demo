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
import com.lvtu.monitor.util.Constant;
import com.lvtu.monitor.util.Constant.PROPERTY_FILE;
import com.lvtu.monitor.util.httpsqs4j.Httpsqs4j;
import com.lvtu.monitor.util.httpsqs4j.HttpsqsClient;
import com.lvtu.monitor.util.httpsqs4j.HttpsqsException;
import com.lvtu.monitor.utils.DbHelper;

public class BehaviorSpout extends BaseRichSpout {

	private static final long serialVersionUID = -2304593696561644879L;

	private Log logger = LogFactory.getLog(BehaviorSpout.class);
	
	private HttpsqsClient httpsqsClient = null;

	private SpoutOutputCollector collector;
	
	@Override
	public void nextTuple() {
		Dao dao = DbHelper.getDao();

		HttpsqsClient client = this.getHttpsqsClient();
		String behaviorStr = null;
		
		try {
			// 从httpsqs队列中读取数据,格式--customerId,opType,goodsId
			behaviorStr = client.getString("storm-demo");
		} catch (HttpsqsException e) {
			try {
				// 队列中无数据,休眠10s
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

		// 组装tuple数据对象
		UgcData data = new UgcData();
		data.setCustomerId(customerId);
		data.setOperation(opType);
		data.setGoodsId(goodsId);
		data.setGoodsName(goods.getName());
		data.setGoodsTags(goods.getTags());

		logger.info("emit ugcdata..." + data.toString());

		// 提交给topology中后面的节点
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
		// 定义输出数据的属性
		declarer.declare(new Fields("ugcData"));
	}
	
	private HttpsqsClient getHttpsqsClient() {
		if (httpsqsClient == null) {
			String connectUrl = Constant.getValue("httpsqs.connectUrl", PROPERTY_FILE.HTTPSQS);
			String charset = Constant.getValue("httpsqs.charset", PROPERTY_FILE.HTTPSQS);
			try {
				logger.info("connectUrl:" + connectUrl);
				Httpsqs4j.setConnectionInfo(connectUrl, charset);
				httpsqsClient = Httpsqs4j.createNewClient();
				logger.info("client:" + httpsqsClient.getStatus("storm-demo"));
			} catch (HttpsqsException e) {
				e.printStackTrace();
			}
		}
		return httpsqsClient;
	}

}
