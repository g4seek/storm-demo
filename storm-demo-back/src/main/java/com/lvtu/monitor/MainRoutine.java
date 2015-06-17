package com.lvtu.monitor;

import backtype.storm.LocalCluster;
import backtype.storm.StormSubmitter;
import backtype.storm.generated.AlreadyAliveException;
import backtype.storm.generated.InvalidTopologyException;

import com.lvtu.monitor.storm.topology.BehaviorTopology;
import com.lvtu.monitor.utils.InitHelper;

public class MainRoutine {

	public static void main(String[] args) throws AlreadyAliveException, InvalidTopologyException {
		InitHelper.init();
		BehaviorTopology topology = new BehaviorTopology();
		if (args.length == 0) {
			LocalCluster cluster = new LocalCluster();
			cluster.submitTopology(topology.getName(), topology.getConfig(),
					topology.getTopology());
		} else {
			StormSubmitter.submitTopology(topology.getName(),
					topology.getConfig(), topology.getTopology());
		}
	}
}
