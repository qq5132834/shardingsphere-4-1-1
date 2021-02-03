package org.apache.shardingsphere.shardingjdbc.test;

import org.apache.shardingsphere.api.sharding.standard.PreciseShardingAlgorithm;
import org.apache.shardingsphere.api.sharding.standard.PreciseShardingValue;

import java.util.Collection;

/***
 * 数据库分片算法
 */
public class TestHLDatabasePreciseShardingAlgorithm implements PreciseShardingAlgorithm<Integer> {

    @Override
    public String doSharding(Collection<String> availableTargetNames, PreciseShardingValue<Integer> shardingValue) {
        System.out.println("TestHLDatabasePreciseShardingAlgorithm.doSharding.availableTargetNames:" + availableTargetNames);
        for (String each : availableTargetNames) {
            if(each.endsWith(shardingValue.getValue() % 2 + "")){
                return each;
            }
        }
        return null;
    }

}
