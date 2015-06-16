package com.lvtu.monitor.storm.topology;

import backtype.storm.Config;
import backtype.storm.generated.StormTopology;
import backtype.storm.topology.TopologyBuilder;

import com.lvtu.monitor.storm.bolts.BehaviorBolt;
import com.lvtu.monitor.storm.spouts.BehaviorSpout;

public class BehaviorTopology implements LvtuTopoloty{

	@Override
	public String getName() {
		return "behaviorTopology";
	}

	@Override
	public Config getConfig() {
		Config conf = new Config();
		conf.setDebug(true);
		conf.put(Config.TOPOLOGY_MAX_SPOUT_PENDING, 1);
		return conf;
	}

	@Override
	public StormTopology getTopology() {
		TopologyBuilder builder = new TopologyBuilder();
		BehaviorBolt bolt = new BehaviorBolt();
		BehaviorSpout spout = new BehaviorSpout();
		builder.setSpout("behaviorSpout", spout);
		builder.setBolt("behaviorBolt", bolt, 1)
				.shuffleGrouping("behaviorSpout");
		return builder.createTopology();
	}

}
