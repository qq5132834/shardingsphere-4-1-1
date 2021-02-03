package org.apache.shardingsphere.shardingjdbc.test;

import org.apache.commons.dbcp2.BasicDataSource;
import org.apache.shardingsphere.api.config.sharding.ShardingRuleConfiguration;
import org.apache.shardingsphere.api.config.sharding.TableRuleConfiguration;
import org.apache.shardingsphere.api.config.sharding.strategy.ComplexShardingStrategyConfiguration;
import org.apache.shardingsphere.api.config.sharding.strategy.InlineShardingStrategyConfiguration;
import org.apache.shardingsphere.api.config.sharding.strategy.ShardingStrategyConfiguration;
import org.apache.shardingsphere.api.config.sharding.strategy.StandardShardingStrategyConfiguration;
import org.apache.shardingsphere.api.hint.HintManager;
import org.apache.shardingsphere.api.sharding.standard.PreciseShardingAlgorithm;
import org.apache.shardingsphere.api.sharding.standard.RangeShardingAlgorithm;
import org.apache.shardingsphere.shardingjdbc.api.ShardingDataSourceFactory;
import org.apache.shardingsphere.shardingjdbc.jdbc.core.datasource.ShardingDataSource;
import org.apache.shardingsphere.shardingjdbc.jdbc.core.statement.ShardingPreparedStatement;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class TestHLShardingJdbc {

    public static void main(String[] args) throws Exception {

        Map<String, DataSource> dataSourceMap = new HashMap<>();

        BasicDataSource dataSource1 = new BasicDataSource();
        dataSource1.setDriverClassName("com.mysql.jdbc.Driver");
        dataSource1.setUrl("jdbc:mysql://gz-cdb-9aun2cpz.sql.tencentcdb.com:62101/test0");
        dataSource1.setUsername("sanyzk");
        dataSource1.setPassword("zkuatDB2018");
        dataSourceMap.put("test0", dataSource1);

        BasicDataSource dataSource2 = new BasicDataSource();
        dataSource2.setDriverClassName("com.mysql.jdbc.Driver");
        dataSource2.setUrl("jdbc:mysql://gz-cdb-9aun2cpz.sql.tencentcdb.com:62101/test1");
        dataSource2.setUsername("sanyzk");
        dataSource2.setPassword("zkuatDB2018");
        dataSourceMap.put("test1", dataSource2);

        TableRuleConfiguration tableRuleConfiguration = new TableRuleConfiguration("t_order","test${0..1}.t_order_${0..1}");

        {
//        tableRuleConfiguration.setDatabaseShardingStrategyConfig(
//                new InlineShardingStrategyConfiguration("user_id", "test${user_id % 2}"));
//        tableRuleConfiguration.setTableShardingStrategyConfig(
//                new InlineShardingStrategyConfiguration("order_id", "t_order_${order_id % 2}"));

            /**
             *  自定义查询算法
             */
//            tableRuleConfiguration.setDatabaseShardingStrategyConfig(
//                    new InlineShardingStrategyConfiguration("user_id", "test${user_id % 2}"));
            TestHLDatabasePreciseShardingAlgorithm testHLDatabasePreciseShardingAlgorithm = new TestHLDatabasePreciseShardingAlgorithm();
            tableRuleConfiguration.setDatabaseShardingStrategyConfig(new StandardShardingStrategyConfiguration("order_id", testHLDatabasePreciseShardingAlgorithm));

            TestHLComplexKeysShardingAlgorithm testHLComplexKeysShardingAlgorithm = new TestHLComplexKeysShardingAlgorithm();
            tableRuleConfiguration.setTableShardingStrategyConfig(new ComplexShardingStrategyConfiguration("user_id, order_id", testHLComplexKeysShardingAlgorithm));
        }

        //
        ShardingRuleConfiguration shardingRuleConfiguration = new ShardingRuleConfiguration();
        shardingRuleConfiguration.getTableRuleConfigs().add(tableRuleConfiguration);

        DataSource dataSource = ShardingDataSourceFactory.createDataSource(dataSourceMap, shardingRuleConfiguration, new Properties());
        ShardingDataSource shardingDataSource = (ShardingDataSource) dataSource;
        selectSQL(shardingDataSource);
        insertSQL(shardingDataSource);
    }

    public static void selectSQL(DataSource dataSource){
        try {

            HintManager hintManager = HintManager.getInstance();

            String sql = "select id, user_id, order_id from t_order p where user_id = 2 and order_id = 2";
            sql = "select id, user_id, order_id from t_order p where order_id = 2";
//            sql = "ree table0 -> table1 ";
            Connection connection = dataSource.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ShardingPreparedStatement shardingPreparedStatement = (ShardingPreparedStatement) preparedStatement;
//            shardingPreparedStatement.executeQuery(sql);
            ResultSet resultSet = shardingPreparedStatement.executeQuery();
//            ResultSet resultSet = preparedStatement.executeQuery(sql);
            if(resultSet != null){
                while (resultSet.next()){
                    String col0 = resultSet.getString(1);
                    String col1 = resultSet.getString(2);
                    String col2 = resultSet.getString(3);
                    System.out.println("id="+ col0 +",user_id="+ col1 +",order_id=" + col2);
                }
            }
            if(resultSet != null){
                resultSet.close();
            }
            if(preparedStatement != null){
                preparedStatement.close();
            }
            if(connection != null){
                connection.close();
            }
        }catch (Exception e){
            e.printStackTrace();
        }


    }

    public static void insertSQL(DataSource dataSource){
        try{
            String sql = "insert into t_order (user_id, order_id) values (?, ?)";
            Connection connection = dataSource.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, 4);
            preparedStatement.setInt(2, 4);
            preparedStatement.execute();
            if(preparedStatement != null){
                preparedStatement.close();
            }
            if(connection != null){
                connection.close();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

}
