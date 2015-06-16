package com.lvtu.monitor.shop.entity;

import org.nutz.dao.entity.annotation.Id;

public class Goods {

	@Id(auto = true)
	private int id;

	private String name;

	private String[] tags;

	public int getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String[] getTags() {
		return tags;
	}

	public void setTags(String[] tags) {
		this.tags = tags;
	}
	
}
