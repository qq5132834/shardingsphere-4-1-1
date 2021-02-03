package org.apache.shardingsphere.shardingjdbc.test;

import org.apache.shardingsphere.api.sharding.standard.PreciseShardingAlgorithm;
import org.apache.shardingsphere.api.sharding.standard.PreciseShardingValue;

import java.util.Collection;

/***
 * 自定一个算法处理一个字段的返回的表
 */
final public class TestHLPreciseShardingAlgorithm implements PreciseShardingAlgorithm<Integer> {

    @Override
    public String doSharding(Collection<String> availableTargetNames, PreciseShardingValue<Integer> shardingValue) {
        System.out.println("TestHLPreciseShardingAlgorithm.doSharding:" + shardingValue.getLogicTableName() + "," + shardingValue.getColumnName() + "," + shardingValue.getValue());
        for (String each : availableTargetNames) {
            System.out.println("TestHLPreciseShardingAlgorithm.doSharding.each:" + each);
            if (each.endsWith(shardingValue.getValue() % 2 + "")) {
                return each;
            }
        }
        throw new UnsupportedOperationException();
    }
}
