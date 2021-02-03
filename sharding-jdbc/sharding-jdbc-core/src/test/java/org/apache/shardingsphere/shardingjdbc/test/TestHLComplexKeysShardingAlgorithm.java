package org.apache.shardingsphere.shardingjdbc.test;

import com.google.common.collect.Range;
import org.apache.shardingsphere.api.sharding.complex.ComplexKeysShardingAlgorithm;
import org.apache.shardingsphere.api.sharding.complex.ComplexKeysShardingValue;

import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.Map;

/***
 * 自定义一个复合查询
 */
final public class TestHLComplexKeysShardingAlgorithm implements ComplexKeysShardingAlgorithm<Integer> {

    @Override
    public Collection<String> doSharding(Collection<String> availableTargetNames, ComplexKeysShardingValue<Integer> shardingValue) {
        System.out.println("TestHLComplexKeysShardingAlgorithm.doSharding:localTableName:" + shardingValue.getLogicTableName());
        Map<String, Range<Integer>> rangeMap = shardingValue.getColumnNameAndRangeValuesMap();
        if(rangeMap != null && rangeMap.size() > 0){
            for (Map.Entry<String, Range<Integer>> entry : rangeMap.entrySet()) {

            }
        }
        Map<String, Collection<Integer>> collectionMap = shardingValue.getColumnNameAndShardingValuesMap();
        if(collectionMap != null && collectionMap.size() > 0){
            for (Map.Entry<String, Collection<Integer>> entry : collectionMap.entrySet()) {
                System.out.println(entry.getKey() + "," + entry.getValue());
            }
        }

//        Collection<String> cols = new LinkedHashSet<>();
//        cols.add("t_order_1");
//        return cols;

        return availableTargetNames;
    }
}
