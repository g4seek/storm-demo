package com.lvtu.monitor.storm.data;

import java.util.Arrays;

public class UgcData {

	private int customerId;
	
	private int goodsId;
	
	private String goodsName;
	
	private String [] goodsTags;
	
	private String operation;

	public int getCustomerId() {
		return customerId;
	}

	public void setCustomerId(int customerId) {
		this.customerId = customerId;
	}

	public int getGoodsId() {
		return goodsId;
	}

	public void setGoodsId(int goodsId) {
		this.goodsId = goodsId;
	}

	public String getGoodsName() {
		return goodsName;
	}

	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
	}

	public String[] getGoodsTags() {
		return goodsTags;
	}

	public void setGoodsTags(String[] goodsTags) {
		this.goodsTags = goodsTags;
	}

	public String getOperation() {
		return operation;
	}

	public void setOperation(String operation) {
		this.operation = operation;
	}

	@Override
	public String toString() {
		return "UgcData [customerId=" + customerId + ", goodsId=" + goodsId
				+ ", goodsName=" + goodsName + ", goodsTags="
				+ Arrays.toString(goodsTags) + ", operation=" + operation + "]";
	}
	
}
