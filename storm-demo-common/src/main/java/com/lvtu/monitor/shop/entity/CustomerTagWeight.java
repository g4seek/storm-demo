package com.lvtu.monitor.shop.entity;

import org.nutz.dao.entity.annotation.PK;

@PK({ "customerId", "tagName" })
public class CustomerTagWeight {

	private int customerId;

	private String tagName;

	private int weight;

	public int getCustomerId() {
		return customerId;
	}

	public void setCustomerId(int customerId) {
		this.customerId = customerId;
	}

	public String getTagName() {
		return tagName;
	}

	public void setTagName(String tagName) {
		this.tagName = tagName;
	}

	public int getWeight() {
		return weight;
	}

	public void setWeight(int weight) {
		this.weight = weight;
	}

}
