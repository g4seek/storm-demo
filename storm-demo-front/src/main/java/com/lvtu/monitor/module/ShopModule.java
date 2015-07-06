package com.lvtu.monitor.module;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.nutz.dao.Cnd;
import org.nutz.dao.Dao;
import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.mvc.annotation.At;
import org.nutz.mvc.annotation.Ok;

import com.lvtu.monitor.module.po.SortableGoods;
import com.lvtu.monitor.shop.entity.Behavior;
import com.lvtu.monitor.shop.entity.Customer;
import com.lvtu.monitor.shop.entity.CustomerTagWeight;
import com.lvtu.monitor.shop.entity.Goods;
import com.lvtu.monitor.util.HttpsqsClientWrapper;
import com.lvtu.monitor.util.httpsqs4j.HttpsqsClient;
import com.lvtu.monitor.util.httpsqs4j.HttpsqsException;

@IocBean
@At("/")
public class ShopModule {

	@Inject
	private Dao dao;

	/**
	 * 商品列表
	 * 
	 * @param msg
	 * @return
	 */
	@At("/")
	@Ok("jsp:page.shop")
	public Map<String, Object> list(HttpServletRequest req, String msg) {

		Object customerObj = req.getSession().getAttribute("customer");
		List<Goods> goodsList = dao.query(Goods.class, null);

		Map<String, Object> result = new HashMap<>();
		result.put("goodsList", goodsList);
		result.put("msg", msg);
		if (customerObj != null) {
			result.put("customer", (Customer) customerObj);
		}
		return result;
	}

	/**
	 * 用户登录
	 * 
	 * @param customerId
	 * @param name
	 * @return
	 */
	@At("/login")
	@Ok("redirect:/?msg=${obj}")
	public String login(HttpServletRequest req, int customerId, String name) {

		Customer customer = dao.fetch(Customer.class, customerId);
		if (customer == null) {
			customer = new Customer();
			customer.setName(name);
			dao.insert(customer);
		}
		req.getSession().setAttribute("customer", customer);
		return "customer [" + customer.getId() + "] login";
	}

	/**
	 * 模拟用户操作
	 * 
	 * @param opType
	 * @param goodsId
	 */
	@At
	@Ok("redirect:/?msg=${obj}")
	public String operate(HttpServletRequest req, String opType, int goodsId) {

		// TODO 优化redirect,链接中不加参数
		int customerId = this.getCustomerId(req);
		Behavior behavior = new Behavior();
		behavior.setCustomerId(customerId);
		behavior.setGoodsId(goodsId);
		behavior.setType(opType);
		dao.insert(behavior);
		this.send2Queue(behavior);

		String msg = "customer [" + customerId + "] " + opType + " goods ["
				+ goodsId + "]";
		return msg;
	}

	/**
	 * 显示操作历史
	 * 
	 * @return
	 */
	@At
	@Ok("jsp:page.history")
	public Map<String, Object> history(HttpServletRequest req) {

		int customerId = this.getCustomerId(req);
		List<Behavior> historyList = dao.query(Behavior.class,
				Cnd.where("customerId", "=", customerId));

		Map<String, Object> result = new HashMap<>();
		result.put("historyList", historyList);
		return result;
	}

	/**
	 * 展示推荐商品
	 * 
	 * @param customerId
	 * @return
	 */
	@At
	@Ok("jsp:page.recommend")
	public Map<String, Object> recommend(HttpServletRequest req) {

		int customerId = this.getCustomerId(req);
		List<CustomerTagWeight> tagWeightList = dao.query(
				CustomerTagWeight.class,
				Cnd.where("customerId", "=", customerId));
		List<Goods> allGoods = dao.query(Goods.class, null);

		List<SortableGoods> sortableList = new ArrayList<>();
		for (Goods goods : allGoods) {

			String[] goodsTags = goods.getTags();
			float weightSum = 0f;
			for (String goodsTag : goodsTags) {
				for (CustomerTagWeight tagWeight : tagWeightList) {
					if (goodsTag.equals(tagWeight.getTagName())) {
						weightSum += tagWeight.getWeight();
					}
				}
			}
			float weight = weightSum / goodsTags.length;
			SortableGoods sortableGoods = new SortableGoods();
			sortableGoods.setWeight(weight);
			sortableGoods.setGoods(goods);
			sortableList.add(sortableGoods);
		}

		Collections.sort(sortableList);

		Map<String, Object> result = new HashMap<>();
		result.put("tagWeightList", tagWeightList);
		result.put("sortableList", sortableList);
		return result;
	}

	/**
	 * 发送操作记录到队列里
	 * 
	 * @param behavior
	 */
	private void send2Queue(Behavior behavior) {
		HttpsqsClient client = HttpsqsClientWrapper.getClient();
		try {
			client.putString("storm-demo", behavior.toString());
		} catch (HttpsqsException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 从session获取用户Id
	 * 
	 * @param request
	 * @return
	 */
	private int getCustomerId(HttpServletRequest request) {
		Object obj = request.getSession().getAttribute("customer");
		if (obj == null) {
			return 1;
		} else {
			Customer customer = (Customer) obj;
			return customer.getId();
		}

	}

}
