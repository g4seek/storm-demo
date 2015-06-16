package com.lvtu.monitor.shop.entity;

import org.nutz.dao.entity.annotation.Id;

public class Category {

	@Id(auto = true)
	private int id;

	private String name;

	public int getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
