package com.lvtu.monitor;

import backtype.storm.LocalCluster;

import com.lvtu.monitor.storm.topology.BehaviorTopology;
import com.lvtu.monitor.utils.InitHelper;

public class MainRoutine {

	public static void main(String[] args) {
		InitHelper.init();
		LocalCluster cluster = new LocalCluster();
		BehaviorTopology topology = new BehaviorTopology();
		cluster.submitTopology(topology.getName(), topology.getConfig(),
				topology.getTopology());
	}
}
