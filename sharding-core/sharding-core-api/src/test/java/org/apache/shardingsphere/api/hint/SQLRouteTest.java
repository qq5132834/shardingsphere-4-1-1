package org.apache.shardingsphere.api.hint;

import javax.sql.DataSource;
import java.sql.*;

public class SQLRouteTest {

    public static void main(String[] args) throws Exception {
        SliceRoute sliceRoute = new SliceRoute();
        sliceRoute.dirRoute();
    }


    /***
     * 分片路由
     */
    public static class SliceRoute{

        /***
         * 笛卡尔积路由
         */
        private void cartesianProductRoute(){

        }
        /***
         * 标准路由
         */
        private void standardRoute(){

        }
        /***
         * 直接路由
         */
        private void dirRoute() throws Exception {
            String url = "jdbc:mysql://gz-cdb-ild273wn.sql.tencentcdb.com:61377/sany_truck";
            String username = "sanydevtest";
            String password = "sanydevtest@2019";
            Connection conn = DriverManager.getConnection(url, username, password);

            String sql = "SELECT * FROM t_order";
            HintManager hintManager = HintManager.getInstance();
            PreparedStatement pstmt = conn.prepareStatement(sql);
            hintManager.setDatabaseShardingValue(3);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    System.out.println(rs.getString(1));
                }
            }
            if(conn != null){
                conn.close();
            }


        }

    }


}
