package com.lvtu.monitor.module.po;

import com.lvtu.monitor.shop.entity.Goods;

public class SortableGoods implements Comparable<SortableGoods> {

	private Goods goods;

	private Float weight;

	public Goods getGoods() {
		return goods;
	}

	public void setGoods(Goods goods) {
		this.goods = goods;
	}

	public Float getWeight() {
		return weight;
	}

	public void setWeight(Float weight) {
		this.weight = weight;
	}

	@Override
	public int compareTo(SortableGoods o) {
		return o.weight.compareTo(this.weight);
	}

}
