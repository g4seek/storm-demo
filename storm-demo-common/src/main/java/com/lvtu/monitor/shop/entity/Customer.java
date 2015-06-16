package com.lvtu.monitor.shop.entity;

import org.nutz.dao.entity.annotation.Id;

public class Customer {
	
	@Id(auto = true)
	private int id;
	
	private String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getId() {
		return id;
	}
	
}
